package com.supagorn.devpractice.utils

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.*
import com.supagorn.devpractice.R

class ChromeCustomTabs constructor(private var mContext: Context) {

    companion object {
        private var customTabsConnection: CustomTabsServiceConnection? = null
        private var customTabsSession: CustomTabsSession? = null
        @SuppressLint("StaticFieldLeak")
        private var instance: ChromeCustomTabs? = null

        fun getInstance(context: Context): ChromeCustomTabs {
            if (instance == null) { // NOT thread safe!
                instance = ChromeCustomTabs(context)
            }
            if (customTabsConnection == null || customTabsSession == null) {
                instance!!.connectCustomTabsService()
            }
            return instance!!
        }
    }

    fun openCustomTabs(url:String) {
        openCustomTabs(Uri.parse(url))
    }

    fun openCustomTabs(uri: Uri) {
        val builder = CustomTabsIntent.Builder(customTabsSession)
        builder.setShowTitle(true)
        builder.setToolbarColor(mContext.resources.getColor(R.color.colorPrimary))
        builder.addDefaultShareMenuItem()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(mContext, uri)
    }

    private fun connectCustomTabsService() {
        val chromePackageName = "com.android.chrome"
        customTabsConnection = object : CustomTabsServiceConnection() {
            override fun onServiceDisconnected(name: ComponentName) {
                // หยุดเชื่อมต่อ Custom Tabs Service
            }

            override fun onCustomTabsServiceConnected(componentName: ComponentName, customTabsClient: CustomTabsClient) {
                // เชื่อมต่อกับ Custom Tabs Service ได้แล้ว
                createCustomTabsSession(customTabsClient)
            }
        }
        CustomTabsClient.bindCustomTabsService(mContext, chromePackageName, customTabsConnection)
    }

    private fun createCustomTabsSession(customTabsClient: CustomTabsClient) {
        customTabsSession = customTabsClient.newSession(object : CustomTabsCallback() {
            override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
                // เมื่อมี Navigation Event ใดๆเกิดขึ้นบน Custom Tabs
            }
        })
    }
}
