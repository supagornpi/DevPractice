package com.supagorn.devpractice.ui.setting.adapter.binder

import android.support.v7.widget.RecyclerView
import com.supagorn.devpractice.ui.setting.Settings
import com.supagorn.devpractice.ui.setting.adapter.viewholder.ToggleViewHolder
import com.supagorn.devpractice.utils.LanguageHelper
import kotlinx.android.synthetic.main.layout_setting_custom_icon.view.*

class CustomIconBinder : SettingViewBinder.Binder {
    override fun bind(holder: RecyclerView.ViewHolder, settings: Settings) {
        val viewHolder = holder as ToggleViewHolder
        viewHolder.itemView.tvTitle.text = holder.itemView.context.resources.getString(settings.nameId)

        when (settings) {
            Settings.ChangeLanguage -> {
                viewHolder.itemView.ivIcon.setImageResource(LanguageHelper.getLanguageIcon())
            }
            else -> {

            }
        }
    }
}