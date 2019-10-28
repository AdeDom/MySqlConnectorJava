package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.mysqlconnectorjava.utility.MyConnect
import com.adedom.mysqlconnectorjava.utility.MyExecuteUpdate
import kotlinx.android.synthetic.main.activity_insert.*

class InsertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        setEvents()
    }

    private fun setEvents() {
        mBtnInsert.setOnClickListener {
            val data = listOf<Any>(
                mEdtName.text.toString().trim(),
                mEdtPrice.text.toString().trim(),
                mEdtType.text.toString().trim()
            )
            MyConnect.executeStoredProcedure("sp_insert_product", data, MyExecuteUpdate {
                mEdtName.setText("")
                mEdtPrice.setText("")
                mEdtType.setText("")
                Toast.makeText(baseContext, "Insert success", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
