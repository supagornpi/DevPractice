package com.supagorn.devpractice.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.supagorn.devpractice.utils.ValidatorUtils

class LoginPresenter constructor(private var view: LoginContract.View) : LoginContract.Presenter {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun validate(email: String, password: String): Boolean {
        var isValid = false
        if (ValidatorUtils.isInValidEmail(email)) {
            view.showEmailInvalid()
        } else if (ValidatorUtils.isInValidPassword(password)) {
            view.showPasswordInvalid()
        } else {
            isValid = true
        }
        return isValid
    }

    override fun login(email: String, password: String) {
        if (validate(email, password)) {
            view.showProgressDialog()
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                view.hideProgressDialog()
                if (task.isSuccessful) {
                    view.loginSuccess()
                } else {
                    view.loginFailed()
                }
            }
        }
    }
}