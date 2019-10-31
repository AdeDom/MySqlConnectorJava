allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
  

dependencies {
   implementation 'com.github.pathiphon:MySqlConnectorJava:0.5.1'
}




CONNECTION
    val conn = Dru.connection(
        "192.168.43.22",
        "my_connect_jdbc",
        "root",
        "abc456"
    )

SELECT
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
      
INSERT
            val sql = "INSERT INTO tbl_product(name, price, type) " +
                    "VALUES ('${mEdtName.text.toString().trim()}'," +
                    "'${mEdtPrice.text.toString().trim()}'," +
                    "'${mEdtType.text.toString().trim()}')"
            Dru.connection(MainActivity().conn)
                .execute(sql)
                .setCallBack {
                    mEdtName.text.clear()
                    mEdtPrice.text.clear()
                    mEdtType.text.clear()
                    Toast.makeText(baseContext, "Insert success", Toast.LENGTH_SHORT).show()
                }
      
UPDATE
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
      
DELETE
            Dru.connection(MainActivity().conn)
                .call("sp_delete_product")
                .parameter(mEdtId.text.toString().trim())
                .commit()
                .setCallBack {
                    mEdtId.setText("")
                    Toast.makeText(baseContext, "Delete success", Toast.LENGTH_SHORT).show()
                }
