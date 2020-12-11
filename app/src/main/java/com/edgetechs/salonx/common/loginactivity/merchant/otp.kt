package com.edgetechs.salonx.common.loginactivity.merchant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.edgetechs.salonx.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class otp : AppCompatActivity() {
    lateinit var pinFromUser: PinView
    lateinit var Login: Button
    lateinit var phone: String
    lateinit var codeBySystem:String
    lateinit var auth: FirebaseAuth
    lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var cdm: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        pinFromUser = findViewById(R.id.pinView)
        Login = findViewById(R.id.submit)

        val sharedPreferences = getSharedPreferences("myKey", Context.MODE_PRIVATE)
        phone = "+91" + sharedPreferences.getString("mobile", "0").toString()
        sendVerificationCodeToUser(phone)
        Login.setOnClickListener {
           val io = pinFromUser.text.toString()

            verifyCode(io)

        }
    }

    private fun sendVerificationCodeToUser(phoneNo: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNo,  // Phone number to verify
            60,  // Timeout duration
            TimeUnit.SECONDS,  // Unit of timeout
            this,  // Activity (for callback binding)
            mCallbacks
        ) // OnVerificationStateChangedCallbacks
    }
    private val mCallbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                s: String,
                forceResendingToken: ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                codeBySystem = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                Toast.makeText(this@otp, "Otp sent succesfully", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@otp, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(codeBySystem, code)
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    //Verification completed successfully here Either
                    // store the data or do whatever desire

                    val intent =Intent(this, confirmation2::class.java)
                    startActivity(intent)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        Toast.makeText(
                            this@otp,
                            "Verification Not Completed! Try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}
      /*  Login.setOnClickListener{
            var otp=userpin.text.toString().trim()
            if(!otp.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
            }
        }



    private fun sendVerificationcode(phone: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
   /* private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, confirmation::class.java))
                    finish()
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }*/

}*/
