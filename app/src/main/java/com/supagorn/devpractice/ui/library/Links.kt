package com.supagorn.devpractice.ui.library

/**
 * Created by apple on 2/24/2018 AD.
 */
class Links {

    companion object {
        fun getLinks(): MutableList<String> {
            val links = mutableListOf<String>()
            links.add("https://github.com/mmin18/RealtimeBlurView")
            links.add("https://www.androidhive.info/2018/01/android-content-placeholder-animation-like-facebook-using-shimmer/")
            links.add("https://github.com/LeonardoCardoso/Android-Link-Preview")
            links.add("https://www.androidhive.info/2017/12/android-easy-runtime-permissions-with-dexter/")

            return links
        }
    }
}