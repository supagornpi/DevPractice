package com.supagorn.devpractice.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.utils.LanguageHelper

class DialogChangeLanguage(activity: Activity) : AlertDialog(activity), View.OnClickListener {

    var onSelectedListener: OnSelectedListener? = null

    companion object {
        fun alert(activity: Activity, onSelectedListener: OnSelectedListener) {
            val dialogChangeLanguage = DialogChangeLanguage(activity)
            dialogChangeLanguage.onSelectedListener = onSelectedListener
            dialogChangeLanguage.alert()
        }
    }

    private fun alert() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_change_language, null)
        setView(view)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val lloThai = view.findViewById<View>(R.id.linearlayoutThai) as LinearLayout
        val lloEnglish = view.findViewById<View>(R.id.linearlayoutEnglish) as LinearLayout

        lloThai.setOnClickListener(this)
        lloEnglish.setOnClickListener(this)

        show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.linearlayoutThai -> {
                LanguageHelper.setLanguage(context, LanguageHelper.TH)
                dismiss()
                if (onSelectedListener != null) {
                    onSelectedListener?.onSelected()
                }
            }
            R.id.linearlayoutEnglish -> {
                LanguageHelper.setLanguage(context, LanguageHelper.EN)
                dismiss()
                if (onSelectedListener != null) {
                    onSelectedListener?.onSelected()
                }
            }
            else -> {
            }
        }
    }

    interface OnSelectedListener {
        fun onSelected()
    }
}
