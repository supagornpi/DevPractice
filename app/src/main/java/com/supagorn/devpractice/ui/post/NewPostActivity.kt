package com.supagorn.devpractice.ui.post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.dialog.DialogAlert
import com.supagorn.devpractice.singleton.AppEventLogger
import com.supagorn.devpractice.utils.*
import kotlinx.android.synthetic.main.activity_new_post.*


class NewPostActivity : AbstractActivity(), NewPostContract.View {

    private val REQUEST_IMAGE_GALLERY = 1
    private var imageUri: Uri? = null
    private var isEditMode = false
    private val presenter: NewPostContract.Presenter = NewPostPresenter(this)

    companion object {
        fun start() {
            AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEW_REGISTER)
            val intent = Intent(MyApplication.instance, NewPostActivity::class.java)
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int = R.layout.activity_new_post

    override fun setupView() {
        isEditMode = intent.getBooleanExtra(NewPostActivity::class.java.simpleName, false)
        setTitle(R.string.title_new_post)
        setMenuRightText(R.string.post_shared)

        showBackButton()
        bindAction()

        //load image profile in circle
        GlideLoader.loadImageCircle(
                applicationContext,
                R.mipmap.ic_launcher, ivProfileImage)

    }

    override fun requireField() {
        edtBody.error = "ไม่สามารถแชร์ค่าว่างได้"
        edtBody.requestFocus()
    }

    override fun postSuccess() {
        finish()
    }

    override fun postFailed() {
        DialogAlert.show(this, "Error: could not fetch user.")
    }

    override fun error(message: String) {
        DialogAlert.show(this, message)
    }

    private fun bindAction() {
        rootView.setOnTouchListener(DismissKeyboardListener(this))
//        scrollView.setOnTouchListener(DismissKeyboardListener(this))

        setOnclickMenuRight(View.OnClickListener {
            KeyboardUtils.dismissKeyboard(this)
            presenter.submitPost(edtBody.text.toString().trim())
        })

//        ivProfileImage.setOnClickListener {
//            openGalleryIntent()
//        }
    }

    private fun openGalleryIntent() {
        IntentUtils.startIntentGallery(this, REQUEST_IMAGE_GALLERY)
    }

    private fun loadProfileUri(uri: Uri) {
        GlideLoader.load(uri, ivProfileImage)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE) {
            if (PermissionUtils.isGrantAll(permissions)) {
                openGalleryIntent()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY && data != null) {
                loadProfileUri(data.data)
                imageUri = data.data
            }
        }
    }
}