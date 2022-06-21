package com.admin.categories.edit_delete

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.categories.edit_delete.adapter.ListAdapterCategories
import com.admin.categories.edit_delete.edit.EditActivityCategories
import com.admin.model.Teacher
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.user.R
import com.user.main.ui.home.ConstantCollection

class ShowDataCategories : AppCompatActivity() {

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapterCategories
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data_categories)
        mRecyclerView = findViewById(R.id.showRecyclerCategories)
        myDataLoaderProgressBar = findViewById(R.id.myProgressBarShowCategories)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,2)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapterCategories(this,mTeachers)
        mRecyclerView.adapter = listAdapter
        /**set Firebase Database*/
        retriveDatabase()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }
    private fun retriveDatabase() {
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("categories")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ShowDataCategories,error.message, Toast.LENGTH_SHORT).show()
                myDataLoaderProgressBar.visibility = View.INVISIBLE
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mTeachers.clear()
                for (teacherSnapshot in snapshot.children){
                    val upload = teacherSnapshot.getValue(Teacher::class.java)
                    upload!!.key = teacherSnapshot.key
                    mTeachers.add(upload)
                }
                listAdapter.notifyDataSetChanged()
                myDataLoaderProgressBar.visibility = View.GONE
            }
        })
        listAdapter.onItemClickListener = object : ListAdapterCategories.OnItemClickListener {
            override fun onItemClick(pos: Int, room: Teacher) {
                startChatActiviy(room)
            }

        }
    }

    private fun startChatActiviy(room:Teacher) {
        val intent = Intent(this, EditActivityCategories::class.java)
        intent.putExtra(ConstantCollection.COLLECTION_CATEGORIES_EDIT, room)
        startActivity(intent)
    }
}