package com.supagorn.devpractice.customs.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.model.home.Post
import kotlinx.android.synthetic.main.view_post_normal.view.*

class PostView : LinearLayout {

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
        View.inflate(context, R.layout.view_post_normal, this)
    }

    fun bind(model: Post, starClickListener: View.OnClickListener) {
        tvName.text = model.author
        tvPostContent.text = model.body
        tvLikeCount.text = context.getString(R.string.post_like, model.likeCount)
//        tvCommentCount.text = context.getString(R.string.post_comment, post.commentCount)

        // Determine if the current user has liked this post and set UI accordingly
        if (model.likes != null) {
            btnLike.setImageResource(if (model.likes.containsKey(UserManager.uid))
                R.drawable.ic_like_active else
                R.drawable.ic_like_unactive)
        }

        btnLike.setOnClickListener(starClickListener)

    }
}
