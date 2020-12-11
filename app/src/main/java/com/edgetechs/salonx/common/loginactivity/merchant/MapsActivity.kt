package com.edgetechs.salonx.common.loginactivity.merchant

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.edgetechs.salonx.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var pickBtn: Button
    lateinit var currentBtn :ImageView


    var lat: Double = 0.0
    var clat: Double = 0.0
    var clong: Double = 0.0
    var PERMISSION_ID = 1000

    var lng: Double = 0.0
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest


    fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_COARSE_LOCATION
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
                permission.ACCESS_FINE_LOCATION,
                permission.ACCESS_COARSE_LOCATION
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
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        pickBtn = findViewById(R.id.btnPick)
        currentBtn=findViewById(R.id.currentloc)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        currentBtn.setOnClickListener{
            getLastLocation()
            currentBtn.visibility= View.GONE
        }

        pickBtn.setOnClickListener {

            val intent = Intent(this, otp::class.java)
            intent.putExtra("latitude", lat)
            intent.putExtra("longitude", lng)
            startActivity(intent)

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
                        mMap.isMyLocationEnabled = true
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(set, 15.0F))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(set, 15.0F))


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
          if(checkPermission()) { if (isNetworkEnabled()){mMap.isMyLocationEnabled = true}}
            mMap.addMarker(MarkerOptions().position(set).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(HUE_AZURE)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(set, 15.0F))
            mMap.addCircle(CircleOptions().center(set).radius(4000.0).fillColor(Color.BLUE).strokeColor(Color.BLUE)
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLastLocation()
        // Add a marker in Sydney and move the camera
        if (checkPermission()){
        if (isNetworkEnabled()){
            mMap.isMyLocationEnabled = true
        }}
        mMap.setOnMapClickListener {
            var bir = it
            mMap.clear()
            mMap.addMarker(
                MarkerOptions().position(it).draggable(true).title("Your Shop Location")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            lat = it.latitude
            lng = it.longitude
            Toast.makeText(this@MapsActivity, "$lat,$lng", Toast.LENGTH_SHORT).show()
            //     mMap.setOnMarkerClickListener(this)

            mMap.setOnMarkerDragListener(object : OnMarkerDragListener {
                override fun onMarkerDragStart(marker: Marker) {}
                override fun onMarkerDragEnd(marker: Marker) {
                    lat = marker.position.latitude
                    lng = marker.position.longitude
                    Toast.makeText(this@MapsActivity, "$lat,$lng", Toast.LENGTH_SHORT).show()
                }

                override fun onMarkerDrag(marker: Marker) {}
            })
        }
    }



}
