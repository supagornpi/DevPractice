package com.supagorn.devpractice.customs.adapter

import android.view.View
import android.view.ViewGroup

abstract class BaseViewHolderFactory {

    /**
     * Create view holder
     * @return LayoutInflater.from(parent.context).inflate(R.layout.your_layout, parent, false)
     * or
     * @return YourCustomView(context: Context)
     */
    abstract fun createView(parent: ViewGroup, viewType: Int): View

    abstract fun <T> bindView(item: T, itemView: View, viewType: Int, position: Int)

    abstract fun <T> areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun <T> areContentsTheSame(oldItem: T, newItem: T): Boolean


}