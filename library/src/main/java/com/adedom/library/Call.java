package com.adedom.library;

import java.sql.Connection;

public class Call {

    private static Connection connection;

    Call(Connection connection) {
        Call.connection = connection;
    }

    public CallCommit call(String storedProcedureName) {
        return new CallCommit(connection, storedProcedureName);
    }
}