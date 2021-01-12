package com.edgetechs.salonx.common.loginactivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView
import com.edgetechs.salonx.R
import com.edgetechs.salonx.common.sliderfragments.ScreenSliderAdapter
import com.edgetechs.salonx.merchantmain.MainActivity
import com.edgetechs.salonx.usermain.userActivity
import com.google.android.material.textfield.TextInputLayout

class AskingPage : AppCompatActivity() {
    lateinit var userLoginbtn : Button
    lateinit var userSkip :Button
    lateinit var mobile: TextInputLayout


    lateinit var str1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asking_page)

        userLoginbtn = findViewById(R.id.btnLogin)
        userSkip = findViewById(R.id.btnSkip)
        mobile = findViewById(R.id.textInputLayout2)

        userSkip.setOnClickListener{
            val intent=Intent(this, userActivity::class.java)
            startActivity(intent)
        }

        userLoginbtn.setOnClickListener{
            str1 = mobile.editText!!.text.toString()
            if (!validatephone()) {
                return@setOnClickListener
            } else{
                val sharedPref = getSharedPreferences("setupKey", Context.MODE_PRIVATE)
                sharedPref.edit().putString("mobile",str1).apply()
            val intent=Intent(this, verifyLogin::class.java)
            startActivity(intent)}
        }


    }
    fun validatephone(): Boolean {
        val test = mobile.editText!!.text.toString().trim()
        if (test.isEmpty()) {
            mobile.error = "Field cant be empty"
            return false
        } else if (test.length != 10) {
            mobile.error = "Enter a valid Number"
            return false
        } else {
            mobile.error = null
            mobile.isErrorEnabled = false
            return true
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}