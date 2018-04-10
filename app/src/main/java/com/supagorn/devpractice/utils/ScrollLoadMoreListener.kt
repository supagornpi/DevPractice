package com.supagorn.devpractice.utils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


class ScrollLoadMoreListener(private val listener: LoadMoreListener) : RecyclerView.OnScrollListener() {

    var isLoadingMore = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        val visibleItemCount = 2
        val totalItemCount = layoutManager.itemCount;
        val firstVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
//        val firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (!isLoadingMore && firstVisibleItem + visibleItemCount >= totalItemCount) {
            isLoadingMore = true
            listener.onLoadMore()

        }
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }
}