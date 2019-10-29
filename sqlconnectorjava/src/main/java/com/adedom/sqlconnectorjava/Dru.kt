package com.adedom.sqlconnectorjava

import android.os.StrictMode
import java.sql.*

/*
    Pathiphon  Jaiyen
    6042470006
    30/10/19
*/

class Dru {

    companion object {

        fun connection(ip: String, dbName: String, username: String, password: String): Connection {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

            Class.forName("com.mysql.jdbc.Driver")
            val url = "jdbc:mysql://$ip/$dbName?useUnicode=true&characterEncoding=utf-8"
            return DriverManager.getConnection(url, username, password)
        }

        fun execute(connection: Connection, sql: String) {
            var conn: Connection? = null
            var statement: Statement? = null

            try {
                conn = connection
                statement = conn.createStatement()
                statement!!.executeUpdate(sql)
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

        fun execute(connection: Connection, sql: String, update: ExecuteUpdate) {
            var conn: Connection? = null
            var statement: Statement? = null

            try {
                conn = connection
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

        fun execute(connection: Connection, sql: String, query: ExecuteQuery) {
            var conn: Connection? = null
            var statement: Statement? = null
            var rs: ResultSet? = null

            try {
                conn = connection
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

        fun call(connection: Connection, spName: String, vararg args: String) {
            val sp = "CALL $spName(${getValues(*args)})"
            execute(connection, sp)
        }

        fun call(
            connection: Connection,
            spName: String,
            vararg args: String,
            update: ExecuteUpdate
        ) {
            val sp = "CALL $spName(${getValues(*args)})"
            execute(connection, sp, update)
        }

        fun call(
            connection: Connection,
            spName: String,
            update: ExecuteUpdate,
            vararg args: String
        ) {
            val sp = "CALL $spName(${getValues(*args)})"
            execute(connection, sp, update)
        }

        fun call(connection: Connection, spName: String, vararg args: String, query: ExecuteQuery) {
            val sp = "CALL $spName(${getValues(*args)})"
            execute(connection, sp, query)
        }

        fun call(connection: Connection, spName: String, query: ExecuteQuery, vararg args: String) {
            val sp = "CALL $spName(${getValues(*args)})"
            execute(connection, sp, query)
        }

        private fun getValues(vararg args: String): String {
            var values = ""
            for (index in args) {
                values += if (index == "") {
                    "'',"
                } else {
                    "'$index',"
                }
            }
            values = if (args.isEmpty()) {
                ""
            } else {
                values.substring(0, values.length - 1)
            }
            return values
        }
    }
}