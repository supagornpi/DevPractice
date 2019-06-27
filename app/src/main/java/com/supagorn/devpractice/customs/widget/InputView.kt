package com.supagorn.devpractice.customs.widget

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.utils.SpinnerUtils
import kotlinx.android.synthetic.main.view_input_field.view.*


class InputView : LinearLayout {

    var input = ""
    lateinit var editText: EditText

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
        View.inflate(context, R.layout.view_input_field, this)
        editText = edtInput
        edtInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                input = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    fun setInputName(title: String, hint: String, inputType: Int) {
        tvTitle.text = title
        edtInput.hint = hint
        edtInput.inputType = inputType
    }

    fun setInputName(title: Int, hint: Int, inputType: Int) {
        setInputName(context.resources.getString(title), context.resources.getString(hint), inputType)
    }

    fun setMaxLength(maxLength: Int) {
        edtInput.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    fun setTitleColor(titleColor: Int) {
        if (titleColor != 0) {
            tvTitle.setTextColor(context.resources.getColor(titleColor))
        }
    }

    fun setSpinner(list: Int, hasHint: Boolean) {
        edtInput.visibility = View.INVISIBLE
        layoutSpinner.visibility = View.VISIBLE
        SpinnerUtils.setSpinner(context, spinner, list, hasHint)
    }

    fun setSelectedPosition(position: Int) {
        spinner.setSelection(position)
    }

    fun getSelectedPosition(): Int {
        return spinner.selectedItemPosition
    }
}
