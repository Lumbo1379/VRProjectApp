package com.example.vrproject.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vrproject.R
import com.example.vrproject.controllers.LoginActivity
import com.example.vrproject.controllers.MainActivity
import com.example.vrproject.helpers.UserHelper
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    private val RC_SIGN_IN = 123

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
    }

    private fun configureView() {
        val user = FirebaseAuth.getInstance().currentUser ?: return

        Glide.with(this)
            .load(user.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(fragment_account_image_profile)

        fragment_account_text_name.text = user.displayName
        fragment_account_text_id.text = "Id: " + user.uid

        fragment_account_delete_account.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Confirm")
                .setMessage("Do you really want to delete your account as it will also remove your data, and as a result it will not be used in the project?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    startSignInActivity()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                    Toast.makeText(context, "Account deletion cancelled", Toast.LENGTH_SHORT).show()
                })
                .show()
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
                val user = FirebaseAuth.getInstance().currentUser ?: return

                UserHelper.deleteUser(user.uid).addOnCompleteListener {
                    user.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Account successfully deleted", Toast.LENGTH_SHORT).show()

                            val intent = Intent(context, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            } else {
                when {
                    response == null -> { // Back out, back button
                        Toast.makeText(context, "Login cancelled...", Toast.LENGTH_SHORT).show()
                    }
                    response.error?.errorCode == ErrorCodes.NO_NETWORK -> {
                        Toast.makeText(context, "You are not connected to the Internet", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}