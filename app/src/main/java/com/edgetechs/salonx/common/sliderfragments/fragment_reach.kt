package com.edgetechs.salonx.common.sliderfragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.edgetechs.salonx.common.loginactivity.MainActivity
import com.edgetechs.salonx.R

class fragment_reach:Fragment() {
      lateinit var skip:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_reach, container, false)
        skip=view.findViewById(R.id.skip)
        skip.setOnClickListener{
            val intent= Intent( activity, MainActivity::class.java)
            startActivity(intent)
        }


        return view
    }

}