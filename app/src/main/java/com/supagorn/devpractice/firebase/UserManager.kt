package com.supagorn.devpractice.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity

object UserManager {

    const val STORAGE_PATH_PROFILE = "profiles/"

    private val reference = FirebaseDatabase.getInstance().reference
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    fun createUsernameWithEmail(email: String): String {
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
        val username = UserManager.createUsernameWithEmail(email)

        val user = User(username, email)
                .setFirstName(entity.firstName)
                .setLastName(entity.lastName)
                .setMobile(entity.mobile)
                .setGender(entity.gender)
        updateUserData(uid, user)
    }

    fun updateUserImage(upload: Upload) {
        reference.child("user-images").child(uid).setValue(upload)
    }
}