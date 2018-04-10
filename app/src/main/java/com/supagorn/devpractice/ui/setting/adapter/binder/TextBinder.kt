package com.supagorn.devpractice.ui.setting.adapter.binder

import android.support.v7.widget.RecyclerView
import com.supagorn.devpractice.BuildConfig
import com.supagorn.devpractice.ui.setting.Settings
import com.supagorn.devpractice.ui.setting.adapter.viewholder.TextViewHolder
import kotlinx.android.synthetic.main.layout_setting_text.view.*

class TextBinder : SettingViewBinder.Binder {
    override fun bind(holder: RecyclerView.ViewHolder, settings: Settings) {
        val viewHolder = holder as TextViewHolder
        viewHolder.itemView.tvTitle.text = holder.itemView.context.resources.getString(settings.nameId)

        when (settings) {
            Settings.Version -> {
                viewHolder.itemView.tvText.text = BuildConfig.VERSION_NAME
            }
            else -> {
            }
        }
    }
}