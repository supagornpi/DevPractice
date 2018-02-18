package com.supagorn.devpractice.customs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import com.supagorn.devpractice.utils.ResolutionUtils
import kotlinx.android.synthetic.main.layout_action_bar.*

/**
 * Created by supagorn on 17/2/2018 AD.
 *
 */
abstract class AbstractFragment : Fragment() {

    protected abstract fun setLayoutView(): Int
    protected abstract fun setupView()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setLayoutView(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    fun setTitle(stringId: Int) {
        setTitle(context.resources.getString(stringId))
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setIconLeft(iconId: Int) {
        btnIconLeft.setImageResource(iconId)
    }

    fun setIconLeftBorderless(iconId: Int) {
        btnIconLeft.setImageResource(iconId)
        val dp15 = ResolutionUtils.dip2px(context, 15.0f).toInt()
        btnIconLeft.setPadding(dp15, 0, dp15, 0)
        btnIconLeft.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
    }

    fun setToolbarColor(colorId: Int) {
        rootView.setBackgroundColor(context.resources.getColor(colorId))
    }

    fun setKeyboardVisibility(show: Boolean) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } else {
            imm.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
        }
    }
}