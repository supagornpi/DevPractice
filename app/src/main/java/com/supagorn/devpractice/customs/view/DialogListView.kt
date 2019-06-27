package com.supagorn.devpractice.customs.view

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.supagorn.devpractice.R
import kotlinx.android.synthetic.main.view_item_dialog_list.view.*

class DialogListView : LinearLayout {


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
        View.inflate(context, R.layout.view_item_dialog_list, this)
    }

    fun bind(item: String) {
        tvItemName.text = item
    }

    fun getTextView(): TextView {
        return tvItemName
    }
}