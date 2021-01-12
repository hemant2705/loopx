package com.edgetechs.salonx.common.loginactivity.user

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.edgetechs.salonx.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main2.*

class userSignup : AppCompatActivity() {
    lateinit var fullname :TextInputLayout
    lateinit var mobile :TextInputLayout
    lateinit var password :TextInputLayout
    lateinit var signup :Button

    lateinit var str1 :String
    lateinit var str2 :String
    lateinit var str3 :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signup)
       fullname= findViewById(R.id.username)
        mobile= findViewById(R.id.phones)
        password= findViewById(R.id.pass)

        signup=findViewById(R.id.button2)
        signup.setOnClickListener {
            str1 = fullname.editText!!.text.toString()
            str2 = mobile.editText!!.text.toString()
            str3 = password.editText!!.text.toString()

            val sharedPreferences=getSharedPreferences("userKey", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("fullname", str1)
            editor.putString("mobile", str2)
            editor.putString("pass", str3)
            editor.apply()
            val intent=Intent(this,userOtp::class.java)
            startActivity(intent)
        }
    }

}