package com.user.main.ui.home.add_card

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.model.Teacher
import com.database.getToCard
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ui.love.adapter.ListAdapterAddToCard
import com.user.R
import com.user.main.ui.home.ConstantCollection
import com.user.main.ui.home.adapter.ListAdapter
import com.user.main.ui.home.add_card.moving.ImmediateOrVisa
import com.user.main.ui.home.poduct.ProductActivityDetails

class AddToCard : AppCompatActivity() {
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapterAddToCard
    lateinit var mRecyclerView: RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_card)
        //.. get data from fireStore
        mRecyclerView = findViewById(R.id.myRecyclerViewAddToCard)
        myDataLoaderProgressBar = findViewById(R.id.myProgressBarAddToCard)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapterAddToCard(this, mTeachers)
        mRecyclerView.adapter = listAdapter
        getDataAddToCard()
        deleteItem()
    }
    private fun deleteItem() {
        listAdapter.deleteItemListener = object :ListAdapterAddToCard.OnDeleteItemListenter{
            override fun onItemClick(pos: Int, room: Teacher) {
                MaterialAlertDialogBuilder(this@AddToCard).setMessage("Are you sure to delete this item!!")
                    .setPositiveButton("Yes"){dialog,which->
                            db.collection(ConstantCollection.COLLECTION_ADD_TO_CARD).document(room.id.toString())
                                .delete()
                                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!")
                                    getDataAddToCard()
                                    Toast.makeText(this@AddToCard,"successfully deleted",Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }.setNegativeButton("No"){dialog,which->
                        dialog.dismiss()
                    }.show()
            }

        }
        listAdapter.onItemClickListener = object : ListAdapterAddToCard.OnItemClickListener {
            override fun onItemClick(pos: Int, room: Teacher,counter:String) {
                MaterialAlertDialogBuilder(this@AddToCard).setMessage("Are you sure to add to cart!!")
                    .setPositiveButton("Yes"){dialog,which->
                        room.counter = counter
                     startChatActiviy(room)
                    }.setNegativeButton("No"){dialog,which->
                        dialog.dismiss()
                    }.show()
            }

        }
    }

    private fun startChatActiviy(room:Teacher) {
        val intent = Intent(this, ImmediateOrVisa::class.java)
        // send data
        intent.putExtra(ConstantCollection.COLLECTION_IMMEDIATE_OR_VISA, room)
        startActivity(intent)
    }
    private fun getDataAddToCard() {
        getToCard(onSuccessListener = { qureySnapshot->
            myDataLoaderProgressBar.visibility = View.GONE
            val rooms = qureySnapshot.toObjects(Teacher::class.java)
            listAdapter.changData(rooms)
        },onFailureListener = {
            myDataLoaderProgressBar.visibility = View.GONE

        })
    }
}