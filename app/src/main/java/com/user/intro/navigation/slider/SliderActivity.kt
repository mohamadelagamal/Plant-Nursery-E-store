package com.user.intro.navigation.slider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.user.R
import com.user.account.register.RegisterActivity
import com.user.intro.navigation.adapter.SliderAdapter

class SliderActivity : AppCompatActivity() {
    lateinit var sliderView:SliderView
    lateinit var sliderAdapter: SliderAdapter
    lateinit var skip:TextView
    lateinit var nextPage:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        sliderView = findViewById(R.id.imageSlider)
        sliderAdapter = SliderAdapter()
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        skip= findViewById(R.id.skipSlider)
        skip.setOnClickListener {
            val intent = Intent (this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        nextPage = findViewById(R.id.nextSlider)
        nextPage.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}