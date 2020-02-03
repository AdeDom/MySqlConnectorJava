package com.adedom.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Callback;
import retrofit2.Response;

/*
    Pathiphon Jaiyen
    6042470006
    28/01/20

    Facebook : AdeDom Jaiyen
*/

public class Dru {

    public static Connection connection(String host, String username, String password, String databaseName) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + host.trim() + "/" + databaseName.trim() + "?useUnicode=true&characterEncoding=utf-8";
            return DriverManager.getConnection(url, username.trim(), password.trim());
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

    public static void recyclerView(Context context, RecyclerView recyclerView, ArrayList<Data> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new Adapter(items));
    }

    public static void recyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public static String getImageNameJpg() {
        return UUID.randomUUID().toString().replace("-", "") + ".jpg";
    }

    private static String getImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    public static void selectImage(Activity activity, int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, code);
    }

    public static void uploadImage(String baseUrl, String name, Bitmap bitmap) {
        String image = getImageToString(bitmap);
        ApiInterface apiInterface = ApiClient.getRetrofit(baseUrl).create(ApiInterface.class);
        retrofit2.Call<ImageClass> call = apiInterface.uploadImage(name, image);
        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(retrofit2.Call<ImageClass> call, Response<ImageClass> response) {
                ImageClass imageClass = response.body();
            }

            @Override
            public void onFailure(retrofit2.Call<ImageClass> call, Throwable t) {
            }
        });
    }

    public static void loadImageCircle(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .circleCrop()
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }
}
