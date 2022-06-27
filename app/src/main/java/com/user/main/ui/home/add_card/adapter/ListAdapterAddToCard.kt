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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.admin.model.Teacher
import com.admin.uitel.loadImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.user.R
import com.user.main.ui.home.ConstantCollection
import org.w3c.dom.Text
import java.util.*

class ListAdapterAddToCard (var mContext:Context, var teacherList:List<Teacher> ):
RecyclerView.Adapter<ListAdapterAddToCard.ListViewHolder>()
{
    var counterRoom :Int =0
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.imageProductAddToCard)
        var nameT = v.findViewById<TextView>(R.id.productNameAddToCard)
        var price = v.findViewById<TextView>(R.id.priceProductAddToCard)
       var removeItem = v.findViewById<ImageView>(R.id.removeRoom)
       val addItem = v.findViewById<ImageView>(R.id.addRoom)
        val counter = v.findViewById<TextView>(R.id.numberRoomCounterAddTOCard)
        val deleteItem = v.findViewById<CardView>(R.id.cardDeleteItemAddToCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.item_add_to_card,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =teacherList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       var newList = teacherList[position]
        holder.nameT.text = newList.name
      //  holder.descriT.text = newList.description
        holder.price.text = newList.price
        holder.imgT.loadImage(newList.imageUrl)
        deleteItemListener.let {
            holder.deleteItem.setOnClickListener {
                deleteItemListener?.onItemClick(position,teacherList[position])
            }
        }
        holder.addItem.setOnClickListener {
            holder.addItem.animate().apply {
                duration = 100
                rotationYBy(360f)
            }.start()
            counterRoom ++
            holder.counter.text = counterRoom.toString()
        }
        holder.removeItem.setOnClickListener {
            holder.removeItem.animate().apply {
                duration = 100
                rotationYBy(360f)
            }.start()
            if (counterRoom>0){
                counterRoom --
                holder.counter.text = counterRoom.toString()
            }
        }
        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,teacherList[position], holder.counter.text.toString())
            }
        }
//                onItemLongClickListener.let {
//            holder.itemView.setOnLongClickListener {
//                onItemLongClickListener?.onItemClick(position,teacherList[position], holder.counter.text.toString())
//                return@setOnLongClickListener true
//            }
//        }

    }
    fun changData(rooms: List<Teacher>) {
      teacherList = rooms
        notifyDataSetChanged()
    }
    var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(pos: Int, room: Teacher , counter:String)
    }
    var onItemLongClickListener : OnItemLongClickListener ?=null
    interface OnItemLongClickListener{
        fun onItemClick(pos: Int, room: Teacher , counter:String)
    }
    var deleteItemListener: OnDeleteItemListenter? = null
    interface OnDeleteItemListenter {
        fun onItemClick(pos: Int, room: Teacher)
    }

}