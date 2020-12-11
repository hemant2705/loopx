package com.edgetechs.salonx.common

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.airbnb.lottie.LottieAnimationView
import com.edgetechs.salonx.R
import com.edgetechs.salonx.common.loginactivity.AskingPage
import com.edgetechs.salonx.merchantmain.MainActivity


class SplashActivity : AppCompatActivity() {
    lateinit var imgLogo: ImageView
    lateinit var splash: Animation
    lateinit var splash2: Animation
    lateinit var bgl: ImageView
    lateinit var loading: LottieAnimationView
    lateinit var prog :LazyLoader
    var value : Boolean=false
    var value1 : Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        splash = AnimationUtils.loadAnimation(this,
            R.anim.splash
        )
        splash2 = AnimationUtils.loadAnimation(this,
            R.anim.splash2
        )




        imgLogo = findViewById(R.id.imgLogo)
        bgl = findViewById(R.id.bgl)
        loading = findViewById(R.id.loadingBox)
        prog=findViewById(R.id.loader)


        imgLogo.animate().translationX(1600f).setDuration(1000).setStartDelay(3000)
        loading.animate().translationX(1600f).setDuration(1000).setStartDelay(3000)
        bgl.animate().translationX(1600f).setDuration(1000).setStartDelay(3000)



        var lazyLoader = LazyLoader(this, 15, 5,
            ContextCompat.getColor(this,
                R.color.loader_selected
            ),
            ContextCompat.getColor(this,
                R.color.loader_selected
            ),
            ContextCompat.getColor(this,
                R.color.loader_selected
            ))
            .apply {
                animDuration = 500
                firstDelayDuration = 100
                secondDelayDuration = 200
                interpolator = DecelerateInterpolator()
            }
        prog.addView(lazyLoader)
         val sharedPreferences=getSharedPreferences("theKey", Context.MODE_PRIVATE)
        value=sharedPreferences.getBoolean("isLogged",false)

       if ( value){
           Handler(Looper.getMainLooper()).postDelayed({
               val intent = Intent(this@SplashActivity, MainActivity::class.java)
               startActivity(intent)
           }, 4000)

       }
        else {
           Handler(Looper.getMainLooper()).postDelayed({
               val intent =
                   Intent(this@SplashActivity, AskingPage::class.java)
               startActivity(intent)
           }, 4000)

       }
    }
}

