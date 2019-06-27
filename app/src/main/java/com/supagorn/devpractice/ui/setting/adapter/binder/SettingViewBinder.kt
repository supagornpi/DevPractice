package com.supagorn.devpractice.ui.setting.adapter.binder

import androidx.recyclerview.widget.RecyclerView
import com.supagorn.devpractice.ui.setting.SettingType
import com.supagorn.devpractice.ui.setting.Settings

class SettingViewBinder {
    companion object {
        fun bind(holder: RecyclerView.ViewHolder, viewType: Int, settings: Settings) {
            var binder: Binder? = null
            when (SettingType.parse(viewType)) {
                SettingType.Normal -> {binder = NormalBinder()}
                SettingType.Toggle -> {binder = ToggleBinder()}
                SettingType.Text -> {binder = TextBinder()}
                SettingType.Custom -> {binder = CustomIconBinder()}
                else -> {
                }
            }
            binder!!.bind(holder, settings)
        }
    }

    internal interface Binder {
        fun bind(holder: RecyclerView.ViewHolder, settings: Settings)
    }
}