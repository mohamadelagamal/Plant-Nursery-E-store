package com.user.main.ui.home.poduct.details

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.admin.model.Teacher
import com.admin.uitel.loadImage
import com.database.addFavorite
import com.database.addToCard
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import com.user.R
import com.user.main.ui.home.ConstantCollection
class ShowProductDetails : AppCompatActivity() {
    lateinit var room: Teacher
    lateinit var imageView: ImageView
    lateinit var nameDetails:TextView
    lateinit var descriptoin:TextView
    lateinit var loveCheckBox: CheckBox
    lateinit var nameNews:TextView
    lateinit var addDataToCard:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product_details)
        room = intent.getParcelableExtra(ConstantCollection.COLLECTION_PRODUCT)!!
        nameDetails = findViewById(R.id.nameProduct)
        nameNews = findViewById(R.id.newsDetailsProduct)
        descriptoin = findViewById(R.id.descriptionDetails)
        loveCheckBox = findViewById(R.id.cbHeartProduct)
        addDataToCard = findViewById(R.id.addToCart)
        addDataToCard.setOnClickListener {
            addToCardButton()
        }
        imageView = findViewById(R.id.imageDetailsProduct)
        imageView.loadImage(room.imageUrl)
        nameDetails.setText(room.name)
        descriptoin.setText(room.description)
        nameNews.setText(room.news)
        loveCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked){
                Log.e("Item added to Wishlist","favorite")
                //  onItemClickListener?.onItemClick(position,newList)
                var favorite = Teacher(name = room.name, price = room.price, imageUrl = room.imageUrl)
                addFavorite(favorite,onSuccessListener = {
                    Toast.makeText(this,"Item added to favorite", Toast.LENGTH_LONG).show()
                } , onFailureListener = {

                })
            }
        }
    }
    fun addToCardButton(){
        addToCard(room, onSuccessListener = {
            MaterialAlertDialogBuilder(this).setMessage("SUCCESSFULLY ADDED !!")
                .setPositiveButton("yes"){ dialog, which->
                    dialog.dismiss()
                }.show()

        }, onFailureListener = {
            Toast.makeText(this,"please,try again !!", Toast.LENGTH_LONG).show()
        })
    }
}