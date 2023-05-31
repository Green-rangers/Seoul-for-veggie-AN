package com.greenranger.seoulforveggi.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.greenranger.seoulforveggi.Constants.GOOGLE_CLIENT_ID
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.data.model.request.LoginRequest
import com.greenranger.seoulforveggi.data.network.LoginService
import com.greenranger.seoulforveggi.databinding.ActivitySigninBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SigninActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123 // Specify your desired request code here
    }

    private lateinit var binding: ActivitySigninBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var retService: LoginService
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEmail = GlobalApplication.prefs.getString("userEmail", "null")

        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(LoginService::class.java)

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
                    val profilePhotoUrl = it.photoUrl

                    GlobalApplication.prefs.setString("userEmail", email.toString())
                    GlobalApplication.prefs.setString("userProfilePhotoUrl", profilePhotoUrl.toString())

                    Log.d("SigninActivity successful", "idToken: $idToken ,Authorization Code: $authorizationCode ,email: $email, profilePhotoUrl: $profilePhotoUrl")
                    // Perform any additional actions or API calls with the obtained information
                    Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        login(email.toString(), profilePhotoUrl.toString())
                    }

                    // Load and display the profile photo using an image loading library like Glide or Picasso
                }
                Log.d("SigninActivity successful", "Google Sign-In was successful!")
            } else {
                // Sign-in failed, handle the exception
                val exception = completedTask.exception
                Log.e("SigninActivity", "Google Sign-In failed: ${exception?.localizedMessage}")
                Toast.makeText(this, "Sign-in failed!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            // Handle ApiException
            val errorMessage = e.localizedMessage ?: "Unknown error"
            Log.e("SigninActivity", "Google Sign-In failed: ${e.statusCode}, $errorMessage")
            Toast.makeText(this, "Sign-in failed!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e("SigninActivity", "Error during Google Sign-In: ${e.message}")
            Toast.makeText(this, "Sign-in failed!", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun login(email: String, imageLink: String) {
        val requestBody = LoginRequest(email, imageLink)

        try {
            val response = retService.login(requestBody)
            if (response.isSuccessful) {
                // 요청 성공
                Log.d("로그인 정보 통신 성공, 요청 성공", response.toString())
                Log.d("로그인 정보 통신 성공, 요청 성공", response.body().toString())
                //acessToken보내기
                val myAccessToken = response.body()?.accessToken

                // useraccesstoken 속성 사용
                val accessToken = myAccessToken.toString()
                GlobalApplication.prefs.setString("userAccessToken", accessToken)
                // accessToken 변수 출력
                println("Access token: $accessToken")

                userEmail = GlobalApplication.prefs.getString("userEmail", "null")

                if(userEmail == "null") {
                    //SignUpActivity로 이동
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                } else {
                    //MainActivity로 이동
                    val intent = Intent(this, SignUpActivity::class.java)
                    //아래가 찐
//                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }

            } else {
                // 요청 실패
                Log.d("로그인 통신 성공, 요청 실패", response.toString())
                Log.d("로그인 통신 성공, 요청 실패", response.body().toString())
            }
        } catch (e: Exception) {
            // 네트워크 에러 또는 예외 발생
            Log.d("로그인 통신 실패", "전송 실패")
        }
    }
}