package com.admin.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Teacher(
    var name:String? = null,
    var imageUrl:String? = null,
    var description:String? = null,
    var categoryID:String?=null,
    var roomID: String? = null,
    var id :String?=null,
    var news:String?=null,
    var price:String?=null,
    var quantity:String?=null,
    @get:Exclude
    @set:Exclude
    var key:String? = null

): Parcelable {

    fun getCategoryImageID(): String? {
        return id
    }
}