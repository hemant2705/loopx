package com.edgetechs.salonx.common.loginactivity.merchant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.core.content.ContextCompat
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.edgetechs.salonx.R
import com.edgetechs.salonx.common.loginactivity.MainActivity
import com.google.firebase.database.FirebaseDatabase

class confirmation2 : AppCompatActivity() {
    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3: String
    lateinit var str4: String
    lateinit var str5: String
    lateinit var str6:String

    lateinit var value1: String
    lateinit var value2: String
    lateinit var value3: String
    lateinit var value4: String
    lateinit var value5: String

    lateinit var latitude:String
    lateinit var longitude:String
    lateinit var submit:Button
    lateinit var prog :LazyLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation2)

        submit=findViewById(R.id.sub)
        prog=findViewById(R.id.loader1)

        val sharedPref = getSharedPreferences("myKey", Context.MODE_PRIVATE)
        value1 = sharedPref.getString("fullname", "0").toString();
        value2 = sharedPref.getString("username", "0").toString()
        value3 = sharedPref.getString("emailAddress", "0").toString()
        value4 = sharedPref.getString("mobile", "0").toString()
        value5 = sharedPref.getString("password", "0").toString()

        val sharedPrefi = getSharedPreferences("Key", Context.MODE_PRIVATE)
        str1 =sharedPrefi.getString("salonName","0").toString()
        str2=sharedPrefi.getString("salonAddress","0").toString()
        str3=sharedPrefi.getString("salonWebsite","0").toString()
        str4 =sharedPrefi.getString("salonType","0").toString()
        str5 =sharedPrefi.getString("salonPhone","0").toString()
        str6 =sharedPrefi.getString("salonDescription","0").toString()


        val lat=intent.getDoubleExtra("latitude",0.0)
        val lng =intent.getDoubleExtra("longitude",0.0)


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

        submit.setOnClickListener {
            latitude=lat.toString()
            longitude=lng.toString()
        new()
            prog.visibility= View.VISIBLE
        val intent = Intent(this,
            MainActivity::class.java)
            startActivity(intent)
        }

    }
    fun new(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Merchant")
        val addnew: DataHelper =
            DataHelper(
                value1,
                value2,
                value3,
                value4,
                value5,
                str1,
                str2,
                str3,
                str4,
                str5,
                str6,
                latitude,
                longitude
            )
        myRef.child(value4).setValue(addnew)
    }
}