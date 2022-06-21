package com.admin.categories

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import com.admin.model.Teacher
import com.base.BaseViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CategoriesViewModel: BaseViewModel<Navigator>() {
    val categoryIDError = ObservableField<String>()
    val categoriesID = ObservableField<String>()
    var mImageUri:Uri?=null


}