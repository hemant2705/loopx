package com.edgetechs.salonx.usermain.userFrags

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.edgetechs.salonx.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import java.util.*

class userHome : Fragment() {


    lateinit var addressS:String
    lateinit var locationTab : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_home, container, false)
        val context = inflater.context


        locationTab=view.findViewById(R.id.locationView)

        val pref=activity!!.getSharedPreferences("commonKey",Context.MODE_PRIVATE)
        val clat=pref.getString("lat","0.0")!!.toDouble()
        val clong=pref.getString("long","0.0")!!.toDouble()


        val addresses: List<Address>
                val geocoder: Geocoder = Geocoder(context, Locale.getDefault())

                addresses = geocoder.getFromLocation(
                    clat,
                    clong,
                    1
                ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                val address: String = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                val city: String = addresses[0].getLocality()
                val state: String = addresses[0].getAdminArea()
                val country: String = addresses[0].getCountryName()
                val postalCode: String = addresses[0].getPostalCode()
                val knownName: String = addresses[0].getFeatureName()
                addressS = address + "," + city + "," + state + country + postalCode + knownName
                locationTab.setText(addressS)


        return view
    }


}