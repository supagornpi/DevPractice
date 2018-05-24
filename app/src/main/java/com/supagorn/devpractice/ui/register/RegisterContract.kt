package com.supagorn.devpractice.ui.register

import com.supagorn.devpractice.base.BaseView
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.model.register.RegisterEntity

interface RegisterContract {
    interface Presenter {
        fun register(entity: RegisterEntity)
    }

    interface View : BaseView.Progress {
        fun requireField(requireField: RequireField)

        fun showMobileInvalid()

        fun showEmailInvalid()

        fun showPasswordInvalid()

        fun showConfirmPasswordNotMatch()

        fun registerSuccess()

        fun registerFailed()
    }
}
