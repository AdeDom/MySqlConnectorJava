package com.adedom.mysqlconnectorjava

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adedom.library.Dru
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val conn = Dru.connection(
        "192.168.43.22",
        "my_connect_jdbc",
        "root",
        "abc456"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (conn == null) {
            Dru.failed(baseContext)
        } else {
            Dru.completed(baseContext)
        }

        setEvents()
    }

    private fun setEvents() {
        mBtnSelect.setOnClickListener {
            startActivity(Intent(baseContext, SelectActivity::class.java))
        }
        mBtnInsert.setOnClickListener {
            startActivity(Intent(baseContext, InsertActivity::class.java))
        }
        mBtnUpdate.setOnClickListener {
            startActivity(Intent(baseContext, UpdateActivity::class.java))
        }
        mBtnDelete.setOnClickListener {
            startActivity(Intent(baseContext, DeleteActivity::class.java))
        }
    }
}
