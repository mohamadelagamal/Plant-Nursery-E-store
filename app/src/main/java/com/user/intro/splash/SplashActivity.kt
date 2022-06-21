package com.user.intro.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.database.getUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser
import com.model.DataUtil
import com.user.R
import com.user.intro.navigation.slider.SliderActivity
import com.user.main.UserActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
        val image = findViewById(R.id.logoSplash) as ImageView
        image.startAnimation(animation)
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginInFirebase()

        },2000)
    }
    private fun checkLoginInFirebase() {
        val firebaseUser = Firebase.auth.currentUser
        when{
            firebaseUser==null->{
                openLoginAccount()
            }
            else->{
                getUser(firebaseUser.uid, OnSuccessListener {
                    val user = it.toObject(ApplicationUser::class.java)
                    DataUtil.user=user
                    openHome()
                }, OnFailureListener {
                    openLoginAccount()
                })
            }
        }
    }

    private fun openHome() {
        val intent = Intent(this,UserActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openLoginAccount() {
        val intent = Intent (this, SliderActivity::class.java)
        startActivity(intent)
        finish()
    }
}