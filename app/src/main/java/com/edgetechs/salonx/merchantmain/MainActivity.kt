package com.edgetechs.salonx.merchantmain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.edgetechs.salonx.common.loginactivity.MainActivity
import com.edgetechs.salonx.R
import com.edgetechs.salonx.merchantmain.fragments.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem : MenuItem? =null
    lateinit var fullname :TextView
    lateinit var mobile :TextView

    lateinit var salonname: TextView
    lateinit var username:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinateLayout)
        frameLayout = findViewById(R.id.frame)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)

      /*  fullname=findViewById(R.id.fullnam)
        mobile=findViewById(R.id.txtMobil)*/

        setupToolbar()
        openHome()


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null)
            {
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem =it

            when (it.itemId) {
                R.id.homes -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            profile()
                        )
                        .addToBackStack("Profile")
                        .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.salon -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            salon()
                        )
                        .addToBackStack("Salon Details")
                        .commit()
                    supportActionBar?.title = "Salon Details"
                    drawerLayout.closeDrawers()
                }
                R.id.rate-> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            ratechart()
                        )
                        .addToBackStack("Rate Chart")
                        .commit()
                    supportActionBar?.title = "Rate Chart"
                    drawerLayout.closeDrawers()
                }

                R.id.faq -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            faqs()
                        )
                        .addToBackStack("FAQs")

                        .commit()
                    supportActionBar?.title = "Bookings"
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {
                    val sharedPreferences= getSharedPreferences("theKey", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("isLogged",false).apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            return@setNavigationItemSelectedListener true
        }
        val sharedPreferences= getSharedPreferences("theKey", Context.MODE_PRIVATE)
        val value1 = sharedPreferences.getString("fullname", "0").toString()
        val value2=sharedPreferences.getString("mobile","0").toString()
        val value3= sharedPreferences.getString("username","0").toString()
        val value4= sharedPreferences.getString("salonName","0").toString()


        sharedPreferences.edit().putBoolean("isLogged",true).apply()


       val navigationView = findViewById(R.id.navigationView) as NavigationView
        val header = navigationView.getHeaderView(0)
        salonname = header.findViewById<View>(R.id.salonnam) as TextView
        username = header.findViewById<View>(R.id.txtuser) as TextView
        salonname.setText(value3)
        username.setText(value3)

    }


    fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openHome() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                home()
            )
            .addToBackStack("Home")
            .commit()
        supportActionBar?.title = "Appointments"
        navigationView.setCheckedItem(R.id.homes)

    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when (frag) {
            !is home -> openHome()
            else -> finishAffinity()

        }
    }


}
