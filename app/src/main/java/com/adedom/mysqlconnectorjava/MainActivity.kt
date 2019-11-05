package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adedom.library.Dru
import com.adedom.library.ExecuteQuery
import com.adedom.library.ExecuteUpdate
import com.adedom.library.MyDataBean
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Connection

class MainActivity : AppCompatActivity() {

    companion object {
        fun connection(): Connection {
            return Dru.connection("192.168.43.22", "root", "abc456", "my_connect_jdbc")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dru.completed(baseContext)

        feedData()

        mBtnInsert.setOnClickListener {
            insert()
        }

        mSwipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        mSwipeRefreshLayout.setOnRefreshListener {
            feedData()
        }
    }

    private fun feedData() {
        Dru.with(connection())
            .call("sp_select_product")
            .commit(ExecuteQuery {
                val items = arrayListOf<MyDataBean>()
                while (it.next()) {
                    items.add(
                        MyDataBean(
                            it.getString(3),
                            it.getString(2)
                        )
                    )
                }
                Dru.recyclerView(baseContext, mRecyclerView, items)
                mSwipeRefreshLayout.isRefreshing = false
            })
    }

    private fun insert() {
        Dru.with(connection())
            .call("sp_insert_product")
            .parameter(mEdtName.text.toString().trim())
            .commit(ExecuteUpdate {
                mEdtName.text.clear()
                feedData()
            })
    }
}
