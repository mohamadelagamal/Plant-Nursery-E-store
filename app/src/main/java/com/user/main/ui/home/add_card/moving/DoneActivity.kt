package com.user.main.ui.home.add_card.moving

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.admin.model.Teacher
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.user.R
import com.user.main.UserActivity
import com.user.main.ui.home.ConstantCollection
import com.user.main.ui.home.HomeFragment

class DoneActivity : AppCompatActivity() {
    lateinit var room:Teacher
    lateinit var salesMoney:TextView
    lateinit var addressDone:TextView
    lateinit var numberItems:TextView
    lateinit var total:TextView
    var counter:Int ?=null
    var price :Int ?=null
    lateinit var buttonDone:Button
    var sumTotal:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(ConstantCollection.DONE_ACTIVITY)!!
        setContentView(R.layout.activity_done)
        addressDone = findViewById(R.id.textAddressDone)
        addressDone.setText(room.address)
        salesMoney = findViewById(R.id.salerySalles)
        salesMoney.setText(room.price)
        numberItems = findViewById(R.id.numberOfItems)
        numberItems.setText(room.counter)
        total = findViewById(R.id.totalOfReslute)
        counter = (room.counter?.toInt())
        price = room.price?.toInt()
        sumTotal = counter!! * price!! + 50
        total.setText(sumTotal.toString())
        buttonDone = findViewById(R.id.buttonDone)
        buttonDone.setOnClickListener {
            donePage()
        }
       // Toast.makeText(this,counter.toString(),Toast.LENGTH_SHORT).show()
    }

    private fun donePage() {
        MaterialAlertDialogBuilder(this).setMessage("SUCCESSFULLY ADDED !!").setCancelable(false)
            .setPositiveButton("yes"){ dialog, which->
                val intent = Intent(this@DoneActivity,UserActivity::class.java)
                startActivity(intent)
            }.show()
    }

}