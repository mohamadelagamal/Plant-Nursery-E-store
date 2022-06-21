package com.admin.products

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.admin.AdminActivity
import com.admin.model.Teacher
import com.base.BaseActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.user.R
import com.user.databinding.ActivityProductsBinding

class ProductsActivity: BaseActivity<ActivityProductsBinding,ProductViewModel>(),Navigator {
    private var mImageUri : Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    lateinit var progressBar: ProgressBar
    lateinit var idCategories: EditText
    private var mUploadTask: StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1
    lateinit var nameEditText: EditText
    lateinit var chooseImageView: ImageView
    lateinit var quantityPoducts:EditText
    lateinit var upLoadBtn : Button
    lateinit var button_choose_image: CardView
    lateinit var news:EditText
    lateinit var priceProducts:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        viewModel.navigator = this
        quantityPoducts = findViewById(R.id.quntityEditText)
        mStorageRef = FirebaseStorage.getInstance().getReference("products")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("products")
        idCategories = findViewById(R.id.categoriesID)
        nameEditText = findViewById(R.id.nameEditText)
        priceProducts = findViewById(R.id.priceEditText)
        news = findViewById(R.id.newEdit)
        button_choose_image = findViewById(R.id.button_choose_image)
        chooseImageView = findViewById(R.id.chooseImageViewProduct)
        upLoadBtn = findViewById(R.id.upLoadBtn)
        button_choose_image.setOnClickListener { openFileChoose() }

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
            viewModel.imageURi = data.data
            chooseImageView.setImageURI(mImageUri)
        }
    }
    var room : Teacher?=null

    override fun getLayoutID(): Int {
        return R.layout.activity_products
    }

    override fun makeViewModelProvider(): ProductViewModel {
        return ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun openAdminFunction() {
        val intent = Intent(this,AdminActivity::class.java)
        startActivity(intent)
    }
}