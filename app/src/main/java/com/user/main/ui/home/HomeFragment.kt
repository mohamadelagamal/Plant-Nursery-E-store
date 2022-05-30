package com.user.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.categories.Teacher
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.model.categories.Categories
import com.user.databinding.FragmentHomeBinding
import com.user.main.ui.home.Adapter.RecyclerAdapter

class HomeFragment : Fragment() ,RecyclerAdapter.OnItemClickListener {
    private var mAdapter: RecyclerAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private var mTeachers: List<Teacher>? = null

    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        // add value about items
        return binding.root
    //return inflater.inflate(R.layout.fragment_home, container, false)
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)


        mTeachers = ArrayList()
        //mAdapter!!.setOnItemClickListener(t)

        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("teachers_uploads")

        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (mTeachers as ArrayList<Teacher>).clear()
                for (teacherSnapshot in dataSnapshot.children) {
                    val upload = teacherSnapshot.getValue(Teacher::class.java)
                    upload?.key = teacherSnapshot.key
                    (mTeachers as ArrayList<Teacher>).add(upload!!)
                }
                mAdapter = RecyclerAdapter(requireContext(), mTeachers)
                binding.mRecyclerView.adapter = mAdapter
                binding.progressBar.visibility = View.GONE
                mAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), databaseError.message, Toast.LENGTH_SHORT).show()
                //mProgressBar!!.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }

    override fun onItemClick(position: Int) {
        val clickedTeacher = mTeachers!![position]
        val teacherData =
            arrayOf(clickedTeacher.imageUrl,clickedTeacher.name)
        //openDetailActivity(teacherData)
    }

    override fun onShowItemClick(position: Int) {
//        val clickedTeacher = mTeachers!![position]
//        val teacherData =
//            arrayOf(clickedTeacher.name, clickedTeacher.description, clickedTeacher.imageUrl)
//        //openDetailActivity(teacherData)
    }

    override fun onDeleteItemClick(position: Int) {
        val selectedItem = mTeachers!![position]
       // val selectedKey = selectedItem.key

        val imageRef = selectedItem.imageUrl?.let { mStorage?.getReferenceFromUrl(it) }
        imageRef?.delete()?.addOnSuccessListener {
         //   mDatabaseRef!!.child(selectedKey).removeValue()
            Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openDetailActivity(data: Array<String>) {
//        val intent = Intent(this, DetailsActivity::class.java)
//        intent.putExtra("NAME_KEY", data[0])
//        intent.putExtra("DESCRIPTION_KEY", data[1])
//        intent.putExtra("IMAGE_KEY", data[2])
//        startActivity(intent)
    }
     override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }

}