package com.supagorn.devpractice.customs.view

import android.animation.AnimatorInflater
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.firebase.database.DatabaseReference
import com.supagorn.devpractice.R
import com.supagorn.devpractice.dialog.DialogLists
import com.supagorn.devpractice.firebase.PostManager
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.home.Post
import com.supagorn.devpractice.ui.post.NewPostActivity
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.view_post_normal.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class PostView : LinearLayout {

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
        View.inflate(context, R.layout.view_post_normal, this)
    }

    fun bind(model: Post, postRef: DatabaseReference?) {
        tvName.text = if (model.fullName.isNullOrEmpty()) model.author else model.fullName
        tvPostContent.text = model.body
        tvLikeCount.text = context.getString(R.string.post_like, model.likeCount)

        val timeAgo = getTimeAgo(model.timestamp)
        tvTime.text = millisecondsToString(timeAgo)

//        tvCommentCount.text = context.getString(R.string.post_comment, submitPost.commentCount)

        // Determine if the current user has liked this submitPost and set UI accordingly
        if (model.likes != null) {
            btnLike.setImageResource(if (model.likes.containsKey(UserManager.uid))
                R.drawable.ic_like_active else
                R.drawable.ic_like_unactive)

            //set animator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val anim = AnimatorInflater.loadStateListAnimator(context, R.animator.scale)
                btnLike.stateListAnimator = anim
            }
        }

        fetchUserImage(model.uid, model)

        postRef?.let {
            btnLike.setOnClickListener {
                // Run two transactions
                // Need to write to both places the post is stored
                postManager.onStarClicked(postManager.getGlobalPostRef(postRef.key!!))
                postManager.onStarClicked(postManager.getUserPostRef(model.uid, postRef.key!!))
            }

            btnMore.setOnClickListener {
                showDialogMore(model.uid, postRef.key!!)
            }

            tvPostContent.setOnClickListener {
                gotoEditPost(model.uid, postRef.key!!)
            }
        }
    }

    fun bindWithImage(imageUrl: String) {
        layoutImage.visibility = View.VISIBLE
        GlideLoader.load(context, imageUrl, imgPostImage)
    }

    private fun fetchUserImage(uid: String, post: Post) {
        //update profile
        if (post.imageProfileUrl.isNullOrEmpty()) {
            //update user image
            UserManager.instance.getImage(uid, object : UserManager.OnEventListener {
                override fun <T> onDataChange(model: T) {
                    //null check
                    if (model == null) {
                        GlideLoader.loadDefaultImage(ivProfile)
                        return
                    }
                    //casting
                    model as Upload
                    post.imageProfileUrl = model.url
                    GlideLoader.loadImageCircle(context, post.imageProfileUrl, ivProfile)
                }
            }, Upload::class.java)
        } else {
            GlideLoader.loadImageCircle(context, post.imageProfileUrl, ivProfile)
        }
    }

    private fun showDialogMore(uid: String, key: String) {
        val items: MutableList<String>
        if (UserManager.uid == uid) {
            //owner
            items = context.resources.getStringArray(R.array.dialog_list_more_owner).toMutableList()
        } else {
            //user
            items = context.resources.getStringArray(R.array.dialog_list_more).toMutableList()
        }

        DialogLists.build<String>(context, items)
                .onCreateItemView(object : DialogLists.OnCreateItemView {
                    override fun <T> onBindItemView(item: T, textView: TextView, position: Int) {

                    }

                    override fun <T> onItemClicked(item: T, position: Int) {
                        when (position) {
                            0 -> {
                                //remove post
                                postManager.removePost(uid, key)
                            }
                            1 -> {
                                gotoEditPost(uid, key)
                            }
                        }
                    }
                }).show()
    }

    private fun gotoEditPost(uid: String, key: String) {
        if (UserManager.uid == uid) {
            NewPostActivity.startEditMode(key)
        }
    }

    private fun getTimeAgo(time: Long): Long {
        return Date().time - time
    }

    fun millisecondsToString(duration: Long): String {
        var duration = duration
        val scale = TimeUnit.MILLISECONDS

        val builder = StringBuilder()
        val days = scale.toDays(duration)
        duration -= TimeUnit.DAYS.toMillis(days)
        val hours = scale.toHours(duration)
        duration -= TimeUnit.HOURS.toMillis(hours)
        val minutes = scale.toMinutes(duration)

        if (days > 0) {
            builder.append(days)
            builder.append(" วัน")
        } else if (hours > 0) {
            builder.append(String.format("%d ชั่วโมงที่แล้ว", hours))
        } else if (minutes > 0) {
            builder.append(String.format("%d นาทีที่แล้ว", minutes))
        } else {
            builder.append("ไม่กี่วินาทีที่แล้ว")
        }
        return builder.toString()

        return builder.toString()
    }
}
