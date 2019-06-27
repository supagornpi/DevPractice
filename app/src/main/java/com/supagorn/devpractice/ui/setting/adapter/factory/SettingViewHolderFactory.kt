package com.supagorn.devpractice.ui.setting.adapter.factory

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.supagorn.devpractice.ui.setting.SettingType

class SettingViewHolderFactory {
    companion object {
        fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var creator: Creator? = null
            when (SettingType.parse(viewType)) {
                SettingType.Normal -> {creator = NormalCreator()
                }
                SettingType.Toggle -> {creator = ToggleCreator()
                }
                SettingType.Text -> {creator = TextCreator()
                }
                SettingType.Custom -> {creator = CustomIconCreator()}
                else -> {
                }
            }
            return creator!!.create(parent)
        }
    }

    interface Creator {
        fun create(parent: ViewGroup): RecyclerView.ViewHolder
    }
}
