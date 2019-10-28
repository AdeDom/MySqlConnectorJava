package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.mysqlconnectorjava.utility.MyConnect
import com.adedom.mysqlconnectorjava.utility.MyExecuteUpdate
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        setEvents()
    }

    private fun setEvents() {
        mBtnUpdate.setOnClickListener {
            val data = listOf<Any>(
                mEdtId.text.toString().trim(),
                mEdtName.text.toString().trim(),
                mEdtPrice.text.toString().trim(),
                mEdtType.text.toString().trim()
            )
            MyConnect.executeStoredProcedure("sp_update_product", data, MyExecuteUpdate {
                mEdtId.setText("")
                mEdtName.setText("")
                mEdtPrice.setText("")
                mEdtType.setText("")
                Toast.makeText(baseContext, "Update success", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
