package com.user.main.ui.love

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.model.Teacher
import com.database.getFavoriteREF
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ui.love.adapter.ListAdapter
import com.user.R

class LoveFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapter
    lateinit var mRecyclerView : RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite, container, false)

    }

    lateinit var reference : DocumentReference
    lateinit var databaseReference  : DatabaseReference
    lateinit var favoriteRef  : DatabaseReference
    lateinit var favoriteListRef  : DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = requireActivity().findViewById(R.id.recycleViewLove)
        myDataLoaderProgressBar = requireActivity().findViewById(R.id.myDataLoaderProgressBar)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapter(requireContext(), mTeachers)
        mRecyclerView.adapter = listAdapter
        showItemLove()
    }
    private fun showItemLove() {

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