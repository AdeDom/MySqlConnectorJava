package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.mysqlconnectorjava.utility.MyConnect
import com.adedom.mysqlconnectorjava.utility.MyExecuteUpdate
import kotlinx.android.synthetic.main.activity_delete.*

class DeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        setEvents()
    }

    private fun setEvents() {
        mBtnDelete.setOnClickListener {
            val data = listOf<Any>(mEdtId.text.toString().trim())
            MyConnect.executeStoredProcedure("sp_delete_product", data, MyExecuteUpdate {
                mEdtId.setText("")
                Toast.makeText(baseContext, "Delete success", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
