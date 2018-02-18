package com.supagorn.devpractice.ui.setting.adapter.binder

import android.support.v7.widget.RecyclerView
import com.supagorn.devpractice.ui.setting.Settings
import com.supagorn.devpractice.ui.setting.adapter.viewholder.NormalViewHolder
import kotlinx.android.synthetic.main.layout_setting_normal.view.*

class NormalBinder: SettingViewBinder.Binder {
    override fun bind(holder: RecyclerView.ViewHolder, settings:Settings) {
        val viewHolder = holder as NormalViewHolder
        viewHolder.itemView.tvTitle.text = holder.itemView.context.resources.getString(settings.nameId)
    }
}