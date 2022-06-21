package com.admin.products

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import com.admin.model.Teacher
import com.base.BaseViewModel
import com.database.addProduct
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProductViewModel: BaseViewModel<Navigator>() {
    var productID = ObservableField<String>()
    var productIDError = ObservableField<String>()
    var nameProduct = ObservableField<String>()
    var priceProduct = ObservableField<String>()
    var descriptionProduct = ObservableField<String>()
    var quantityProduct = ObservableField<String>()
    var news = ObservableField<String>()
    var imageURi:Uri?=null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    fun saveDataProduct(){
        if (validation()){
            mStorageRef = FirebaseStorage.getInstance().getReference(productID.get().toString())
            mDatabaseRef = FirebaseDatabase.getInstance().getReference(productID.get().toString())
            val storageReference = FirebaseStorage.getInstance()
                .getReference("Product/" + System.currentTimeMillis() + ".jpg")
            if (imageURi != null) {
                showLoading.value = true
                storageReference.putFile(imageURi!!)
                    .addOnSuccessListener { taskSnapshot ->
                      //  messageLiveData.value = "success uploading"
                        val myprofileurl = taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                        Log.d(ContentValues.TAG, "Profile image uploading url $myprofileurl")
                        storageReference.downloadUrl.addOnCompleteListener { task ->
                            val upload = Teacher(
                                categoryID = productID.get().toString(),
                                name = nameProduct.get().toString(),
                                price = priceProduct.get().toString(),
                                news = news.get().toString(),
                                imageUrl = task.result.toString(),
                                description = descriptionProduct.get().toString(),
                            )
                            //TODO Here take object form RealTimeDatabase to FireStore DataBase
                            addProduct(upload, onSuccessListener = {}, onFailureListener = {})
                            val uploadId = mDatabaseRef!!.push().key
                            mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                        }
                        // viewDataBinding.progressBar.visibility = View.INVISIBLE
                        showLoading.value = false
                        navigator?.openAdminFunction()
                    }.addOnFailureListener {
                        messageLiveData.value= "you have a problem"
                        showLoading.value = false
                    }
            }
            else{
                messageLiveData.value= "please check image!!"
            }
        }
    }
    fun validation():Boolean{
        var valid = true
        if (productID.get().isNullOrBlank()){
            productIDError.set("please enter ID")
            valid=false
        }else{
            productIDError.set(null)
        }
        return valid
    }
}