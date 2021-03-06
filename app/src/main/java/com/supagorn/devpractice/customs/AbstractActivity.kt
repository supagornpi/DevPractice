package com.supagorn.devpractice.customs

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.supagorn.devpractice.R
import com.supagorn.devpractice.utils.LanguageHelper
import kotlinx.android.synthetic.main.layout_action_bar.*


abstract class AbstractActivity : AppCompatActivity() {

    protected lateinit var progressDialog: ProgressDialog

    protected abstract fun setLayoutView(): Int
    protected abstract fun setupView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutView())
        setProgressDialog()
        setupView()
    }

    // override the base context of application to update default locale for this activity
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(base))
    }

    override fun setTitle(stringId: Int) {
        setTitle(resources.getString(stringId))
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun showBackButton() {
        if (btnIconLeft != null) {
            btnIconLeft.setImageResource(R.drawable.ic_previous)
            btnIconLeft.visibility = View.VISIBLE
            btnIconLeft.setOnClickListener({
                onBackPressed()
            })
        }
    }

    fun setMenuRightText(textId: Int) {
        tvRightMenu.text = applicationContext.getString(textId)
    }

    fun setOnclickMenuRight(onClickListener: View.OnClickListener) {
        tvRightMenu.setOnClickListener(onClickListener)
    }

    fun setButtonLeft(icon: Int, onClickListener: View.OnClickListener) {
        if (btnIconLeft != null) {
            btnIconLeft.setImageResource(icon)
            btnIconLeft.visibility = View.VISIBLE
            btnIconLeft.setOnClickListener(onClickListener)
        }
    }

    fun setButtonRight(icon: Int, onClickListener: View.OnClickListener) {
        if (btnRight != null) {
            btnRight.setImageResource(icon)
            btnRight.visibility = View.VISIBLE
            btnRight.setOnClickListener(onClickListener)
        }
    }

    private fun setProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Now Loading...")
        progressDialog.setCancelable(false)
    }

    fun updateProgress(message: String) {
        if (progressDialog.isShowing) {
            progressDialog.setMessage(message)
        }
    }

    fun showProgressDialog() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}