package com.manager.repot_one

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manager.main.ManagerActivity
import com.manager.repot_one.annual.AnnualActivity
import com.user.R

class ReportOneActivity : AppCompatActivity() {
    lateinit var iconApps :FloatingActionButton
    lateinit var cardBack:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_one)
        iconApps = findViewById(R.id.iconApps)
        iconApps.setOnClickListener {
            intentFunction()
        }
        cardBack = findViewById(R.id.cardBack)
        cardBack.setOnClickListener{
            backFunction()
        }
    }

    private fun backFunction() {
        val intent = Intent(this,ManagerActivity::class.java)
        startActivity(intent)
    }

    private fun intentFunction() {
        val intent = Intent(this,AnnualActivity::class.java)
        startActivity(intent)
    }
}