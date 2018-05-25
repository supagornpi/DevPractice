package com.supagorn.devpractice.ui.register

import com.supagorn.devpractice.base.BaseView
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity

interface RegisterContract {
    interface Presenter {
        fun register(entity: RegisterEntity)

        fun editProfile(entity: RegisterEntity)

        fun getProfile()
    }

    interface View : BaseView.Progress {
        fun bindUserProfile(user: User)

        fun requireField(requireField: RequireField)

        fun showMobileInvalid()

        fun showEmailInvalid()

        fun showPasswordInvalid()

        fun showConfirmPasswordNotMatch()

        fun registerSuccess()

        fun registerFailed()

        fun updateProfileSuccess()
    }
}
