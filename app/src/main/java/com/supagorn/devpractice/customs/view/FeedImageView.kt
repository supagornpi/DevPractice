package com.supagorn.devpractice.customs.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.supagorn.devpractice.R
import com.supagorn.devpractice.firebase.PostManager
import com.supagorn.devpractice.model.home.Post
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.view_feed_image.view.*

class FeedImageView : LinearLayout {

    private val postManager = PostManager.instance

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_feed_image, this)
    }

    fun bind(post: Post) {
        GlideLoader.load(context, post.imageUrl, iv_post_image)
    }
}
