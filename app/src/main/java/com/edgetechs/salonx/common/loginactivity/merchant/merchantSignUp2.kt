package com.edgetechs.salonx.common.loginactivity.merchant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.edgetechs.salonx.R
import com.google.android.material.textfield.TextInputLayout

class merchantSignUp2 : AppCompatActivity() {
    lateinit var nextu: Button
    lateinit var back: ImageView
    lateinit var salonName: TextInputLayout
    lateinit var salonAddress: TextInputLayout
    lateinit var salonWebsite: TextInputLayout
    lateinit var salonNumber: TextInputLayout
    lateinit var type: RadioGroup
    lateinit var btn: RadioButton
    lateinit var gender: String

    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3: String
    lateinit var str4: String
    lateinit var str5: String

    lateinit var salonDescription: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_merchant_sign_up2)
        nextu = findViewById(R.id.nex)
        back = findViewById(R.id.iconRet)
        salonNumber = findViewById(R.id.salonNumber)
        salonAddress = findViewById(R.id.salonAddress)
        salonName = findViewById(R.id.salonName)
        salonWebsite = findViewById(R.id.salonWebsite)
        salonDescription = findViewById(R.id.salonDescription)
        type = findViewById(R.id.type)


        back.setOnClickListener {
            onBackPressed()
        }


        nextu.setOnClickListener {
            btn = findViewById(type.checkedRadioButtonId)
            gender = btn.text.toString()
            str1 = salonName.editText!!.text.toString()
            str2 = salonAddress.editText!!.text.toString()
            str3 = salonWebsite.editText!!.text.toString()
            str4 = salonNumber.editText!!.text.toString()
            str5 = salonDescription.editText!!.text.toString()


            if (!validateSalon() || !validateSalonAdd() || !validateType()||!validateSalonNum()) {
                return@setOnClickListener
            } else {
                val sharedPrefi = getSharedPreferences("Key", Context.MODE_PRIVATE)
                val editor = sharedPrefi.edit()
                editor.putString("salonName", str1)
                editor.putString("salonAddress", str2)
                editor.putString("salonWebsite", str3)
                editor.putString("salonType", gender)
                editor.putString("salonPhone", str4)
                editor.putString("salonDescription", str5)
                editor.apply()
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun validateSalon(): Boolean {
        val tets: String = salonName.editText!!.text.toString()
        if (tets.isEmpty()) {
            salonName.error = "Enter Salon Name"
            return false
        } else
            return true
    }

    fun validateSalonAdd(): Boolean {
        val tets: String = salonAddress.editText!!.text.toString()
        if (tets.isEmpty()) {
            salonAddress.error = "Enter Salon Name"
            return false
        } else
            return true
    }

    fun validateType(): Boolean {
        if (type.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Enter Salon Type", Toast.LENGTH_SHORT).show()
            return false
        } else
            return true
    }

    fun validateSalonNum(): Boolean {
        val test = salonNumber.editText!!.text.toString().trim()
        if (test.isEmpty()) {
            salonNumber.error = "Field cant be empty"
            return false
        } else if (test.length !== 10) {
            salonNumber.error = "Enter a valid Number"
            return false
        } else {
            salonNumber.error = null
            salonNumber.isErrorEnabled = false
            return true
        }
    }
}