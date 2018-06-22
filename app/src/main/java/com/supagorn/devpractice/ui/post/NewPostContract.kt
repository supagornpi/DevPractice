package com.supagorn.devpractice.ui.post

import com.supagorn.devpractice.base.BaseView
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity

interface NewPostContract {
    interface Presenter {
        fun submitPost(body: String)

    }

    interface View : BaseView.Progress {
        fun requireField()

        fun postSuccess()

        fun postFailed()

    }
}
