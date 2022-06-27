package com.admin.products.edit_delete
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.categories.edit_delete.edit.EditActivityCategories
import com.admin.model.Teacher
import com.admin.products.edit_delete.adapter.ListAdapterProducts
import com.admin.products.edit_delete.edit.EditProductData
import com.database.getProduct
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.user.R
import com.user.main.ui.home.ConstantCollection

class ProductsShowData : AppCompatActivity() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapterProducts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_show_data)
        mRecyclerView = findViewById(R.id.showRecyclerProduct)
        myDataLoaderProgressBar = findViewById(R.id.myProgressBarShowProduct)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,2)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapterProducts(this,mTeachers)
        mRecyclerView.adapter = listAdapter
        /** set Firebase Database*/
        retriveDatabase()
    }
    private fun retriveDatabase() {
      getProduct(onSuccessListener = {quarySnapshot->
        myDataLoaderProgressBar.visibility=View.GONE
        val  rooms = quarySnapshot.toObjects(Teacher::class.java)
          listAdapter.changData(rooms)
      }, onFailureListener = {
          myDataLoaderProgressBar.visibility= View.GONE
      })
        listAdapter.onItemClickListener = object : ListAdapterProducts.OnItemClickListener{
            override fun onItemClick(pos: Int, room: Teacher) {
                startChatActiviy(room)
            }
        }

    }
    private fun startChatActiviy(room:Teacher) {
        val intent = Intent(this, EditProductData::class.java)
       intent.putExtra(ConstantCollection.COLLECTION_PUTEXTRA_PRODUCT, room)
        startActivity(intent)
    }
}