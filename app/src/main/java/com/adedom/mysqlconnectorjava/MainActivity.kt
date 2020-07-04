package com.adedom.mysqlconnectorjava

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adedom.library.Dru
import com.adedom.library.ExecuteQuery
import com.adedom.library.ExecuteUpdate
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Connection
import java.util.*

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

        bt_upload_image.setOnClickListener {
            Dru.selectImage(this, 1234)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchProduct()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK && data != null) {
            val baseUrl = "http://192.168.43.22/ics/images/"
            val imageName = UUID.randomUUID().toString().replace("-", "") + ".jpg"
            Dru.uploadImage(baseContext, baseUrl, imageName, data.data) {
                Toast.makeText(baseContext, it.response, Toast.LENGTH_SHORT).show()
            }
        }
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
