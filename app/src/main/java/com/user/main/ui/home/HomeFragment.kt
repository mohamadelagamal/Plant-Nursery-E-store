package com.user.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.admin.model.Teacher
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.user.R
import com.user.main.ui.home.adapter.ListAdapter
import com.user.main.ui.home.filter.FilterActivity
import com.user.main.ui.home.poduct.ProductActivityDetails
import com.user.main.ui.home.search.SearchScreenActivity


class HomeFragment : Fragment()  {
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapter
    lateinit var filterImage:ImageView
    lateinit var searchView: ImageView
   // private var _binding : FragmentHomeBinding?=null
   // private val binding get() = _binding!!
    override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?,
   ): View {
       return inflater.inflate(R.layout.fragment_home, container, false)
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**set adapter*/
        mRecyclerView = requireView().findViewById(R.id.myRecyclerView)
        myDataLoaderProgressBar = requireView().findViewById(R.id.myProgressBar)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapter(requireContext(),mTeachers)
        mRecyclerView.adapter = listAdapter
        //... filter image
        filterImage = requireActivity().findViewById(R.id.filterView)
        filterImage.setOnClickListener {
            val intent = Intent(requireContext(),FilterActivity::class.java)
            startActivity(intent)
        }
        /**set Firebase Database*/
        retriveDatabase()
        searchView = requireView().findViewById(R.id.searchView)
        searchView.setOnClickListener {
            val intent = Intent(requireContext(), SearchScreenActivity::class.java)
            startActivity(intent)

        }
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }
    private fun retriveDatabase() {
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("categories")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),error.message, Toast.LENGTH_SHORT).show()
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
        listAdapter.onItemClickListener = object : ListAdapter.OnItemClickListener {
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
        val intent = Intent(requireContext(), ProductActivityDetails::class.java)
        // send data
        intent.putExtra(ConstantCollection.COLLECTION_ROOMS, room)
        startActivity(intent)
    }
}