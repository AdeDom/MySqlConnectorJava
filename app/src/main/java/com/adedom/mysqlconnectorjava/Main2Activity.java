package com.adedom.mysqlconnectorjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adedom.library.Dru;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class Main2Activity extends AppCompatActivity {

    private TextInputEditText mEtName;
    private MaterialButton mBtInsert;
    private MaterialTextView mTvProduct;
    private MaterialButton mBtUploadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtName = (TextInputEditText) findViewById(R.id.et_name);
        mBtInsert = (MaterialButton) findViewById(R.id.bt_insert);
        mTvProduct = (MaterialTextView) findViewById(R.id.tv_product);
        mBtUploadImage = (MaterialButton) findViewById(R.id.bt_upload_image);

        if (connection() == null) {
            Dru.failed(getBaseContext());
        } else {
            Dru.completed(getBaseContext());
        }

        mBtInsert.setOnClickListener(view -> {
            insert();
        });

        mBtUploadImage.setOnClickListener(v -> {
            Dru.selectImage(this, 1234);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProduct();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK & data != null) {
            String baseUrl = "http://192.168.43.22/ics/images/";
            String imageName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
            Dru.uploadImage(getBaseContext(), baseUrl, imageName, data.getData(), response -> {
                Toast.makeText(getBaseContext(), response.getResponse(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void fetchProduct() {
        String sql = "SELECT * FROM `product`";
        Dru.connection(connection())
                .execute(sql)
                .commit(resultSet -> {
                    try {
                        while (resultSet.next()) {
                            mTvProduct.append(resultSet.getString(2) + "\n");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void insert() {
        String name = mEtName.getText().toString().trim();
        String sql = "INSERT INTO `product`(`name`) VALUES ('" + name + "')";
        Dru.connection(connection())
                .execute(sql)
                .commit(() -> {
                    mEtName.setText("");
                    mTvProduct.setText("");
                    fetchProduct();
                });
    }

    public static Connection connection() {
        return Dru.connection("192.168.43.22", "root", "abc456", "mydev");
    }

}
