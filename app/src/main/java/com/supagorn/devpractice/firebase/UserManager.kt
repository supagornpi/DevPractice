package com.supagorn.devpractice.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity

class UserManager {

    private var userProfileEventListener: ValueEventListener? = null
    private var userImageEventListener: ValueEventListener? = null

    companion object {
        @Volatile
        var instance: UserManager? = synchronized(this) {
            UserManager().also { instance = it }
        }

        private val mDatabase = FirebaseDatabase.getInstance().reference

        const val STORAGE_PATH_PROFILE = "profiles/"

        val uid get() = if (FirebaseAuth.getInstance().currentUser != null) FirebaseAuth.getInstance().currentUser!!.uid else ""
        val isLoggedIn get() = FirebaseAuth.getInstance().currentUser != null

        fun createUsernameWithEmail(email: String): String {
            var username = email
            if (email.contains("@")) {
                username = email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            }
            return username
        }

        fun updateUserData(uid: String, user: User) {
            mDatabase.child("users").child(uid).setValue(user)
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
            mDatabase.child("user-images").child(uid).setValue(upload)
        }
    }

    fun getUserProfile(onValueEventListener: OnValueEventListener) {
        userProfileEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                onValueEventListener.onDataChange(dataSnapshot)
            }
        }
        mDatabase.child("users").child(UserManager.uid).addValueEventListener(userProfileEventListener!!)
    }

    fun <T> getProfile(uid: String, onEventListener: OnEventListener, mClass: Class<T>) {
        val userProfileEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val model = dataSnapshot.getValue(mClass)
                onEventListener.onDataChange(model)
            }
        }
        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(userProfileEventListener)
    }

    fun getUserImage(onValueEventListener: OnValueEventListener) {
        userImageEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onValueEventListener.onDataChange(dataSnapshot)
//                val userImage = dataSnapshot.getValue(Upload::class.java)
//
//                if (userImage?.url != null) {
////                    view.bindUserImage(userImage)
//                }

            }
        }
        mDatabase.child("user-images").child(UserManager.uid).addValueEventListener(userImageEventListener)
    }

    fun <T> getImage(uid: String, onEventListener: OnEventListener, mClass: Class<T>) {
        val userImageEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val model = dataSnapshot.getValue(mClass)
                onEventListener.onDataChange(model)
            }
        }
        mDatabase.child("user-images").child(uid).addListenerForSingleValueEvent(userImageEventListener)
    }

    fun removeListener() {
        mDatabase.removeEventListener(userProfileEventListener!!)
        mDatabase.removeEventListener(userImageEventListener!!)
    }

    interface OnValueEventListener {
        fun onDataChange(dataSnapshot: DataSnapshot)
    }

    interface OnEventListener {
        fun <T> onDataChange(model: T)
    }
}