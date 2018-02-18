package com.supagorn.devpractice.ui.setting

import com.supagorn.devpractice.R
import java.util.*

enum class Settings constructor(val id: Int, val nameId: Int, val type: SettingType) {

    Notification(0, R.string.setting_notification, SettingType.Toggle),
    Version(1, R.string.setting_version, SettingType.Text),
    About(2, R.string.setting_about, SettingType.Normal),
    PrivacyPolicy(3, R.string.setting_privacy_policy, SettingType.Normal);

    companion object {
        fun parse(typeId: Int): Settings {
            val creatorMap = HashMap<Int, Settings>()
            creatorMap.put(Settings.Notification.id, Settings.Notification)
            creatorMap.put(Settings.Version.id, Settings.Version)
            creatorMap.put(Settings.About.id, Settings.About)
            creatorMap.put(Settings.PrivacyPolicy.id, Settings.PrivacyPolicy)
            return creatorMap[typeId]!!
        }
    }
}
