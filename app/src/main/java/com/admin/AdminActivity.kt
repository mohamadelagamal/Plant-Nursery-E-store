package com.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.admin.categories.CategoriesActivity
import com.user.R
class AdminActivity : AppCompatActivity() {
    lateinit var categoryCardView: CardView
    lateinit var productCardView: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        categoryCardView = findViewById(R.id.categories)
        categoryCardView.setOnClickListener{
            startCategories()
        }
        categoryCardView = findViewById(R.id.products)
        categoryCardView.setOnClickListener {
        }
    }

    private fun startCategories() {
        val intent = Intent(this, CategoriesActivity::class.java)
        startActivity(intent)
    }

}