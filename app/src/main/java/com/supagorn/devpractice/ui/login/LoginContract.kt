package com.supagorn.devpractice.ui.login

import com.supagorn.devpractice.base.BaseView

interface LoginContract {
    interface Presenter {
        fun login(email: String, password: String)
    }

    interface View : BaseView.Progress {
        fun showEmailInvalid()

        fun showPasswordInvalid()

        fun loginSuccess()

        fun loginFailed()

    }
}
