package com.user.main.ui.home.poduct

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.model.Teacher
import com.base.BaseActivity
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.user.R
import com.user.databinding.ActivityPoductDetailsBinding
import com.user.main.ui.home.ConstantCollection
import com.user.main.ui.home.adapter.ListAdapter
import com.user.main.ui.home.poduct.adapter.ProductAdapter
import com.user.main.ui.home.poduct.details.ShowProductDetails

class ProductActivityDetails : BaseActivity<ActivityPoductDetailsBinding,ProductViewModelDetails>(),Navigator {
    lateinit var room: Teacher
    lateinit var layoutManager: LinearLayoutManager
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private lateinit var mTeachers:MutableList<Teacher>
    lateinit var mRecyclerView : RecyclerView
    private var mDBListener: ValueEventListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRecyclerView = findViewById(R.id.myRecyclerView)
        room = intent.getParcelableExtra(ConstantCollection.COLLECTION_ROOMS)!!
        viewModel.room = room
        var categoryID = room.categoryID
        viewDataBinding.vmProducts = viewModel
        viewModel.navigator = this
        /**set adapter*/
        mRecyclerView = findViewById(R.id.myRecyclerView)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,2)
        viewModel.showLoading.value=true
        mTeachers = ArrayList()
        listAdapter = ProductAdapter(this,mTeachers)
        mRecyclerView.adapter = listAdapter
        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(categoryID.toString())
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
              //  Toast.makeText(this@ProductActivityDetails,error.message, Toast.LENGTH_SHORT).show()
               // myDataLoaderProgressBar.visibility = View.INVISIBLE
                viewModel.showLoading.value=false
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mTeachers.clear()
                for (teacherSnapshot in snapshot.children){
                    val upload = teacherSnapshot.getValue(Teacher::class.java)
                    upload!!.key = teacherSnapshot.key
                    mTeachers.add(upload)

                }
                listAdapter.notifyDataSetChanged()
                viewModel.showLoading.value=false

            }

        })
        listAdapter.onItemClickListener = object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, room: Teacher) {
                startChatActiviy(room)
            }

        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_poduct_details
    }

    override fun makeViewModelProvider(): ProductViewModelDetails {
        return ViewModelProvider(this).get(ProductViewModelDetails::class.java)
    }
    private lateinit var listAdapter: ProductAdapter
    private fun startChatActiviy(room:Teacher) {
        val intent = Intent(this, ShowProductDetails::class.java)
        intent.putExtra(ConstantCollection.COLLECTION_PRODUCT, room)
        startActivity(intent)
    }
}