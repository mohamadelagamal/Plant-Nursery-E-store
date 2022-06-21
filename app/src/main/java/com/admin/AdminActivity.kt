package com.admin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.admin.categories.CategoriesActivity
import com.admin.categories.edit_delete.ShowDataCategories
import com.admin.products.ProductsActivity
import com.admin.products.edit_delete.ProductsShowData
import com.user.R


class AdminActivity : AppCompatActivity() {
    lateinit var categoryCardView: CardView
    lateinit var productCardView: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        categoryCardView = findViewById(R.id.categories)
//        categoryCardView.setOnClickListener{
//            startCategories()
//        }
       productCardView = findViewById(R.id.products)
//        categoryCardView.setOnClickListener {
//            val intent = Intent(this, ProductsActivity::class.java)
//            startActivity(intent)
//        }
        categoryCardView.setOnClickListener {
            val popupMenu = PopupMenu(this, categoryCardView)
            popupMenu.menuInflater.inflate(R.menu.pop_categories, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                  //  Toast.makeText(this@AdminActivity, "You Clicked " + menuItem?.getTitle(), Toast.LENGTH_SHORT).show();
                  if (menuItem?.itemId == R.id.newCategories){
                      startCategories()
                  }
                    if (menuItem?.itemId == R.id.deleteAndEditCategories){
                        val intent = Intent(this@AdminActivity, ShowDataCategories::class.java)
                        startActivity(intent)
                    }
                    return true;
                }

            })
            popupMenu.show();
        }
        productCardView.setOnClickListener {
            val popupMenu = PopupMenu(this, productCardView)
            popupMenu.menuInflater.inflate(R.menu.pop_categories, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                    if (menuItem?.itemId == R.id.newCategories){
                        startProduct()
                    }
                    if (menuItem?.itemId == R.id.deleteAndEditCategories){
                      val intent = Intent(this@AdminActivity, ProductsShowData::class.java)
                      startActivity(intent)
                    }
                    return true;
                }

            })
            popupMenu.show();
        }
    }

    private fun startProduct() {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }

    private fun startCategories() {
        val intent = Intent(this, CategoriesActivity::class.java)
        startActivity(intent)
    }

}