package com.supagorn.devpractice.ui.sidebar

import com.supagorn.devpractice.base.BasePresenter
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User

interface SidebarContract {
    interface Presenter : BasePresenter {
        fun fetchUserImage()
    }

    interface View {
        fun bindUserProfile(user: User)

        fun bindUserImage(upload: Upload?)

    }
}
