package com.supagorn.devpractice.customs.adapter.kotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.listener.ScrollLoadMoreListener


class CustomAdapter<T>(private var onBindViewListener: OnBindViewListener) :
        RecyclerView.Adapter<CustomAdapter<T>.ViewHolder>() {

    companion object {
        const val TYPE_LOAD_MORE = 0
        const val TYPE_ITEM = 1
    }

    private var list = mutableListOf<T>()
    private var isLoadMore = false
    private var pageSize = 10 //default is 10
    private var onScrollLoadMoreListener: ScrollLoadMoreListener? = null

    fun setPageSize(pageSize: Int): CustomAdapter<T> {
        this.pageSize = pageSize
        return this
    }

    /**
     * To enable load more listener
     *
     * @param recyclerView
     * @return
     */
    fun setOnLoadMoreListener(recyclerView: RecyclerView, emptyItem: T,
                              onLoadMoreListener: ScrollLoadMoreListener.OnLoadMoreListener): CustomAdapter<T> {
        //Enable load more
        val scrollLoadMoreListener = ScrollLoadMoreListener(object : ScrollLoadMoreListener.OnLoadMoreListener {
            override fun onLoadMore() {
                showLoadMore(emptyItem)
                onLoadMoreListener.onLoadMore()
            }
        })
        this.onScrollLoadMoreListener = scrollLoadMoreListener
        recyclerView.addOnScrollListener(scrollLoadMoreListener)
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_LOAD_MORE -> {
                val viewLoadMore = LayoutInflater.from(parent.context).inflate(R.layout.view_load_more,
                        parent, false)
                ViewHolder(viewLoadMore)
            }
            TYPE_ITEM -> {
                val view = onBindViewListener.onCreateView()
                view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                ViewHolder(view)
            }
            else -> {
                ViewHolder(LinearLayout(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        if (holder.itemViewType == TYPE_ITEM) {
            onBindViewListener.onBindViewHolder(item, holder.itemView, holder.itemViewType, position)
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == this.list.size - 1 && this.isLoadMore) TYPE_LOAD_MORE else TYPE_ITEM
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnBindViewListener {
        fun <T> onBindViewHolder(item: T, itemView: View, viewType: Int, position: Int)

        fun onCreateView(): View
    }

    internal fun setListItem(list: MutableList<T>) {
        this.onScrollLoadMoreListener?.hasLoadingMore = list.size == this.pageSize
        this.list = list
        notifyDataSetChanged()
    }

    internal fun addListItem(list: List<T>) {
        this.onScrollLoadMoreListener?.hasLoadingMore = list.size == this.pageSize
        if (this.isLoadMore) {
            hideLoadMore()
        }
        val notifyStart = this.list.size
        this.list.addAll(list)
        notifyItemRangeChanged(notifyStart, notifyStart + list.size)
    }

    /**
     * hide automatically when add item list if isLoadMore is true
     */
    fun hideLoadMore() {
        //remove last item
        this.isLoadMore = false
        this.list.removeAt(this.list.size - 1)
        notifyItemRemoved(this.list.size - 1)
    }

    /**
     *  show automatically when add item list if isLoadMore is false
     * @param emptyObject
     */
    private fun showLoadMore(emptyObject: T) {
        //show load more item
        this.isLoadMore = true
        this.list.add(emptyObject)
        notifyItemInserted(this.list.size - 1)
    }
}
