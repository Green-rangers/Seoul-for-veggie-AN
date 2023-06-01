package com.greenranger.seoulforveggi.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.greenranger.seoulforveggi.R

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000 // 스플래시 화면이 표시될 시간 (3초)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 지정된 시간 후에 다음 액티비티로 전환하기 위한 핸들러 생성
        Handler().postDelayed({
            // 스플래시 화면이 표시된 후에 실행될 코드
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // 스플래시 액티비티를 종료하여 사용자가 뒤로 가기 버튼을 눌렀을 때 다시 스플래시 화면이 나타나지 않도록 함
        }, SPLASH_TIME_OUT)
    }
}