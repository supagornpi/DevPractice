package com.supagorn.devpractice.customs.adapter.viewholder

import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.supagorn.devpractice.customs.adapter.BaseViewHolderFactory
import com.supagorn.devpractice.customs.view.*
import com.supagorn.devpractice.enums.FeedViewType
import com.supagorn.devpractice.enums.PostViewType
import com.supagorn.devpractice.firebase.PostManager
import com.supagorn.devpractice.model.home.Post

object FeedViewHolderFactory : BaseViewHolderFactory() {

    override fun createView(parent: ViewGroup, viewType: Int): View {
        return when (FeedViewType.parse(viewType)) {
            FeedViewType.ProfileHeader -> {
                FeedProfileView(parent.context)
            }
            FeedViewType.Image -> {
                FeedImageView(parent.context)
            }
            FeedViewType.Comment -> {
                FeedCommentView(parent.context)
            }
            else -> {
                EmptyView(parent.context)
            }
        }
    }

    override fun <T> bindView(item: T, itemView: View, viewType: Int, position: Int) {
        item as Post

        when (FeedViewType.parse(viewType)) {
            FeedViewType.ProfileHeader -> {
                ((itemView) as FeedProfileView).bind(item)
            }
            FeedViewType.Image -> {
                ((itemView) as FeedImageView).bind(item)
            }
            FeedViewType.Comment -> {
                ((itemView) as FeedCommentView).bind()
            }
            else -> {

            }
        }
    }

    override fun <T> areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is Post -> {
                (oldItem as Post).id == (newItem as Post).id
            }
            else -> false
        }
    }

    override fun <T> areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is Post -> {
                ((oldItem as Post).body == (newItem as Post).body) && (oldItem.timestamp == newItem.timestamp)
            }
            else -> false
        }
    }
}