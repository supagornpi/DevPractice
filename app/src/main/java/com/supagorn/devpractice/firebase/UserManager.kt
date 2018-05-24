package com.supagorn.devpractice.firebase

import com.google.firebase.auth.FirebaseAuth

object UserManager {

    fun isLogin(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun getUid(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}