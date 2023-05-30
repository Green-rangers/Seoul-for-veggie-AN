package com.greenranger.seoulforveggi

import android.app.Application
import com.greenranger.seoulforveggi.data.utli.PreferenceUtil

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}