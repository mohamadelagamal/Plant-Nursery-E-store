package com.admin.products.edit_delete.edit

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
import com.admin.products.edit_delete.ProductsShowData
import com.admin.uitel.loadImage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.user.R
import com.user.main.ui.home.ConstantCollection


class EditProductData : AppCompatActivity() {
    lateinit var room:Teacher
    lateinit var productID:TextView
    lateinit var nameProduct:EditText
    private var mStorageRef: StorageReference? = null
    lateinit var saveChanges:Button
    lateinit var descriptionChanges:EditText
    lateinit var progressBar: ProgressBar
    private val PICK_IMAGE_REQUEST = 1
    private var mImageUri : Uri? = null
    lateinit var quantity:EditText
    lateinit var new:EditText
    //... save data in map
    val products = HashMap<String, Any>()
    //... key from realTime database
    var key:String?=null
    lateinit var priceProduct:EditText
    lateinit var databaseReference: DatabaseReference
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       room = intent.getParcelableExtra(ConstantCollection.COLLECTION_PUTEXTRA_PRODUCT)!!
        setContentView(R.layout.activity_edit_product_data)
        productID  = findViewById(R.id.categoriesIDEdit)
        nameProduct = findViewById(R.id.nameProductEdit)
        nameProduct.setText(room.name)
        priceProduct = findViewById(R.id.priceEditTextEdit)
        priceProduct.setText(room.price)
        new = findViewById(R.id.newproductEdit)
        new.setText(room.news)
         quantity = findViewById(R.id.quntityEditTextEdit)
        quantity.setText(room.quantity)
        mStorageRef = FirebaseStorage.getInstance().getReference(room.categoryID.toString())
        descriptionChanges= findViewById(R.id.descriptionEditTextProductEdit)
        descriptionChanges.setText(room.description)
        imageView= findViewById(R.id.chooseImageViewProductEdit)
        imageView.loadImage(room.imageUrl)
        progressBar = findViewById(R.id.progressBarChangeEdit)
        productID.setText(room.categoryID)
        saveChanges = findViewById(R.id.upLoadBtnEdit)
        //... get key form realTime database
        databaseReference = FirebaseDatabase.getInstance().getReference(room.categoryID.toString())
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.getChildren()) {
                     key = ds.key
                    Log.e("Key brother",key.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        val deleteItem= findViewById<Button>(R.id.deleteItemEdit)
        deleteItem.setOnClickListener {
            deleteItemListener()
        }
        imageView.setOnClickListener { openFileChoose() }
        saveChanges.setOnClickListener {
            changeDataFromRealTimeDatabase()
        }
//        Toast.makeText(
//            applicationContext,
//            room.getCategoryImageID().toString(),
//            Toast.LENGTH_LONG
//        ).show()

    }
    val db = Firebase.firestore
    private fun deleteItemListener() {

        MaterialAlertDialogBuilder(this).setMessage("Are you sure to delete this item?")
            .setPositiveButton("yes"){ dialog, which->
                db.collection(ConstantCollection.COLLECTION_PRODUCT_FIRESTORE).document(room.id.toString())
                    .delete().addOnSuccessListener {
                        Toast.makeText(this,"successfully deleted", Toast.LENGTH_LONG).show()
                        // changData(teacherList)
                    }.addOnFailureListener {  }
                // .... delete form realTimeDataBase
                val ref = FirebaseDatabase.getInstance().reference
                val applesQuery: Query =
                    ref.child(room.categoryID.toString()).orderByChild("name").equalTo(room.name)
                applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (appleSnapshot in dataSnapshot.children) {
                            appleSnapshot.ref.removeValue()
                        }
                        openProductData()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(ContentValues.TAG, "onCancelled", databaseError.toException())
                    }
                })
            dialog.dismiss()
        }.show()


    }

    private fun openProductData() {
        val intent = Intent(this,ProductsShowData::class.java)
        startActivity(intent)
    }
    private fun changeDataFromRealTimeDatabase() {
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
                        products.put("categoryID",productID.text.toString())
                        products.put("imageUrl",task.result.toString())
                        products.put("description",descriptionChanges.text.toString())
                        products.put("quantity",quantity.text.toString())
                        products.put("name",nameProduct.text.toString())
                        products.put("price",priceProduct.text.toString())
                        products.put("news",new.text.toString())
                    //... update data from firestore
                        db.collection(ConstantCollection.COLLECTION_PRODUCT_FIRESTORE)
                            .document(room.getCategoryImageID().toString())
                            .update(products)

                        databaseReference.child(key.toString()).updateChildren(products)
                            .addOnCompleteListener(object : OnCompleteListener<Void> {
                                override fun onComplete(p0: Task<Void>) {
                                   // Toast.makeText(this@EditProductData,"SUCCESS",Toast.LENGTH_LONG).show()
                                }
                            })
                    }
                    progressBar.visibility = View.INVISIBLE
                    backToRecyclerView()
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
       }
        else{Toast.makeText(this@EditProductData,"please choose image !!",Toast.LENGTH_LONG).show() }


    }

    private fun backToRecyclerView() {
        val intent = Intent(this@EditProductData,ProductsShowData::class.java)
        startActivity(intent)
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