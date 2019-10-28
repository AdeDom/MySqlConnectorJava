package com.adedom.mysqlconnectorjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adedom.mysqlconnectorjava.model.ProductItem
import com.adedom.mysqlconnectorjava.utility.MyConnect
import com.adedom.mysqlconnectorjava.utility.MyExecuteQuery
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
    }

    private fun feedProduct() {
        MyConnect.executeStoredProcedure("sp_select_product", emptyList(), MyExecuteQuery { rs ->
            while (rs.next()) {
                val item = ProductItem(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4)
                )
                mProductItem.add(item)
            }
            mRecyclerView.adapter = CustomAdapter()
        })
    }

    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CustomHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_product, viewGroup, false)
            return CustomHolder(view)
        }

        override fun getItemCount(): Int {
            return mProductItem.size
        }

        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
            val item = mProductItem[position]
            holder.mTvId.text = item.id
            holder.mTvName.text = item.name
            holder.mTvPrice.text = item.price.toString()
            holder.mTvType.text = item.type
        }
    }

    inner class CustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvId = itemView.findViewById(R.id.mTvId) as TextView
        val mTvName = itemView.findViewById(R.id.mTvName) as TextView
        val mTvPrice = itemView.findViewById(R.id.mTvPrice) as TextView
        val mTvType = itemView.findViewById(R.id.mTvType) as TextView
    }
}
