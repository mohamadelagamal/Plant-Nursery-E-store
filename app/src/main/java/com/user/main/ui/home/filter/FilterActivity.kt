package com.user.main.ui.home.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.user.R

class FilterActivity : AppCompatActivity() {
    lateinit var radioButton:RadioButton
    lateinit var radioButtonTwo:RadioButton
    lateinit var radioThree:RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        radioButton = findViewById(R.id.radioPrice)
        radioButtonTwo = findViewById(R.id.radioButtonTwo)
        radioThree = findViewById(R.id.radioButtonThree)
        radioButton.setOnClickListener{
            if (radioButton.isSelected){
                radioButton.isSelected = false
                radioButton.isChecked = false
            }
            else{
                radioButton.isSelected = true
                radioButton.isChecked = true
            }
        }
        radioThree.setOnClickListener{
            if (radioThree.isSelected){
                radioThree.isSelected = false
                radioThree.isChecked = false
            }
            else{
                radioThree.isSelected = true
                radioThree.isChecked = true
            }
        }
        radioButtonTwo.setOnClickListener{
            if (radioButtonTwo.isSelected){
                radioButtonTwo.isSelected = false
                radioButtonTwo.isChecked = false
            }
            else{
                radioButtonTwo.isSelected = true
                radioButtonTwo.isChecked = true
            }
        }
    }
}