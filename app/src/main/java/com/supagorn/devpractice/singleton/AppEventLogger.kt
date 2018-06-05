package com.supagorn.devpractice.singleton

import com.facebook.appevents.AppEventsLogger
import com.supagorn.devpractice.MyApplication

class AppEventLogger {
    companion object {
        @Volatile
        private var INSTANCE: AppEventsLogger? = null

        private fun getInstance(): AppEventsLogger = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AppEventsLogger.newLogger(MyApplication.instance).also { INSTANCE = it }
        }

        fun logEvent(eventName: String) {
            getInstance().logEvent(eventName)
        }
    }
}