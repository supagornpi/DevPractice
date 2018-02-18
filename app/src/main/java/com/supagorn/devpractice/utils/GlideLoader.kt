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
            load(context, url, RequestOptions().fitCenter(), imageView)
        }

        fun load(context: Context, url: String, requestOptions: RequestOptions, imageView: ImageView) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView)
        }
    }
}