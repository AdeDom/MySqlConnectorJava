package com.adedom.mysqlconnectorjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adedom.library.Dru
import com.adedom.library.ExecuteQuery
import com.adedom.library.ExecuteUpdate
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Connection

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (connection() == null) {
            Dru.failed(baseContext)
        } else {
            Dru.completed(baseContext)
        }

        bt_insert.setOnClickListener {
            insert()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchProduct()
    }

    private fun fetchProduct() {
        val sql = "SELECT * FROM `product`"
        Dru.connection(connection())
            .execute(sql)
            .commit(ExecuteQuery {
                while (it.next()) {
                    tv_product.append("${it.getString(2)}\n")
                }
            })
    }

    private fun insert() {
        val name = et_name.text.toString().trim()
        val sql = "INSERT INTO `product`(`name`) VALUES ('$name')"
        Dru.connection(connection())
            .execute(sql)
            .commit(ExecuteUpdate {
                et_name.text?.clear()
                tv_product.text = ""
                fetchProduct()
            })
    }

    companion object {
        fun connection(): Connection? {
            return Dru.connection("192.168.43.22", "root", "abc456", "mydev")
        }
    }

}
