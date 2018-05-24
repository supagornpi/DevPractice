package com.supagorn.devpractice.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.supagorn.devpractice.utils.ValidatorUtils

class LoginPresenter constructor(private var view: LoginContract.View) : LoginContract.Presenter {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun validate(email: String, password: String) {
        if (ValidatorUtils.isInValidEmail(email)) {
            view.showEmailInvalid()
        } else if (ValidatorUtils.isInValidPassword(password)) {
            view.showPasswordInvalid()
        } else {
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
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