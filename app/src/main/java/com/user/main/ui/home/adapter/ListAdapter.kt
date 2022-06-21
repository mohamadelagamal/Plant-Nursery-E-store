package com.user.main.ui.home.adapter
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
import androidx.room.Room
import com.admin.model.Teacher
import com.admin.uitel.loadImage
import com.google.android.gms.common.internal.service.Common
import com.user.R

class ListAdapter (var mContext:Context,var teacherList:List<Teacher> ):
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    inner class ListViewHolder(var v: View) : RecyclerView.ViewHolder(v) {
        var imgT = v.findViewById<ImageView>(R.id.teacherImageView)
        var nameT = v.findViewById<TextView>(R.id.nameTextView)
        var descriT = v.findViewById<TextView>(R.id.descriptionTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.item_categories, parent, false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int = teacherList.size

     fun setFilteredList(filteredList: ArrayList<Teacher>){
             this.teacherList = filteredList
             notifyDataSetChanged()
         }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var newList = teacherList[position]
        holder.nameT.text = newList.name
        holder.descriT.text = newList.description
        holder.imgT.loadImage(newList.imageUrl)
        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,teacherList[position])
            }
        }

    }

    var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(pos: Int, room: Teacher)
    }
}