package com.adedom.mysqlconnectorjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adedom.library.Data;
import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.adedom.library.ExecuteUpdate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private EditText mEdtName;
    private Button mBtnInsert;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Connection connection() {
        return Dru.connection("192.168.43.22", "root", "abc456", "mydev");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtName = (EditText) findViewById(R.id.mEdtName);
        mBtnInsert = (Button) findViewById(R.id.mBtnInsert);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);

        Main2Activity.connection();
        Dru.completed(getBaseContext());

        feedData();

        mBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feedData();
            }
        });
    }

    private void feedData() {
        String sql = "SELECT * FROM product";
        Dru.connection(Main2Activity.connection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            ArrayList<Data> items = new ArrayList<>();
                            while (resultSet.next()) {
                                items.add(new Data(
                                        resultSet.getString(3),
                                        resultSet.getString(2)
                                ));
                            }
                            Dru.recyclerView(getBaseContext(), mRecyclerView, items);
                            mSwipeRefreshLayout.setRefreshing(false);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void insert() {
        String name = mEdtName.getText().toString().trim();
        String sql = "INSERT INTO `product`(`name`, `price`) VALUES ('" + name + "','1')";
        Dru.connection(Main2Activity.connection())
                .execute(sql)
                .commit(new ExecuteUpdate() {
                    @Override
                    public void onComplete() {
                        mEdtName.setText("");
                        feedData();
                    }
                });
    }
}
