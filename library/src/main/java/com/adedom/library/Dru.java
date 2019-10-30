package com.adedom.library;

/*
    Pathiphon Jaiyen
    6042470006
    30/10/19
*/

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Dru implements SetCallBack {

    private static Connection connection;

    private static ArrayList<String> values;

    private static String sql;

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

    public static Dru connection(Connection connection) {
        Dru.connection = connection;
        return new Dru();
    }

    public Dru execute(String sql) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            if (result == 1) {
                new Dru();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Dru();
    }

    public void execute(String sql, ExecuteQuery query) {
        query(sql, query);
    }

    @Override
    public void setCallBack(ExecuteUpdate update) {
        update.onComplete();
    }

    public Dru call(String storedProcedureName) {
        values = new ArrayList<>();
        sql = "CALL " + storedProcedureName + "(";
        return new Dru();
    }

    public Dru parameter(String values) {
        Dru.values.add("'" + values + "'");
        return new Dru();
    }

    public Dru commit() {
        spFormat();
        execute(sql);
        return new Dru();
    }

    public void commit(ExecuteQuery query) {
        spFormat();
        query(sql, query);
    }

    private void query(String sql, ExecuteQuery query) {
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            query.onComplete(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert rs != null;
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void spFormat() {
        for (String s : values) sql += s + ",";
        if (!values.isEmpty()) sql = sql.substring(0, sql.length() - 1);
        sql += ")";
    }
}
