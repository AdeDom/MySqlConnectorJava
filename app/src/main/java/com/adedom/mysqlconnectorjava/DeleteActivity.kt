package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.library.Dru
import kotlinx.android.synthetic.main.activity_delete.*

class DeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        setEvents()
    }

    private fun setEvents() {
        mBtnDelete.setOnClickListener {
            Dru.connection(MainActivity().conn)
                .call("sp_delete_product")
                .parameter(mEdtId.text.toString().trim())
                .commit()
                .setCallBack {
                    mEdtId.setText("")
                    Toast.makeText(baseContext, "Delete success", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
