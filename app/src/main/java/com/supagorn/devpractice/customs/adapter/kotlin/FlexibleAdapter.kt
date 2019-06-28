package com.supagorn.devpractice.customs.adapter.kotlin

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FlexibleAdapter<T>(private var onBindViewListener: OnBindViewListener, onDiffCallback: OnDiffCallback? = null) :
        ListAdapter<T, RecyclerView.ViewHolder>(AsyncDifferConfig.Builder(PostDiffCallback<T>(onDiffCallback)).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = onBindViewListener.onCreateView(parent, viewType)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return onBindViewListener.getItemViewType(currentList[position], position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (currentList.isNullOrEmpty()) return
        onBindViewListener.onBindViewHolder(currentList[position], holder.itemView, holder.itemViewType, position)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class PostDiffCallback<T>(private val onDiffCallback: OnDiffCallback?) : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return onDiffCallback?.areItemsTheSame(oldItem, newItem) ?: false
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return onDiffCallback?.areContentsTheSame(oldItem, newItem) ?: false
        }
    }

    interface OnBindViewListener {
        fun <T> onBindViewHolder(item: T, itemView: View, viewType: Int, position: Int)

        fun onCreateView(parent: ViewGroup, viewType: Int): View

        fun <T> getItemViewType(item: T, position: Int): Int
    }

    interface OnDiffCallback {
        fun <T> areItemsTheSame(oldItem: T, newItem: T): Boolean

        fun <T> areContentsTheSame(oldItem: T, newItem: T): Boolean
    }
}


