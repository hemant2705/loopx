package com.edgetechs.salonx.usermain

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.edgetechs.salonx.R
import com.edgetechs.salonx.common.loginactivity.merchant.otp
import com.edgetechs.salonx.usermain.userFrags.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_brand.*

class userActivity : AppCompatActivity() {


    lateinit var bottomNavigation: BottomNavigationView
    var previousMenuItem: MenuItem? = null
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout


    lateinit var pickBtn: TextView
    var clat: Double = 0.0
    var clong: Double = 0.0
    var PERMISSION_ID = 4000
    lateinit var cnslayout: ConstraintLayout

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest


    fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_ID
        )

    }

    fun isNetworkEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You have the Access")
            }

        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        bottomNavigation=findViewById(  R.id.bottomNavigationView)
        toolbar=findViewById(R.id.toolbar1)
       pickBtn = findViewById(R.id.enter)
       cnslayout=findViewById(R.id.clsplash)




       pickBtn.setOnClickListener {
           getLastLocation()
            Toast.makeText(this,"$clong , $clat",Toast.LENGTH_SHORT).show()

           val pref = getSharedPreferences("commonKey",Context.MODE_PRIVATE)
           pref.edit().putString("lat",clat.toString()).apply()
           pref.edit().putString("long",clong.toString())
               .apply()

            cnslayout.visibility=View.GONE
        }
        openHomefrag()
        setupToolbar()
        getLastLocation()

        bottomNavigation.setOnNavigationItemSelectedListener {

            if(previousMenuItem!=null)
            {
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem =it

            when (it.itemId) {
                R.id.userhome -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame1,
                            userHome()
                        )
                        .addToBackStack("Home")
                        .commit()

                }
                R.id.userappointment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame1,
                            userAppointment()
                        )
                        .addToBackStack("Appointment")
                        .commit()
                    supportActionBar?.title = "My Appointments"

                }
                R.id.userExplore-> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame1,
                            userexplore()
                        )
                        .addToBackStack("explore")
                        .commit()
                    supportActionBar?.title = "Explore"

                }
                R.id.userOffers-> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame1,
                            userOffers()
                        )
                        .addToBackStack("offers")
                        .commit()
                    supportActionBar?.title = "Offers"

                }

                R.id.userprofile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame1,
                            userProfile()
                        )
                        .addToBackStack("profile")
                        .commit()
                    supportActionBar?.title = "My Profile"

                }

            }
            return@setOnNavigationItemSelectedListener  true
        }}
        fun setupToolbar() {
            setSupportActionBar(toolbar)
            supportActionBar?.title = "Toolbar title"

        }


    fun openHomefrag(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame1,
                userHome()
            )
            .addToBackStack("Home")
            .commit()
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame1)
        when (frag) {

            !is userHome -> openHomefrag()

            else -> finishAffinity()
        }
    }


    private fun getLastLocation() {
        if (checkPermission()) {
            if (isNetworkEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    val location: Location = it.result

                    if (location == null) {
                        getNewLocation()
                    } else {
                        clat = location.latitude
                        clong = location.longitude
                        var set = LatLng(clat, clong)
                    }
                }

            } else {
                Toast.makeText(this, "Please Enable Location", Toast.LENGTH_SHORT).show()
            }
        } else (requestPermission())

    }


    private fun getNewLocation() {

        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 1000
        locationRequest.numUpdates = 2
        if (checkPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            var lastlocation = p0.lastLocation
            clat = lastlocation.latitude
            clong = lastlocation.longitude
            var set = LatLng(clat, clong)
          }
    }

}