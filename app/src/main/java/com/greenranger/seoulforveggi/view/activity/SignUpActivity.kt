package com.greenranger.seoulforveggi.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.model.request.SignupRequest
import com.greenranger.seoulforveggi.data.model.response.NicknameResponse
import com.greenranger.seoulforveggi.data.network.LoginService
import com.greenranger.seoulforveggi.databinding.ActivitySignUpBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.fragment.CountryBottomSheetFragment
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private lateinit var retService: LoginService
    var check1 = false
    var check2 = false
    private val check1LiveData = MutableLiveData<Boolean>()
    private val check2LiveData = MutableLiveData<Boolean>()
    private var InputNickname = ""
    private var accessToken = ""
    private var resultCountry = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accessToken = GlobalApplication.prefs.getString("userAccessToken", "")

        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(LoginService::class.java)


        val colorSpan = SpannableStringBuilder(getString(R.string.signup_title)) // 수정된 부분

        // text 색상 변경
        colorSpan.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.green_main)),
            0, // 시작 인덱스
            16, // 끝 인덱스
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signUpTitle.text = colorSpan

        //bottom sheet
        binding.countrySpinner.setOnClickListener {
            val bottomSheet = CountryBottomSheetFragment()
            supportFragmentManager.setFragmentResultListener(
                "requestKey",
                this
            ) { requestKey, result ->
                resultCountry = result.getString("bundleKey").toString()
                // Do something with the result...
                binding.myCountry.text = resultCountry
                check2LiveData.value = true
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        //닉네임 중복 체크
        binding.duplicateCheck.setOnClickListener {
            //닉네임 중복 체크
            inputNicknameCheck()
        }

        // LiveData check1, check2가 변경될 때 마다 updateDoneButton 호출
        check1LiveData.observe(this, Observer {
            updateDoneButton()
        })
        check2LiveData.observe(this, Observer {
            updateDoneButton()
        })

    }

    private fun inputNicknameCheck() {
        binding.apply {
            InputNickname = enterNick.text.toString()
        }
        getRequestWithPathParameters(InputNickname)

    }

    private fun getRequestWithPathParameters(inputText: String) {
        val duplicateResponse: LiveData<Response<NicknameResponse>> = liveData {
            val response = retService.nicknameCheck(inputText)
            emit(response)
        }

        duplicateResponse.observe(this, Observer {
            val check = it.body()?.isDuplicated
            if (check == false) {
                binding.duplicateCheck.setImageResource(R.drawable.ic_checkmark)
                Toast.makeText(this@SignUpActivity, "Nickname you can use.", Toast.LENGTH_LONG).show()
                check1LiveData.value = true

            } else {
                Toast.makeText(this@SignUpActivity, "An unavailable nickname.", Toast.LENGTH_LONG).show()
                check1LiveData.value = false
            }
        })
    }

    private fun updateDoneButton() {
        val check1 = check1LiveData.value ?: false
        val check2 = check2LiveData.value ?: false

        if (check1 && check2) {
            binding.btnDone.setImageResource(R.drawable.btn_signup_on)
            binding.btnDone.setOnClickListener {
                // resultCountry, InputNickname 서버에 post
                lifecycleScope.launch {
                    postData(accessToken, resultCountry, InputNickname)
                }
            }
        } else {
            binding.btnDone.setImageResource(R.drawable.btn_signup_off)
            binding.btnDone.setOnClickListener {
                Toast.makeText(this, "Confirm your input information.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //서버에게 닉네임, 나라 값 전달
    suspend fun postData(accessToken: String, resultCountry: String, inputNickname: String) {
        try {
            val requestBody = SignupRequest(resultCountry, inputNickname)
            val response = retService.signUp("Bearer $accessToken", requestBody)

            if (response.isSuccessful) {
                // 요청 성공
                val signupResponse = response.body()
                Log.d("로그인 추가정보 통신 성공, 요청 성공", response.toString())
                Log.d("로그인 추가정보 통신 성공, 요청 성공", signupResponse.toString())

                GlobalApplication.prefs.setString("userCountry", signupResponse?.country ?: "")
                GlobalApplication.prefs.setString("userNickname", signupResponse?.nickname ?: "")

                Toast.makeText(this, "Complete your registration", Toast.LENGTH_SHORT).show()
                //HomeActivity로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            } else {
                // 요청 실패
                Toast.makeText(this, "Sign up failed.", Toast.LENGTH_SHORT).show()
                Log.d("로그인 추가정보 통신 성공, 요청 실패", response.toString())
                Log.d("로그인 추가정보 통신 성공, 요청 실패", response.body().toString())
            }
        } catch (e: Exception) {
            // 네트워크 에러 또는 예외 발생
            Toast.makeText(this, "Sign up failed.", Toast.LENGTH_SHORT).show()
            Log.d("로그인 추가정보 통신 실패", "전송 실패")
        }
    }
    // 키보드 숨기기 함수
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    // Dispatch touch event for hiding keyboard when touching outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            currentFocus?.let { focus ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focus.windowToken, 0)
                focus.clearFocus()
            }
        }
        return super.dispatchTouchEvent(event)
    }
}

