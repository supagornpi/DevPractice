package com.supagorn.devpractice

import android.app.Application
import android.content.Context
import com.supagorn.devpractice.utils.LanguageHelper

class MyApplication : Application() {

    // override the base context of application to update default locale for the application
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(base, LanguageHelper.getLanguage(base)))
    }
}