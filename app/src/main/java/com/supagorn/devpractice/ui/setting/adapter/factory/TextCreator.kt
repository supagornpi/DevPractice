package com.supagorn.devpractice.ui.setting.adapter.factory

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.supagorn.devpractice.R
import com.supagorn.devpractice.ui.setting.adapter.viewholder.TextViewHolder

class TextCreator : SettingViewHolderFactory.Creator {
    override fun create(parent: ViewGroup): RecyclerView.ViewHolder {
        return TextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_setting_text, parent, false))
    }
}