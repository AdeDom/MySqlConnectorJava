package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adedom.library.Dru
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        setEvents()
    }

    private fun setEvents() {
        mBtnUpdate.setOnClickListener {
            Dru.connection(MainActivity().conn)
                .call("sp_update_product")
                .parameter(mEdtId.text.toString().trim())
                .parameter(mEdtName.text.toString().trim())
                .parameter(mEdtPrice.text.toString().trim())
                .parameter(mEdtType.text.toString().trim())
                .commit()
                .setCallBack {
                    mEdtId.setText("")
                    mEdtName.setText("")
                    mEdtPrice.setText("")
                    mEdtType.setText("")
                    Toast.makeText(baseContext, "Update success", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
