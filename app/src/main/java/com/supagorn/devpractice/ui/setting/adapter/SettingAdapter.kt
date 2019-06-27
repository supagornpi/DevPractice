package com.supagorn.devpractice.ui.setting.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.supagorn.devpractice.ui.setting.Settings
import com.supagorn.devpractice.ui.setting.adapter.binder.SettingViewBinder
import com.supagorn.devpractice.ui.setting.adapter.factory.SettingViewHolderFactory

class SettingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var settings: MutableList<Settings> = mutableListOf()
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SettingViewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        SettingViewBinder.bind(holder, holder.itemViewType, settings[position])

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClicked(settings[position])
        }
    }

    override fun getItemCount(): Int {
        return this.settings.size
    }

    override fun getItemViewType(position: Int): Int {
        return this.settings[position].type.id
    }

    fun setSettings(settings: MutableList<Settings>) {
        this.settings = settings
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(setting: Settings)
    }
}