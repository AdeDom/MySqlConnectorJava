package com.adedom.mysqlconnectorjava;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.adedom.library.ExecuteUpdate;
import com.adedom.library.MyDataBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private EditText mEdtName;
    private Button mBtnInsert;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtName = (EditText) findViewById(R.id.mEdtName);
        mBtnInsert = (Button) findViewById(R.id.mBtnInsert);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);

        MainActivity.Companion.connection();
        Dru.completed(getBaseContext());

        feedData();

        mBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEdtName.getText().toString().trim();
                insert(name);
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
        String sql = "SELECT product.product_id,product.name,category.name \n" +
                "FROM product, category\n" +
                "WHERE product.category_id = category.category_id";
        Dru.connection(MainActivity.Companion.connection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            ArrayList<MyDataBean> items = new ArrayList<>();
                            while (resultSet.next()) {
                                items.add(new MyDataBean(
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

    private void insert(String name) {
        String sql = "INSERT INTO product(name, category_id) VALUES ('" + name + "','1')";
        Dru.connection(MainActivity.Companion.connection())
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
