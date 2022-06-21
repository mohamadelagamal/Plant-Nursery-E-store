package com.ui.love.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.admin.model.Teacher
import com.admin.uitel.loadImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.user.R
import java.util.*

class ListAdapter (var mContext:Context,var teacherList:List<Teacher> ):
RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.imageProductLove)
        var nameT = v.findViewById<TextView>(R.id.productNameLove)
        var favorite = v.findViewById<CheckBox>(R.id.cbHeartProductLove)
        var price = v.findViewById<TextView>(R.id.priceProductLove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.item_love,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =teacherList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       var newList = teacherList[position]
        holder.nameT.text = newList.name
      //  holder.descriT.text = newList.description
        holder.price.text = newList.price
        holder.imgT.loadImage(newList.imageUrl)
        holder.favorite.setOnCheckedChangeListener { checkBox, isChecked ->
            val db = Firebase.firestore
            if (isChecked){
                db.collection("love").document(newList.id.toString())
                    .delete()
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!")
                    Toast.makeText(mContext,"successfully deleted",Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
            }
        }

    }
    fun changData(rooms: List<Teacher>) {
      teacherList = rooms
        notifyDataSetChanged()
    }
    var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(pos: Int, room: Teacher)
    }
}