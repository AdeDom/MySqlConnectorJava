package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adedom.library.Dru
import com.adedom.library.MyAdapter
import com.adedom.library.MyDataBean
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    private var adapter = MyAdapter()
    private var items = arrayListOf<MyDataBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        Dru.recyclerView(baseContext, mRecyclerView, adapter)

        feedData()

        mBtnSelect.setOnClickListener {
            feedData()
        }
    }

    private fun feedData() {
        Dru.with(MainActivity().conn)
            .call("sp_select_product")
            .commit { rs ->
                items.clear()
                while (rs.next()) {
                    items.add(MyDataBean(rs.getString(2)))
                }
                adapter.refresh(items)
            }
    }
}
