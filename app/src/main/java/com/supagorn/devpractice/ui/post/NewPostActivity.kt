package com.supagorn.devpractice.ui.post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.dialog.DialogAlert
import com.supagorn.devpractice.firebase.PostManager
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.home.Post
import com.supagorn.devpractice.singleton.AppEventLogger
import com.supagorn.devpractice.utils.*
import kotlinx.android.synthetic.main.activity_new_post.*


class NewPostActivity : AbstractActivity(), NewPostContract.View {

    private val REQUEST_IMAGE_GALLERY = 1
    private var imageUri: Uri? = null
    private var isEditMode = false
    private var postKey: String? = null
    private val presenter: NewPostContract.Presenter = NewPostPresenter(this)
    private lateinit var userManager: UserManager

    companion object {

        const val EXTRA_EDIT_MODE = "EXTRA_EDIT_MODE"
        const val EXTRA_POST_KEY = "EXTRA_POST_KEY"

        fun start() {
            AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEW_NEW_POST)
            val intent = Intent(MyApplication.instance, NewPostActivity::class.java)
            MyApplication.instance.startActivity(intent)
        }

        fun startEditMode(postKey: String) {
            AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEW_EDIT_POST)
            val intent = Intent(MyApplication.instance, NewPostActivity::class.java)
            intent.putExtra(EXTRA_EDIT_MODE, true)
            intent.putExtra(EXTRA_POST_KEY, postKey)
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int = R.layout.activity_new_post

    override fun setupView() {
        userManager = UserManager.instance
        isEditMode = intent.getBooleanExtra(EXTRA_EDIT_MODE, false)
        postKey = intent.getStringExtra(EXTRA_POST_KEY)

        setTitle(if (isEditMode) R.string.title_edit_post else R.string.title_new_post)
        setMenuRightText(R.string.post_shared)

        showBackButton()
        bindAction()

        fetchUserProfile()

        if (isEditMode) {
            postKey?.let {
                presenter.fetchPost(it)
            }
        }
    }

    override fun showPost(body: String) {
        edtBody.setText(body)
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

            if (isEditMode) {
                //edit post
                presenter.editPost(edtBody.text.toString().trim(), postKey!!)
            } else{
                //new post
                presenter.submitPost(edtBody.text.toString().trim())
            }
        })

//        ivProfileImage.setOnClickListener {
//            openGalleryIntent()
//        }
    }

    private fun fetchUserProfile() {
        userManager.getUserImage(object : UserManager.OnValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userImage = dataSnapshot.getValue(Upload::class.java)
                if (userImage?.url != null) {
                    //load image profile in circle
                    GlideLoader.loadImageCircle(
                            applicationContext,
                            userImage.url, ivProfileImage)
                }
            }
        })

        userManager.getUserProfile(object : UserManager.OnValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    tvName.text = user.firstName + " " + user.lastName
                }
            }
        })
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