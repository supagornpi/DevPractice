package com.supagorn.devpractice.customs.adapter.kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supagorn.devpractice.R

class FlexibleAdapter<T>(private var onBindViewListener: OnBindViewListener, onDiffCallback: OnDiffCallback) :

        ListAdapter<T, RecyclerView.ViewHolder>(AsyncDifferConfig.Builder(PostDiffCallback<T>(onDiffCallback)).build()) {

    companion object {
        const val TYPE_LOAD_MORE = 0
        const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_LOAD_MORE -> {
                val viewLoadMore = LayoutInflater.from(parent.context).inflate(R.layout.view_load_more,
                        parent, false)
                ViewHolder(viewLoadMore)
            }
            TYPE_ITEM -> {
                val view = onBindViewListener.onCreateView(parent, viewType)
                view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                ViewHolder(view)
            }
            else -> {
                ViewHolder(LinearLayout(parent.context))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM;
//        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    class PostDiffCallback<T>(private val onDiffCallback: OnDiffCallback) : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            //return oldItem.id == newItem.id

            return onDiffCallback.areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            //if (oldItem is PhotoPost && newItem is PhotoPost) {
            //return oldItem.url == newItem.url && oldItem.text == newItem.text && oldItem.timestamp == newItem.timestamp
            //}
            return onDiffCallback.areContentsTheSame(oldItem, newItem)

        }
    }

    interface OnBindViewListener {
        fun <T> onBindViewHolder(item: T, itemView: View, viewType: Int, position: Int)

        fun onCreateView(parent: ViewGroup, viewType: Int): View
    }

    interface OnDiffCallback {
        fun <T> areItemsTheSame(oldItem: T, newItem: T): Boolean

        fun <T> areContentsTheSame(oldItem: T, newItem: T): Boolean
    }
}


