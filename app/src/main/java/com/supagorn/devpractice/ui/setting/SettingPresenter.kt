package com.supagorn.devpractice.ui.setting

class SettingPresenter constructor(val view: SettingContract.View) : SettingContract.Presenter {

    override fun start() {
        val settings: MutableList<Settings> = mutableListOf()
        settings.add(Settings.Notification)
        settings.add(Settings.Version)
        settings.add(Settings.About)
        settings.add(Settings.PrivacyPolicy)
        view.showSetting(settings)
    }

    override fun stop() {

    }
}