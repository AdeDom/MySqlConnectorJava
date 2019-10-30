package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adedom.library.Dru
import com.adedom.mysqlconnectorjava.model.ProductItem
import kotlinx.android.synthetic.main.activity_select.*
import java.util.*

class SelectActivity : AppCompatActivity() {

    private lateinit var mProductItem: ArrayList<ProductItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        init()
        feedProduct()
    }

    private fun init() {
        mProductItem = arrayListOf<ProductItem>()
        mRecyclerView.layoutManager = LinearLayoutManager(baseContext)
        mRecyclerView.adapter = CustomAdapter()
    }

    private fun feedProduct() {
        val sql = "SELECT name FROM tbl_product"
        Dru.connection(MainActivity().conn)
            .execute(sql) {
                while (it.next()) {
                    val item = ProductItem(
                        name = it.getString(1)
                    )
                    mProductItem.add(item)
                }
                mRecyclerView.adapter!!.notifyDataSetChanged()
            }
    }

    inner class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomHolder>() {
        inner class CustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mTvName = itemView.findViewById(android.R.id.text1) as TextView
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CustomHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(android.R.layout.simple_list_item_1, viewGroup, false)
            return CustomHolder(view)
        }

        override fun getItemCount(): Int {
            return mProductItem.size
        }

        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
            val item = mProductItem[position]
            holder.mTvName.text = item.name
        }
    }
}
