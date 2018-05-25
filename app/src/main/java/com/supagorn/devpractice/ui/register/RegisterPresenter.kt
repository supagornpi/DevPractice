package com.supagorn.devpractice.ui.register

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity
import com.supagorn.devpractice.utils.ValidatorUtils

class RegisterPresenter constructor(private var view: RegisterContract.View) : RegisterContract.Presenter {

    private var isEditMode = false
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDatabase = FirebaseDatabase.getInstance().reference

    override fun register(entity: RegisterEntity) {
        isEditMode = false
        if (validate(entity)) {
            view.showProgressDialog()
            mAuth.createUserWithEmailAndPassword(entity.email, entity.password).addOnCompleteListener({ task ->
                view.hideProgressDialog()
                if (task.isSuccessful) {
                    val firebaseUser = task.result.user
                    UserManager.updateUserData(firebaseUser.uid, entity)
                    view.registerSuccess()
                } else {
                    view.registerFailed()
                }
            })
        }
    }

    override fun getProfile() {
        mDatabase.child("users").child(UserManager.getUid()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                view.bindUserProfile(user!!)
            }
        })
    }

    override fun editProfile(entity: RegisterEntity) {
        isEditMode = true
        if (validate(entity)) {
            UserManager.updateUserData(UserManager.getUid(), entity)
            view.updateProfileSuccess()
        }
    }

    private fun validate(entity: RegisterEntity): Boolean {
        var isValid = false
        if (entity.firstName.isNullOrEmpty()) {
            //require first name
            view.requireField(RequireField.FirstName)
        } else if (entity.lastName.isNullOrEmpty()) {
            //require last name
            view.requireField(RequireField.LastName)
        } else if (entity.email.isNullOrEmpty()) {
            //require email
            view.requireField(RequireField.Email)
        } else if (ValidatorUtils.isInValidEmail(entity.email)) {
            //email is invalid
            view.showEmailInvalid()
        } else if (!entity.mobile.isNullOrEmpty() && ValidatorUtils.isInValidMobile(entity.mobile)) {
            //mobile is invalid
            view.showMobileInvalid()
        } else if (!isEditMode) {
            if (entity.password.isNullOrEmpty()) {
                //require password
                view.requireField(RequireField.Password)
            } else if (ValidatorUtils.isInValidPassword(entity.password)) {
                //password is invalid
                view.showPasswordInvalid()
            } else if (entity.confirmPassword.isNullOrEmpty()) {
                //require confirm password
                view.requireField(RequireField.ConfirmPassword)
            } else if (entity.password != entity.confirmPassword) {
                //confirm password not match
                view.showConfirmPasswordNotMatch()
            } else {
                isValid = true
            }
        } else {
            isValid = true
        }
        return isValid
    }
}