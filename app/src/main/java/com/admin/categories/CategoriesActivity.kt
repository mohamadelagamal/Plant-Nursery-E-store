package com.admin.categories

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.admin.AdminActivity
import com.admin.model.Teacher
import com.base.BaseActivity
import com.database.addCategories
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.user.R
import com.user.databinding.ActivityCategoriesBinding

class CategoriesActivity: BaseActivity<ActivityCategoriesBinding,CategoriesViewModel>(),Navigator {
    private var mImageUri : Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    lateinit var progressBar:ProgressBar
    lateinit var idCategories:EditText
    private var mUploadTask: StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1
    lateinit var nameEditText:EditText
    lateinit var chooseImageView:ImageView
    lateinit var descriptionEditText:EditText
    lateinit var upLoadBtn :Button
    lateinit var button_choose_image:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm=viewModel
        viewModel.navigator=this
        descriptionEditText=findViewById(R.id.descriptionEditText)
        chooseImageView=findViewById(R.id.chooseImageView)
        button_choose_image = findViewById(R.id.button_choose_image)
        idCategories = findViewById(R.id.idCategories)
        mStorageRef = FirebaseStorage.getInstance().getReference("categories")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("categories")
        upLoadBtn = findViewById(R.id.upLoadBtn)
        progressBar = findViewById(R.id.progressBar)
        nameEditText = findViewById(R.id.nameEditText)
        button_choose_image.setOnClickListener { openFileChoose() }
        upLoadBtn.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(this@CategoriesActivity,
                    "An Upload is Still in Progress",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                uploadFile()
             // openImagesActivity()
            }
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
            viewModel.mImageUri = data.data
            chooseImageView.setImageURI(mImageUri)
        }
    }
    var room : Teacher?=null
     fun uploadFile() {
         val storageReference = FirebaseStorage.getInstance()
            .getReference("myprofile/" + System.currentTimeMillis() + ".jpg")
        if (mImageUri != null && validation()) {
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
                    Log.d(TAG, "Profile image uploading url $myprofileurl")
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        val upload = Teacher(
                            name = nameEditText.text.toString().trim { it <= ' ' },
                            imageUrl =  task.result.toString(),
                            description =  descriptionEditText.text.toString().trim { it <= ' ' },
                            categoryID = idCategories.text.toString(),
                            roomID = room?.id
                        )
                        addCategories(upload,onSuccessListener = {},onFailureListener = {})
                        val uploadId = mDatabaseRef!!.push().key
                        mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                    }
                    progressBar.visibility = View.INVISIBLE
                  openImagesActivity()
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                }}
        else{
             Toast.makeText(
                 applicationContext,
                 "Image Uploading was failed",
                 Toast.LENGTH_LONG
             ).show()
        }
    }
    fun validation():Boolean{
        var valid = true
        if (viewModel.categoriesID.get().isNullOrBlank()){
            viewModel.categoryIDError.set("please enter your email")
            valid=false
        }else{
            viewModel.categoryIDError.set(null)
        }
        return valid
    }
    private fun  openImagesActivity() {
        startActivity(Intent(this@CategoriesActivity, AdminActivity::class.java))
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_categories
    }

    override fun makeViewModelProvider(): CategoriesViewModel {
        return ViewModelProvider(this).get(CategoriesViewModel::class.java)
    }
}