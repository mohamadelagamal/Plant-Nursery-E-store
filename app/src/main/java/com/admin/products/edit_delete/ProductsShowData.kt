package com.admin.products.edit_delete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.categories.edit_delete.adapter.ListAdapterCategories
import com.admin.model.Teacher
import com.admin.products.edit_delete.adapter.ListAdapterProducts
import com.database.getFavoriteREF
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.user.R

class ProductsShowData : AppCompatActivity() {

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
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
        /**set Firebase Database*/
        retriveDatabase()
    }
    private fun retriveDatabase() {
        getFavoriteREF(onSuccessListener = {
                qureySnapshot->
            myDataLoaderProgressBar.visibility = View.GONE
            val rooms = qureySnapshot.toObjects(Teacher::class.java)
            listAdapter.changData(rooms)
        },onFailureListener = {
            myDataLoaderProgressBar.visibility = View.GONE

        })
    }

}