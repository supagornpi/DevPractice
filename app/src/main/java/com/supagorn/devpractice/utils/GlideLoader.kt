package com.supagorn.devpractice.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by apple on 2/18/2018 AD.
 */
 class GlideLoader {

    companion object {
        fun load(context: Context, url: String, imageView: ImageView) {
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions().fitCenter())
                    .into(imageView)
        }
    }
}