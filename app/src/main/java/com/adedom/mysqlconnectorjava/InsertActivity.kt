package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.sqlconnectorjava.Dru
import com.adedom.sqlconnectorjava.ExecuteUpdate
import kotlinx.android.synthetic.main.activity_insert.*

class InsertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        setEvents()
    }

    private fun setEvents() {
        mBtnInsert.setOnClickListener {
            val sql = "INSERT INTO tbl_product(name, price, type) " +
                    "VALUES ('${mEdtName.text.toString().trim()}'," +
                    "'${mEdtPrice.text.toString().trim()}'," +
                    "'${mEdtType.text.toString().trim()}')"
            Dru.execute(MainActivity().conn, sql, ExecuteUpdate {
                mEdtName.text.clear()
                mEdtPrice.text.clear()
                mEdtType.text.clear()
                Toast.makeText(baseContext, "Insert success", Toast.LENGTH_SHORT).show()
            })
        }
    }
}