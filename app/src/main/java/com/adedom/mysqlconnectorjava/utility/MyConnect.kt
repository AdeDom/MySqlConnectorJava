package com.adedom.mysqlconnectorjava.utility

import android.os.StrictMode
import java.sql.*

class MyConnect {

    companion object {
        private const val TAG = "MyConnect"
        private const val IP = "192.168.43.22"
        private const val DB_NAME = "my_connect_jdbc"
        private const val USERNAME = "root"
        private const val PASSWORD = "abc456"

        fun conn(): Connection {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

            Class.forName("com.mysql.jdbc.Driver")
            val url = "jdbc:mysql://$IP/$DB_NAME?useUnicode=true&characterEncoding=utf-8"
            return DriverManager.getConnection(url, USERNAME, PASSWORD)
        }

        fun execute(sql: String, update: MyExecuteUpdate) {
            var conn: Connection? = null
            var statement: Statement? = null

            try {
                conn = conn()
                statement = conn.createStatement()
                val result = statement!!.executeUpdate(sql)
                if (result == 1) {
                    update.onComplete()
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                try {
                    statement!!.close()
                } catch (e: Exception) {
                }
                try {
                    conn!!.close()
                } catch (e: Exception) {
                }
            }
        }

        fun execute(sql: String, query: MyExecuteQuery) {
            var conn: Connection? = null
            var statement: Statement? = null
            var rs: ResultSet? = null

            try {
                conn = conn()
                statement = conn.createStatement()
                rs = statement!!.executeQuery(sql)
                query.onComplete(rs)
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                try {
                    rs!!.close()
                } catch (e: Exception) {
                }
                try {
                    statement!!.close()
                } catch (e: Exception) {
                }
                try {
                    conn!!.close()
                } catch (e: Exception) {
                }
            }
        }

        fun executeStoredProcedure(spName: String, data: List<Any>, update: MyExecuteUpdate) {
            val sp = "CALL $spName(${getValues(data)})"
            execute(sp, update)
        }

        fun executeStoredProcedure(spName: String, data: List<Any>, query: MyExecuteQuery) {
            val sp = "CALL $spName(${getValues(data)})"
            execute(sp, query)
        }

        private fun getValues(data: List<Any>): String {
            var values = ""
            for (index in data) {
                values += if (index == "") {
                    "'',"
                } else {
                    "'$index',"
                }
            }
            values = if (data.isEmpty()) {
                ""
            } else {
                values.substring(0, values.length - 1)
            }
            return values
        }
    }
}