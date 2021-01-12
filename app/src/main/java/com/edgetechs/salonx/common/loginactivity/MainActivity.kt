package com.edgetechs.salonx.common.loginactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.edgetechs.salonx.R
import com.edgetechs.salonx.common.loginactivity.merchant.merchantSignUp1
import com.edgetechs.salonx.merchantmain.MainActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    lateinit var phone :TextInputLayout
    lateinit var pass :TextInputLayout
    lateinit var finalphone :String
    lateinit var finalpass:String
    lateinit var textSign : Button
    lateinit var btn :Button
    lateinit var loader: LazyLoader



    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3: String
    lateinit var str4: String
    lateinit var str5: String

    lateinit var stg1: String
    lateinit var stg2: String
    lateinit var stg3: String
    lateinit var stg4: String
    lateinit var stg5: String
    lateinit var stg6 :String

    lateinit var lat:String
    lateinit var long:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        textSign = findViewById(R.id.txtsgn)
        phone = findViewById(R.id.phones)
        pass = findViewById(R.id.pass)
        btn = findViewById(R.id.button2)
        loader=findViewById(R.id.loader)

        loader.visibility = View.INVISIBLE
        val ntent=Intent(this,MainActivity::class.java)




        btn.setOnClickListener {
            finalphone = phone.editText!!.text.toString()
            finalpass = pass.editText!!.text.toString()
            loader.visibility = View.VISIBLE
            val checkuser =
                FirebaseDatabase.getInstance().getReference("Merchant").orderByChild("mobile")
                    .equalTo(finalphone)
            checkuser.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
               Toast.makeText(this@MainActivity,"$error",Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                 if (snapshot.exists())
                 {
                     phone.error=null
                     phone.isErrorEnabled=false

                     val pa = snapshot.child(finalphone).child("password").getValue().toString()
                         if (pa.equals(finalpass)){
                             pass.error=null
                             pass.isErrorEnabled=false


                             str1=snapshot.child(finalphone).child("fullname").getValue().toString()
                             str2=snapshot.child(finalphone).child("username").getValue().toString()
                             str3=snapshot.child(finalphone).child("emailAddress").getValue().toString()
                             str4=snapshot.child(finalphone).child("mobile").getValue().toString()
                             str5=snapshot.child(finalphone).child("password").getValue().toString()

                             stg1=snapshot.child(finalphone).child("salonName").getValue().toString()
                             stg2=snapshot.child(finalphone).child("salonAddress").getValue().toString()
                             stg3=snapshot.child(finalphone).child("salonWebsite").getValue().toString()
                             stg4=snapshot.child(finalphone).child("salonPhone").getValue().toString()
                             stg5=snapshot.child(finalphone).child("salonType").getValue().toString()
                             stg6=snapshot.child(finalphone).child("salonDescription").getValue().toString()

                             lat=snapshot.child(finalphone).child("latitude").getValue().toString()
                             long=snapshot.child(finalphone).child("longitude").getValue().toString()

                             val sharedPref = getSharedPreferences("theKey", Context.MODE_PRIVATE)
                             val editor = sharedPref.edit()
                             editor.putString("fullname", str1)
                             editor.putString("username", str2)
                             editor.putString("emailAddress", str3)
                             editor.putString("mobile",str4)
                             editor.putString("password", str5)
                             editor.putString("salonName", stg1)
                             editor.putString("salonAddress", stg2)
                             editor.putString("salonWebsite", stg3)
                             editor.putString("salonType", stg5)
                             editor.putString("salonPhone", stg4)
                             editor.putString("salonDescription", stg6)
                             editor.putString("latitude", lat)
                             editor.putString("longitude", long)
                             editor.apply()


                             startActivity(ntent)
                         }
                         else{
                             loader.visibility = View.INVISIBLE
                             Toast.makeText(this@MainActivity,"Password Invalid",Toast.LENGTH_SHORT).show()

                         }
                     }

                    else
                 {
                     loader.visibility = View.INVISIBLE
                     Toast.makeText(this@MainActivity,"No such user found",Toast.LENGTH_SHORT).show()
                 }
                }


            })}



            textSign.setOnClickListener {
                val intent = Intent(this, merchantSignUp1::class.java)
                startActivity(intent)
            }

    }

    override fun onBackPressed() {
        finishAffinity()
    }
}

