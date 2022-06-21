package com.admin.products.edit_delete.adapter

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.user.R
import com.user.main.ui.home.ConstantCollection


class ListAdapterProducts (var mContext:Context, var teacherList:List<Teacher> ):
    RecyclerView.Adapter<ListAdapterProducts.ListViewHolder>() {
    inner class ListViewHolder(var v: View) : RecyclerView.ViewHolder(v) {
        var imgT = v.findViewById<ImageView>(R.id.teacherImageView)
        var nameT = v.findViewById<TextView>(R.id.nameTextView)
        var descriT = v.findViewById<TextView>(R.id.descriptionTextView)
        var delete = v.findViewById<CheckBox>(R.id.deleteShowDataCategories)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.item_products_show_data, parent, false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int = teacherList.size
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
        val db = Firebase.firestore
        holder.delete.setOnCheckedChangeListener { checkBox, isChecked ->
//            //.. delete form fireStore
                db.collection(ConstantCollection.COLLECTION_PRODUCT_FIRESTORE).document(newList.id.toString())
                    .delete()
                    .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                      //  Toast.makeText(mContext,"successfully deleted", Toast.LENGTH_LONG).show()
                        Toast.makeText(mContext,newList.id.toString(), Toast.LENGTH_LONG).show()

                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
            //... delete form realTimeDataBase
//            val ref = FirebaseDatabase.getInstance().reference
//            val applesQuery: Query =
//                ref.child(newList.categoryID.toString()).orderByChild(newList.id.toString()).equalTo(newList.name)
//            applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (appleSnapshot in dataSnapshot.children) {
//                        appleSnapshot.ref.removeValue()
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    Log.e(TAG, "onCancelled", databaseError.toException())
//                }
//            })
        }
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
    fun changData(rooms: List<Teacher>) {
        teacherList = rooms
        notifyDataSetChanged()
    }
}