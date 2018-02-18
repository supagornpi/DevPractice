package com.supagorn.devpractice.ui.setting

import com.supagorn.devpractice.BasePresenter

interface SettingContract {
    interface Presenter : BasePresenter {

    }

    interface View {
        fun showSetting(settings: MutableList<Settings>)
    }
}