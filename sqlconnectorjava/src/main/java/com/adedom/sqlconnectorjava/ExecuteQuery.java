package com.adedom.sqlconnectorjava;

import java.sql.ResultSet;

public interface ExecuteQuery {
    void onComplete(ResultSet rs);
}
