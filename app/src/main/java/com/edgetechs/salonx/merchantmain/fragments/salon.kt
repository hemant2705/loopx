package com.edgetechs.salonx.merchantmain.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.edgetechs.salonx.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class salon : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var Salonnamehead :TextView
    lateinit var username :TextView
    lateinit var salonno :TextView
    lateinit var add :TextView
    lateinit var website :TextView
    lateinit var type :TextView
    lateinit var Salonname :TextView
    var sln =LatLng(0.0,0.0)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_salon, container, false)

        Salonnamehead=view.findViewById(R.id.textView17)
        username=view.findViewById(R.id.textView18)
        Salonname=view.findViewById(R.id.salonName)
        add=view.findViewById(R.id.salonAddress)
        salonno=view.findViewById(R.id.salonNumber)
        type=view.findViewById(R.id.type)
        website=view.findViewById(R.id.salonWebsite)

        val sharedPreferences=this.activity!!.getSharedPreferences("theKey",Context.MODE_PRIVATE)

        val value1 = sharedPreferences.getString("salonName","0").toString()
        val value2 = sharedPreferences.getString("salonAddress","0").toString()
        val value3 = sharedPreferences.getString("salonPhone","0").toString()
        val value4 = sharedPreferences.getString("salonWebsite","0").toString()
        val value5 = sharedPreferences.getString("salonType","0").toString()
        val value8 =sharedPreferences.getString("username","0").toString()

        val value6 = sharedPreferences.getString("latitude","0")!!.toDouble()
        val value7 = sharedPreferences.getString("longitude","0")!!.toDouble()

      sln= LatLng(value6,value7)
      Salonnamehead.text=value1
        Salonname.text=value1
        username.text=value8
        add.text=value2
        salonno.text=value3
        website.text=value4
        type.text=value5

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment

        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sln).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sln))
    }


}