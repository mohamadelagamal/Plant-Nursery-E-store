package com.admin.categories.edit_delete.edit

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.admin.model.Teacher
import com.admin.uitel.loadImage
import com.database.addCategories
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.user.R
import com.user.main.ui.home.ConstantCollection


class EditActivityCategories : AppCompatActivity() {
    lateinit var room:Teacher
    lateinit var categoriesID:EditText
    lateinit var categoriesName:EditText
    private var mStorageRef: StorageReference? = null
    lateinit var saveChanges:Button
    lateinit var descriptionChanges:EditText
    lateinit var progressBar: ProgressBar
    private val PICK_IMAGE_REQUEST = 1
    private var mImageUri : Uri? = null
    lateinit var databaseReference: DatabaseReference
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(ConstantCollection.COLLECTION_CATEGORIES_EDIT)!!
        setContentView(R.layout.activity_edit_categories)
       categoriesID  = findViewById(R.id.idCategoriesChange)
        categoriesName = findViewById(R.id.nameEditTextChange)
        categoriesName.setText(room.name)
        mStorageRef = FirebaseStorage.getInstance().getReference("categories")
        descriptionChanges= findViewById(R.id.descriptionEditTextChange)
        descriptionChanges.setText(room.description)
        imageView= findViewById(R.id.chooseImageViewChange)
        imageView.loadImage(room.imageUrl)
        progressBar = findViewById(R.id.progressBarChange)
        categoriesID.setText(room.categoryID)
        saveChanges = findViewById(R.id.upLoadBtnChange)
        imageView.setOnClickListener { openFileChoose() }
        saveChanges.setOnClickListener {
           changeData()
         //   Toast.makeText(this@EditActivityCategories,room.key.toString(),Toast.LENGTH_LONG).show()
        }
    }

    private fun changeData(){
        val storageReference = FirebaseStorage.getInstance()
            .getReference("myprofile/" + System.currentTimeMillis() + ".jpg")
        if (mImageUri != null ) {
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
            storageReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    val handler = Handler()
                    handler.postDelayed({
                        progressBar.visibility = View.VISIBLE
                        progressBar.isIndeterminate = false
                        progressBar.progress = 0
                    }, 500)

                    Toast.makeText(
                        applicationContext,
                        "Image Uploaded Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    val myprofileurl = taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    Log.d(ContentValues.TAG, "Profile image uploading url $myprofileurl")
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        val categories = HashMap<String, Any>()
                        categories.put("categoryID",categoriesID.text.toString())
                         categories.put("imageUrl",task.result.toString())
                        categories.put("description",descriptionChanges.text.toString())
                        categories.put("categoryID",categoriesID.text.toString())
                        categories.put("name",categoriesName.text.toString())
                        databaseReference = FirebaseDatabase.getInstance().getReference("categories")
                        databaseReference.child(room.key.toString()).updateChildren(categories)
                            .addOnCompleteListener(object : OnCompleteListener<Void>{
                                override fun onComplete(p0: Task<Void>) {
                                    Toast.makeText(this@EditActivityCategories,"SUCCESS",Toast.LENGTH_LONG).show()
                                }
                            })
                    }
                    progressBar.visibility = View.INVISIBLE

                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                }}
        else{
//            Toast.makeText(
//                applicationContext,
//                "Image Uploading was failed",
//                Toast.LENGTH_LONG
//            ).show()
            val categories = HashMap<String, Any>()
            categories.put("categoryID",categoriesID.text.toString())
            categories.put("imageUrl",room.imageUrl.toString())
            categories.put("description",descriptionChanges.text.toString())
            categories.put("categoryID",categoriesID.text.toString())
            categories.put("name",categoriesName.text.toString())
            databaseReference = FirebaseDatabase.getInstance().getReference("categories")
            databaseReference.child(room.key.toString()).updateChildren(categories)
                .addOnCompleteListener(object : OnCompleteListener<Void>{
                    override fun onComplete(p0: Task<Void>) {
                        Toast.makeText(this@EditActivityCategories,"SUCCESS",Toast.LENGTH_LONG).show()
                    }
                })
        }
    }




    private fun openFileChoose() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            imageView.setImageURI(mImageUri)
        }
    }
}