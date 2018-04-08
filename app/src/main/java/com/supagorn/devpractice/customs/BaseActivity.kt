package com.supagorn.devpractice.customs

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.supagorn.devpractice.utils.LanguageHelper

abstract class BaseActivity : AppCompatActivity() {

    // override the base context of application to update default locale for this activity
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(base))
    }
}