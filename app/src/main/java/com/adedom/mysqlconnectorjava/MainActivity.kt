package com.adedom.mysqlconnectorjava

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.mysqlconnectorjava.utility.MyConnect
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnect()
        setEvents()
    }

    private fun checkConnect() {
        if (MyConnect.conn() == null) {
            Toast.makeText(baseContext, "Connect Failed", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(baseContext, "Connect Ok", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setEvents() {
        mBtnSelect.setOnClickListener { startActivity(Intent(baseContext, SelectActivity::class.java)) }
        mBtnInsert.setOnClickListener { startActivity(Intent(baseContext, InsertActivity::class.java)) }
        mBtnUpdate.setOnClickListener { startActivity(Intent(baseContext, UpdateActivity::class.java)) }
        mBtnDelete.setOnClickListener { startActivity(Intent(baseContext, DeleteActivity::class.java)) }
    }
}
