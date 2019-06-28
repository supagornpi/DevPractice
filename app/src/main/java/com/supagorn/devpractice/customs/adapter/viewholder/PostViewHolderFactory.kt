package com.supagorn.devpractice.customs.adapter.viewholder

import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.supagorn.devpractice.customs.adapter.BaseViewHolderFactory
import com.supagorn.devpractice.customs.view.EmptyView
import com.supagorn.devpractice.customs.view.PostView
import com.supagorn.devpractice.enums.PostViewType
import com.supagorn.devpractice.firebase.PostManager
import com.supagorn.devpractice.model.home.Post

object PostViewHolderFactory : BaseViewHolderFactory() {

    override fun createView(parent: ViewGroup, viewType: Int): View {
        return when (PostViewType.parse(viewType)) {
            PostViewType.Caption -> {
                PostView(parent.context)
            }
            PostViewType.Empty -> {
                EmptyView(parent.context)
            }
            else -> {
                EmptyView(parent.context)
            }
        }
    }

    override fun <T> bindView(item: T, itemView: View, viewType: Int, position: Int) {
        item as Post
        var postRef: DatabaseReference? = null
        item.key?.let {
            postRef = PostManager.instance.getPost(item.key)
        }

        when (PostViewType.parse(viewType)) {
            PostViewType.Caption -> {
                (itemView as PostView).bind(item, postRef)
            }
            PostViewType.Image -> {
                (itemView as PostView).bind(item, postRef)

                item.imageUrl?.let {
                    itemView.bindWithImage(item.imageUrl)
                }
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