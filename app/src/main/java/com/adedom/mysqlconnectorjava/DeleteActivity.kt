package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adedom.library.Dru
import com.adedom.library.ExecuteUpdate
import kotlinx.android.synthetic.main.activity_delete.*

class DeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        mBtnDelete.setOnClickListener {
            Dru.with(MainActivity().conn)
                .call("sp_delete_product")
                .parameter(mEdtId.text.toString().trim())
                .commit(ExecuteUpdate {
                    mEdtId.text.clear()
                    Dru.completed(baseContext)
                })
        }
    }
}
