package com.user.main.ui.home.add_card.moving

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.admin.model.Teacher
import com.user.R
import com.user.main.ui.home.ConstantCollection
import com.user.main.ui.home.add_card.moving.address.AddressPaiementActivity

class ImmediateOrVisa : AppCompatActivity() {
    lateinit var room:Teacher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  COLLECTION_IMMEDIATE_OR_VISA
        room = intent.getParcelableExtra(ConstantCollection.COLLECTION_IMMEDIATE_OR_VISA)!!
        setContentView(R.layout.activity_immediate_or_visa)
        val receiving = findViewById<CardView>(R.id.openDetailsReceivingCardView)
        receiving.setOnClickListener {
            startChatActiviy(room)
        }
    }
    private fun startChatActiviy(room:Teacher) {
        val intent = Intent(this, AddressPaiementActivity::class.java)
        // send data
        intent.putExtra(ConstantCollection.AddressPaiementActivity, room)
        startActivity(intent)
    }
}