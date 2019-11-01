package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adedom.library.Dru
import com.adedom.library.ExecuteUpdate
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        mBtnUpdate.setOnClickListener {
            Dru.with(MainActivity().conn)
                .call("sp_update_product")
                .parameter(mEdtId.text.toString().trim())
                .parameter(mEdtName.text.toString().trim())
                .parameter(mEdtPrice.text.toString().trim())
                .parameter(mEdtType.text.toString().trim())
                .commit(ExecuteUpdate {
                    mEdtId.text.clear()
                    mEdtName.text.clear()
                    mEdtPrice.text.clear()
                    mEdtType.text.clear()
                    Dru.completed(baseContext)
                })
        }
    }
}
