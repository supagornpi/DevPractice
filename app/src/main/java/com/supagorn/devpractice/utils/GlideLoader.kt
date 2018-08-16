package com.supagorn.devpractice.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R

/**
 * Created by apple on 2/18/2018 AD.
 */
class GlideLoader {

    companion object {
        fun loadDefaultImage(imageView: ImageView) {
            imageView.setImageResource(R.drawable.img_moon)
        }

        fun load(context: Context, url: String, imageView: ImageView) {
            load(context, url, RequestOptions().fitCenter(), imageView)
        }

        fun load(context: Context, url: String?, requestOptions: RequestOptions, imageView: ImageView) {
            if (url.isNullOrEmpty()) {
                loadDefaultImage(imageView)
            } else {
                Glide.with(context)
                        .load(url)
                        .apply(requestOptions)
                        .into(imageView)
            }
        }

        fun load(context: Context, image: Int, imageView: ImageView) {
            Glide.with(context)
                    .load(image)
                    .into(imageView)
        }

        fun load(uri: Uri, imageView: ImageView) {
            Glide.with(MyApplication.instance)
                    .load(uri)
                    .into(imageView)
        }

        fun loadImageCircle(context: Context, imageId: Int, imageView: ImageView) {
            Glide.with(context)
                    .load(imageId)
                    .apply(RequestOptions().fitCenter().circleCrop())
                    .into(imageView)
        }

        fun loadImageCircle(context: Context, url: String?, imageView: ImageView) {
            if (url.isNullOrEmpty()) {
                loadDefaultImage(imageView)
            } else {
                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions().fitCenter().circleCrop())
                        .into(imageView)
            }
        }
    }
}