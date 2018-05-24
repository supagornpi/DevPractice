package com.supagorn.devpractice.ui.register

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity
import com.supagorn.devpractice.utils.ValidatorUtils

class RegisterPresenter constructor(private var view: RegisterContract.View) : RegisterContract.Presenter {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun register(entity: RegisterEntity) {
        if (validate(entity)) {
            view.showProgressDialog()
            mAuth.createUserWithEmailAndPassword(entity.email, entity.password).addOnCompleteListener({ task ->
                view.hideProgressDialog()
                if (task.isSuccessful) {
                    onAuthSuccess(task.result.user, entity)
                    view.registerSuccess()
                } else {
                    view.registerFailed()
                }
            })
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
        } else if (entity.password.isNullOrEmpty()) {
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
        return isValid
    }

    private fun onAuthSuccess(firebaseUser: FirebaseUser, entity: RegisterEntity) {
        val email = firebaseUser.email
        var username = email
        if (email != null && email.contains("@")) {
            username = email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }

        val user = User(username, email)
                .setFirstName(entity.firstName)
                .setLastName(entity.lastName)
                .setMobile(entity.mobile)
                .setGender(entity.gender)

        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("users").child(firebaseUser.uid).setValue(user)
    }


}