package com.supagorn.devpractice.ui.sidebar

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User

class SidebarPresenter constructor(private var view: SidebarContract.View) : SidebarContract.Presenter {

    private val mDatabase = FirebaseDatabase.getInstance().reference
    private var userProfileEventListener: ValueEventListener? = null
    private var userImageEventListener: ValueEventListener? = null

    override fun start() {
        fetchUserProfile()
        fetchUserImage()
    }

    override fun stop() {
        mDatabase.removeEventListener(userProfileEventListener!!)
        mDatabase.removeEventListener(userImageEventListener!!)
    }

    private fun fetchUserProfile() {

        userProfileEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                view.bindUserProfile(user!!)
            }
        }
        mDatabase.child("users").child(UserManager.uid).addValueEventListener(userProfileEventListener!!)

    }

    override fun fetchUserImage() {
        userImageEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userImage = dataSnapshot.getValue(Upload::class.java)
                view.bindUserImage(userImage)

            }
        }
        mDatabase.child("user-images").child(UserManager.uid).addValueEventListener(userImageEventListener as ValueEventListener)
    }
}