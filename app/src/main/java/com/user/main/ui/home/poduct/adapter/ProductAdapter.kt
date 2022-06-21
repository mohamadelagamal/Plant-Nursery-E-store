package com.user.main.ui.home.poduct.adapter

import android.content.ContentValues
import android.content.Context
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


class ProductAdapter (var mContext:Context,var teacherList:List<Teacher> ):
    RecyclerView.Adapter<ProductAdapter.ListViewHolder>()
{
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.teacherImageViewProduct)
        var nameT = v.findViewById<TextView>(R.id.nameTextViewProduct)
        var descriT = v.findViewById<TextView>(R.id.descriptionTextViewProduct)
        var news = v.findViewById<TextView>(R.id.newsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.item_product,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =teacherList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var newList = teacherList[position]
        holder.nameT.text = newList.name
        holder.descriT.text = newList.description
        holder.imgT.loadImage(newList.imageUrl)
        holder.news.text = newList.news
        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,teacherList[position])
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