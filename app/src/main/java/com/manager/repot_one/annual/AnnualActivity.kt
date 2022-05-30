package com.manager.repot_one.annual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.manager.repot_one.ReportOneActivity
import com.user.R

class AnnualActivity : AppCompatActivity() {
    lateinit var cardBack:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annual)
        cardBack = findViewById(R.id.cardBack)
        cardBack.setOnClickListener {
            backingFunction()
        }
    }

    private fun backingFunction() {
        val intent = Intent (this,ReportOneActivity::class.java)
        startActivity(intent)
    }
}