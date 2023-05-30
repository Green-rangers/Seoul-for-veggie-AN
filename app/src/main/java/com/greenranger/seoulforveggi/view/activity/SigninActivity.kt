package com.greenranger.seoulforveggi.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.greenranger.seoulforveggi.Constants.GOOGLE_CLIENT_ID
import com.greenranger.seoulforveggi.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123 // Specify your desired request code here
    }

    private lateinit var binding: ActivitySigninBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_CLIENT_ID) // Get your client ID
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleLogin.setOnClickListener {
            // Launch the Google Sign-In intent
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            // Handle the result of Google Sign-In
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, handle the account details
            account?.let {
                val idToken = it.idToken // Get the ID token
                val email = it.email // Get the email
                val displayName = it.displayName // Get the display name
                val profilePhotoUrl = it.photoUrl // Get the profile photo URL

                // Perform any additional actions or API calls with the obtained information
                // ...

                // Load and display the profile photo using an image loading library like Glide or Picasso
//                profilePhotoUrl?.let { url ->
                    // Use your preferred image loading library to load and display the image
                    // Example using Glide:
//                    Glide.with(this)
//                        .load(url)
//                        .into(binding.profilePhotoImageView)
//                }
            }
        } catch (e: ApiException) {
            // Sign-in failed, handle the exception
            Log.e("SigninActivity", "Google Sign-In failed: ${e.statusCode}")
        }
    }
}