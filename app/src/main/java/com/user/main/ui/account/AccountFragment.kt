package com.user.main.ui.account

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.admin.uitel.loadImage
import com.database.addUserToFireStore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.model.ApplicationUser
import com.model.DataUtil
import com.user.R
import com.user.account.sign.LoginActivity

class AccountFragment : Fragment() {
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mImageUri : Uri? = null
    private var mStorage: FirebaseStorage? = null
    private val PICK_IMAGE_REQUEST = 1
    lateinit var nameUser : EditText
    lateinit var moreCategories: TextView
    lateinit var progressBar: ProgressBar
    lateinit var saveButton : LinearLayout
    lateinit var emailUser : EditText
    lateinit var cardImageView : CardView
    lateinit var imageUser: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_account_activity, container, false)
    }

    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    var progressDialog : ProgressDialog?=null
    private fun showLoading() {
        progressDialog= ProgressDialog(requireContext())
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }
    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog=null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardImageView = requireActivity().findViewById(R.id.cardImageUser)
        imageUser = requireActivity().findViewById(R.id.imageUser)
        mAuth = FirebaseAuth.getInstance()
        nameUser = requireActivity().findViewById(R.id.userName)
        moreCategories = requireActivity().findViewById(R.id.logOut)
        emailUser = requireActivity().findViewById(R.id.userEmail)
        saveButton = requireActivity().findViewById(R.id.linearSave)
        //...
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.reference
        val getImage = databaseReference.child("imageUser")
        getImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val link = dataSnapshot.getValue(String::class.java)
                Picasso.get().load(link).fit().centerCrop()
                    .placeholder(R.drawable.icon_account)
                    .error(R.drawable.icon_account)
                    .into(imageUser);
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Error Loading Image", Toast.LENGTH_SHORT).show()
            }
        })
        cardImageView.setOnClickListener {
            showCamera()
        }
        //..
        moreCategories.setOnClickListener{
            MaterialAlertDialogBuilder(requireContext()).setMessage("Are you sure to log out?").setPositiveButton("yes"){ dialog, which->
                logOut()
            }.show()
        }
        val rootRef = FirebaseFirestore.getInstance()
        val usersRef = rootRef.collection("users")
        val uidRef = usersRef.document(uid)
        uidRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val zip_codes = document.toObject(ApplicationUser::class.java)
                    //Do what you need to do with your list
                    nameUser.setText(zip_codes?.userName)
                    emailUser.setText(zip_codes?.email)
                    imageUser.loadImage(zip_codes?.imageID)
                    Log.d(ContentValues.TAG, "No such document")
                }
            } else {
                Log.d(ContentValues.TAG, "get failed with ", task.exception)
            }
        }
        saveButton.setOnClickListener {
            saveUserData()
            imageUserFunctionSave()
            showImageUser()}
    }
    private fun showImageUser() {
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUser")

    }
    var mAuth: FirebaseAuth? = null
    private fun imageUserFunctionSave() {
        mStorageRef = FirebaseStorage.getInstance().getReference("imageUser")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUser")
        val storageReference = FirebaseStorage.getInstance()
            .getReference("imageUser/" + System.currentTimeMillis() + ".jpg")
        if (filePath != null){
            storageReference.putFile(filePath!!)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        val upload =  ApplicationUser(id = uid,imageID = task.result.toString())
                        val uploadId = mDatabaseRef!!.push().key
                        mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            storageReference.child(mAuth?.currentUser?.uid+".jpg")

        }
    }
    private fun saveUserData() {
        showLoading()
        val appUser = ApplicationUser(
            id = uid,
            userName = nameUser.text.toString(),
            email = emailUser.text.toString(),
            imageID = mImageUri.toString()
        )
        addUserToFireStore(appUser ,
            OnSuccessListener {
                hideLoading()
                DataUtil.user = appUser
                Toast.makeText(requireContext(),"update successfully", Toast.LENGTH_LONG).show()
            }, OnFailureListener {
                hideLoading()
                Toast.makeText(requireContext(),"please check internet", Toast.LENGTH_LONG).show()
            })

    }
    var auth: FirebaseAuth? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            imageUser.setImageURI(mImageUri)
        }
    }
    fun showCamera() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    private var filePath: Uri? = null

    private fun logOut() {
        auth?.signOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }


}