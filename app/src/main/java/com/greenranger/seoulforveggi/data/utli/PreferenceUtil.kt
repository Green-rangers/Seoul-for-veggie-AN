package com.greenranger.seoulforveggi.data.utli

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil (context: Context){

    // SharedPreferences 인스턴스 생성 : 싱글턴 패턴
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}