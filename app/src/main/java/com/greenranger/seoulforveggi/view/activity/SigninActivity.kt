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
import com.greenranger.seoulforveggi.retrofit.APIS
import com.greenranger.seoulforveggi.retrofit.RetrofitClient

class SigninActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123 // Specify your desired request code here
    }

    private lateinit var binding: ActivitySigninBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var retService: APIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(APIS::class.java)

        // Configure Google Sign-In options

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_CLIENT_ID)
            .requestEmail()
            .requestServerAuthCode(GOOGLE_CLIENT_ID, true)
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleLogin.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            if (completedTask.isSuccessful) {
                val account = completedTask.getResult(ApiException::class.java)
                // Sign-in successful, handle the account details
                account?.let {
                    // Obtain the required information from the account
                    val idToken = it.idToken
                    val authorizationCode = it.serverAuthCode
                    val email = it.email
                    val displayName = it.displayName
                    val profilePhotoUrl = it.photoUrl

                    Log.d("SigninActivity successful", "idToken: $idToken ,Authorization Code: $authorizationCode ,email: $email, profilePhotoUrl: $profilePhotoUrl")
                    // Perform any additional actions or API calls with the obtained information

                    // Load and display the profile photo using an image loading library like Glide or Picasso


                }
                Log.d("SigninActivity successful", "Google Sign-In was successful!")
            } else {
                // Sign-in failed, handle the exception
                val exception = completedTask.exception
                Log.e("SigninActivity", "Google Sign-In failed: ${exception?.localizedMessage}")
            }
        } catch (e: ApiException) {
            // Handle ApiException
            val errorMessage = e.localizedMessage ?: "Unknown error"
            Log.e("SigninActivity", "Google Sign-In failed: ${e.statusCode}, $errorMessage")
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e("SigninActivity", "Error during Google Sign-In: ${e.message}")
        }
    }
}
