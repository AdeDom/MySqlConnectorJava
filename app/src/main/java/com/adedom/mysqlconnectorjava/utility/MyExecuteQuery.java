package com.adedom.mysqlconnectorjava.utility;

import java.sql.ResultSet;

public interface MyExecuteQuery {
    void onComplete(ResultSet rs);
}
