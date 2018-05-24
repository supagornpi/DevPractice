package com.supagorn.devpractice.ui.login

import com.supagorn.devpractice.base.BaseView

interface LoginContract {
    interface Presenter {
        fun validate(email: String, password: String)
    }

    interface View : BaseView.Progress {
        fun showEmailInvalid()

        fun showPasswordInvalid()

        fun loginSuccess()

        fun loginFailed()

    }
}
