package com.adedom.library;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/*
    Pathiphon Jaiyen
    6042470006
    31/10/19
*/

public class Dru {

    public Dru() {
    }

    public static Connection connection(String ip, String dbName, String username, String password) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + ip + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Execute connection(Connection connection) {
        return new Execute(connection);
    }

    public static Call with(Connection connection) {
        return new Call(connection);
    }

    public static void completed(Context context) {
        Toast.makeText(context, R.string.completed, Toast.LENGTH_SHORT).show();
    }

    public static void failed(Context context) {
        Toast.makeText(context, R.string.failed, Toast.LENGTH_LONG).show();
    }

    public static Boolean isEmpty(EditText editText, String error) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.requestFocus();
            editText.setError(error);
            return true;
        }
        return false;
    }

    public static void recyclerView(Context context, RecyclerView recyclerView, ArrayList<DataItem> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyAdapter(items));
    }

    public static void recyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
}
