package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.sqlconnectorjava.Dru
import com.adedom.sqlconnectorjava.ExecuteUpdate
import kotlinx.android.synthetic.main.activity_delete.*

class DeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        setEvents()
    }

    private fun setEvents() {
        mBtnDelete.setOnClickListener {
            val args = arrayOf(mEdtId.text.toString().trim())
            Dru.call(MainActivity().conn, "sp_delete_product", ExecuteUpdate {
                mEdtId.setText("")
                Toast.makeText(baseContext, "Delete success", Toast.LENGTH_SHORT).show()
            }, *args)
        }
    }
}
