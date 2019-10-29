allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
  

dependencies {
   implementation 'com.github.pathiphon:MySqlConnectorJava:0.5.0'
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
      Dru.execute(MainActivity().conn, sql, ExecuteQuery {
          while (it.next()) {
              val item = ProductItem(
                  name = it.getString(1)
              )
              mProductItem.add(item)
          }
          mRecyclerView.adapter!!.notifyDataSetChanged()
      })
      
INSERT
      val sql = "INSERT INTO tbl_product(name, price, type) " +
        "VALUES ('${mEdtName.text.toString().trim()}'," +
        "'${mEdtPrice.text.toString().trim()}'," +
        "'${mEdtType.text.toString().trim()}')"
      Dru.execute(MainActivity().conn, sql, ExecuteUpdate {
          mEdtName.text.clear()
          mEdtPrice.text.clear()
          mEdtType.text.clear()
          Toast.makeText(baseContext, "Insert success", Toast.LENGTH_SHORT).show()
      })
      
UPDATE
      val args = arrayOf(
          mEdtId.text.toString().trim(),
          mEdtName.text.toString().trim(),
          mEdtPrice.text.toString().trim(),
          mEdtType.text.toString().trim()
      )
      Dru.call(MainActivity().conn, "sp_update_product", *args, update = ExecuteUpdate {
          mEdtId.setText("")
          mEdtName.setText("")
          mEdtPrice.setText("")
          mEdtType.setText("")
          Toast.makeText(baseContext, "Update success", Toast.LENGTH_SHORT).show()
      })
      
DELETE
      val args = arrayOf(mEdtId.text.toString().trim())
      Dru.call(MainActivity().conn, "sp_delete_product", ExecuteUpdate {
          mEdtId.setText("")
          Toast.makeText(baseContext, "Delete success", Toast.LENGTH_SHORT).show()
      }, *args)
