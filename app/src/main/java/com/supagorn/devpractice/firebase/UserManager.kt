package com.supagorn.devpractice.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity

object UserManager {

    var reference = FirebaseDatabase.getInstance().reference

    fun isLogin(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun getUid(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun splitUsername(email: String): String {
        var username = email
        if (email.contains("@")) {
            username = email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }
        return username
    }

    fun updateUserData(uid: String, user: User) {
        reference.child("users").child(uid).setValue(user)
    }

    fun updateUserData(uid: String, entity: RegisterEntity) {
        val email = entity.email
        val username = UserManager.splitUsername(email)

        val user = User(username, email)
                .setFirstName(entity.firstName)
                .setLastName(entity.lastName)
                .setMobile(entity.mobile)
                .setGender(entity.gender)
        updateUserData(uid, user)
    }
}