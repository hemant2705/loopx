package com.edgetechs.salonx.common.loginactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.edgetechs.salonx.R
import com.edgetechs.salonx.common.sliderfragments.ScreenSliderAdapter

class AskingPage : AppCompatActivity() {
    lateinit var user : LottieAnimationView
    lateinit var vend :LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asking_page)

        user = findViewById(R.id.l1)
        vend = findViewById(R.id.l2)

        vend.setOnClickListener{
            val intent=Intent(this, ScreenSliderAdapter::class.java)
            startActivity(intent)
        }


    }
}