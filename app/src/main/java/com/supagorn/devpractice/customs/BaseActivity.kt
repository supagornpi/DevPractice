package com.supagorn.devpractice.customs

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.supagorn.devpractice.utils.LanguageHelper

abstract class BaseActivity : AppCompatActivity() {

    // override the base context of application to update default locale for this activity
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(base))
    }
}