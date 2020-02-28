package com.supagorn.devpractice.customs.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.supagorn.devpractice.R.layout
import com.supagorn.devpractice.model.home.Post
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.view_feed_profile_header.view.*
import java.util.concurrent.TimeUnit


class FeedProfileView : LinearLayout {

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
        val layoutInflater = LayoutInflater.from(context)
        View.inflate(context, layout.view_feed_profile_header, this)

    }

    fun bind(post: Post) {
        tv_post_at.text = millisecondsToString(1567161507129)
        tv_display_name.text = post.fullName
        GlideLoader.loadImageCircle(context, "https://f.ptcdn.info/428/029/000/1426577791-1102299266-o.jpg", iv_profile)
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
    }
}
