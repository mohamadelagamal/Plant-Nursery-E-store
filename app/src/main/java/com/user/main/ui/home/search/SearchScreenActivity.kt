package com.user.main.ui.home.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.admin.model.Teacher
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.user.R
import com.user.main.ui.home.ConstantCollection
import com.user.main.ui.home.poduct.ProductActivityDetails

class SearchScreenActivity : AppCompatActivity() {
    lateinit var swiping : SwipeRefreshLayout
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: SearchScreenAdapter
    lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_screen)
        mRecyclerView = findViewById(R.id.myRecyclerView)
        myDataLoaderProgressBar = findViewById(R.id.myProgressBar)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,2)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = SearchScreenAdapter(this,mTeachers)
        mRecyclerView.adapter = listAdapter
        /**set Firebase Database*/
        retriveDatabase()
        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun retriveDatabase() {
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("categories")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SearchScreenActivity,error.message, Toast.LENGTH_SHORT).show()
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
        listAdapter.onItemClickListener = object : SearchScreenAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, room: Teacher) {
                startChatActiviy(room)
            }

        }

    }

    lateinit var itemList:List<Teacher>
    fun filterList(newText: String) {
        val open = ArrayList<Teacher>()
        mTeachers.forEach { Teacher->
            if (Teacher.name!!.toLowerCase().contains(newText!!.toLowerCase())){
                open.add(Teacher)
            }
        }
        listAdapter.setFilteredList(open)
        //ListAdapter(requireContext(),open)

    }

    private fun startChatActiviy(room:Teacher) {
        val intent = Intent(this, ProductActivityDetails::class.java)
        // send data
        intent.putExtra(ConstantCollection.COLLECTION_ROOMS, room)
        startActivity(intent)
    }
}