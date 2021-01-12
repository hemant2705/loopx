package com.edgetechs.salonx.usermain.userFrags

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.edgetechs.salonx.R
import com.edgetechs.salonx.common.loginactivity.user.userLogin


class userOffers : Fragment() {

    lateinit var  signin : Button
    var status = false
    lateinit var dialog : RelativeLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_offers, container, false)

        dialog=view.findViewById(R.id.dialogView)
        val pref=activity!!.getSharedPreferences("theKey", Context.MODE_PRIVATE)
        status=pref.getBoolean("status",false)
        if (!status) {

            signin = view.findViewById(R.id.btnSign)
            signin.setOnClickListener {
                val intent = Intent(activity, userLogin::class.java)
                startActivity(intent)
            }

        }
        else{ dialog.visibility=View.GONE}

        return view
    }


}