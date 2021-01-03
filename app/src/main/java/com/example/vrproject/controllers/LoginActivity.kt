package com.example.vrproject.controllers

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.vrproject.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("newUser", false) // Use does not need to be readded to database
            startActivity(intent)
        }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        activity_login_google_login_button.setOnClickListener {
            startSignInActivity()
        }
    }

    private fun startSignInActivity() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(mutableListOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                .setIsSmartLockEnabled(false, true)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        handleResponseAfterSignIn(requestCode, resultCode, data)
    }

    private fun handleResponseAfterSignIn(requestCode: Int, resultCode: Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) { // Successful login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                when {
                    response == null -> { // Back out, back button
                        showSnackBar(activity_login_layout, "Login cancelled")
                    }
                    response.error?.errorCode == ErrorCodes.NO_NETWORK -> {
                        showSnackBar(activity_login_layout, "You are not connected to the Internet")
                    }
                    else -> {
                        showSnackBar(activity_login_layout, "Something went wrong...")
                    }
                }
            }
        }
    }

    private fun showSnackBar(layout: FrameLayout, message: String) {
        Snackbar.make(layout, message, Snackbar.LENGTH_SHORT).show()
    }
}