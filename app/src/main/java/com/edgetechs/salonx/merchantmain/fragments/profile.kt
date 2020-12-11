package com.edgetechs.salonx.merchantmain.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.edgetechs.salonx.R

class profile: Fragment() {
    lateinit var fullnamehead :TextView
    lateinit var username :TextView
    lateinit var mobileno :TextView
    lateinit var emailadd :TextView
    lateinit var pass :TextView
    lateinit var fullname :TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        fullnamehead=view.findViewById(R.id.textView17)
        username=view.findViewById(R.id.txtuser)
        mobileno=view.findViewById(R.id.mob)
        emailadd=view.findViewById(R.id.txtmail)
        pass=view.findViewById(R.id.passw)
        fullname=view.findViewById(R.id.txtfullname)


        val preferences = this.activity!!.getSharedPreferences("theKey", Context.MODE_PRIVATE)
       val value1 =  preferences.getString("fullname","0").toString()
        val value2 =  preferences.getString("username","0").toString()
        val value3 =  preferences.getString("emailAddress","0").toString()
        val value4 =  preferences.getString("mobile","0").toString()
        val value5 =  preferences.getString("password","0").toString()
        fullnamehead.text=value1
        fullname.text=value1
        username.text=value2
        emailadd.text=value3
        pass.text=value5
        mobileno.text=value4


        return view
    }


}