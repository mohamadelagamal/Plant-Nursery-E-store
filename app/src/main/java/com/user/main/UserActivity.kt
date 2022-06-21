package com.user.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.user.R
import com.user.account.sign.LoginActivity
import com.user.databinding.ActivityHomeBinding
import com.user.main.ui.UserActvityViewModel
import com.user.main.ui.account.AccountFragment
import com.user.main.ui.home.HomeFragment
import com.user.main.ui.love.LoveFragment


class UserActivity : BaseActivity<ActivityHomeBinding,UserActvityViewModel>() {

    val auth = FirebaseAuth.getInstance()
    lateinit var drawerlayout: DrawerLayout
    //.........
    lateinit var homeLinear: LinearLayout
    lateinit var favoriteLinear: LinearLayout
    lateinit var accountLinear:LinearLayout
    lateinit var showDrawable:ImageView
    lateinit var item_navigation : BottomNavigationView
    val homeFragment = HomeFragment()
    val loveFragment = LoveFragment()
    val accountFragment = AccountFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushfragment(homeFragment)
        intItems()
        makeItemNavigation()

    }

    private fun intItems() {
        drawerlayout = findViewById(R.id.drawer_layout)
        accountLinear = findViewById(R.id.accountLinear)
        favoriteLinear = findViewById(R.id.favoriteLinear)
        homeLinear = findViewById(R.id.homeLinear)
        showDrawable = findViewById(R.id.menuAppbar)
        setDrawableItems()
    }

    private fun setDrawableItems() {
        accountLinear.setOnClickListener {
            pushfragment(accountFragment)
            drawerlayout.close()
        }
        homeLinear.setOnClickListener {
            pushfragment(homeFragment)
            drawerlayout.close()
        }
        favoriteLinear.setOnClickListener {
            pushfragment(loveFragment)
            drawerlayout.close()
        }
        showDrawable.setOnClickListener {
            drawerlayout.open()
        }
//        moreCategories.setOnClickListener{
//            showPopuptwo(it)
//        }
    }

    private fun dosomething() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.user, menu)
//        return true
//    }
    fun makeItemNavigation(){
        item_navigation = findViewById(R.id.bottonNavigation)
        item_navigation.setOnItemSelectedListener OnItemSelectedListener@{
            when (it.itemId) {
                R.id.homeNavigation -> {
                    pushfragment(homeFragment)
                }
                R.id.favoriteNavigation -> {
                    pushfragment(loveFragment)
                }
                R.id.accountNavigation -> {
                    pushfragment(accountFragment)
                }
            }
            return@OnItemSelectedListener true
        }
    }
    private fun pushfragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.Fragment_Container , fragment).commit()
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_home
    }
    override fun makeViewModelProvider(): UserActvityViewModel {
        return ViewModelProvider(this).get(UserActvityViewModel::class.java)
    }

}