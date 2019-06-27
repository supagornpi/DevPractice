package com.supagorn.devpractice.customs.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ScrollLoadMoreListener(private val listener: OnLoadMoreListener) : RecyclerView.OnScrollListener() {

    var hasLoadingMore = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        val visibleItemCount = 2
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
        if (hasLoadingMore && firstVisibleItem + visibleItemCount >= totalItemCount) {
            hasLoadingMore = false
            listener.onLoadMore()

        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}