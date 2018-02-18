package com.supagorn.devpractice.ui.setting.adapter.factory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.supagorn.devpractice.R
import com.supagorn.devpractice.ui.setting.adapter.viewholder.ToggleViewHolder

/**
 * Created by apple on 2/12/2018 AD.
 */
class ToggleCreator : SettingViewHolderFactory.Creator {
    override fun create(parent: ViewGroup): RecyclerView.ViewHolder {
        return ToggleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_setting_toggle, parent, false))
    }
}