package com.user.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.user.R
import com.user.account.register.RegisterActivity
import com.user.account.sign.LoginActivity
import com.user.databinding.ActivityHomeBinding
import com.user.main.ui.UserActvityViewModel
import com.user.main.ui.account.AccountActivity
import com.user.main.ui.explore.ExploreFragment
import com.user.main.ui.home.HomeFragment
import com.user.main.ui.love.LoveFragment


class UserActivity : BaseActivity<ActivityHomeBinding,UserActvityViewModel>() {

    val auth = FirebaseAuth.getInstance()
    lateinit var drawerlayout: DrawerLayout
    //.........
    lateinit var homeLinear: LinearLayout
    lateinit var favoriteLinear: LinearLayout
    lateinit var exploreLinear:LinearLayout
    lateinit var accountLinear:LinearLayout
    lateinit var showDrawable:ImageView
    lateinit var moreCategories:ImageView
    lateinit var item_navigation : BottomNavigationView
    val homeFragment = HomeFragment()
    val exploreFragment = ExploreFragment()
    val loveFragment = LoveFragment()
    val accountFragment = AccountActivity()
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
        exploreLinear = findViewById(R.id.exploreLinear)
        showDrawable = findViewById(R.id.menuAppbar)
        moreCategories = findViewById(R.id.moreApp)
        setDrawableItems()
    }

    private fun setDrawableItems() {
        accountLinear.setOnClickListener {
            pushfragment(accountFragment)
            drawerlayout.close()
        }
        exploreLinear.setOnClickListener {
            pushfragment(exploreFragment)
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
        moreCategories.setOnClickListener{
            showPopuptwo(it)
        }
    }
    private fun showPopuptwo(v: View) {
        PopupMenu(this, v).apply {
            setOnMenuItemClickListener(object: PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        R.id.logoutID -> {
                            dosomething()
                            true
                        }
                        else -> false
                    }
                }
            })
            inflate(R.menu.user)
            show()
        }
    }
    private fun dosomething() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.user, menu)
        return true
    }
    fun makeItemNavigation(){
        item_navigation = findViewById(R.id.bottonNavigation)
        item_navigation.setOnItemSelectedListener OnItemSelectedListener@{
            when (it.itemId) {
                R.id.homeNavigation -> {
                    pushfragment(homeFragment)
                }
                R.id.categoriesNavigation -> {
                    pushfragment(exploreFragment)
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