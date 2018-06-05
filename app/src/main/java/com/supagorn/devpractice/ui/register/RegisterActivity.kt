package com.supagorn.devpractice.ui.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.supagorn.devpractice.MainActivity
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.dialog.DialogAlert
import com.supagorn.devpractice.enums.Gender
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity
import com.supagorn.devpractice.utils.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File


class RegisterActivity : AbstractActivity(), RegisterContract.View {

    private val REQUEST_IMAGE_GALLERY = 1
    private var imageUri: Uri? = null
    private var isEditMode = false
    private val presenter: RegisterContract.Presenter = RegisterPresenter(this)

    companion object {
        fun start() {
            val intent = Intent(MyApplication.instance, RegisterActivity::class.java)
            MyApplication.instance.startActivity(intent)
        }

        fun startEditMode() {
            val intent = Intent(MyApplication.instance, RegisterActivity::class.java)
            intent.putExtra(RegisterActivity::class.java.simpleName, true)
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int = R.layout.activity_register

    override fun setupView() {
        isEditMode = intent.getBooleanExtra(RegisterActivity::class.java.simpleName, false)
        setTitle(if (isEditMode) R.string.register_edit_mode else R.string.register)
        inputPassword.visibility = if (isEditMode) View.GONE else View.VISIBLE
        inputConfirmPassword.visibility = if (isEditMode) View.GONE else View.VISIBLE
        showBackButton()
        initInputType()
        bindAction()

        //load image profile in circle
        GlideLoader.loadImageCircle(
                applicationContext,
                R.mipmap.ic_launcher, ivProfileImage)

        if (isEditMode) {
            presenter.fetchUserProfile()
            presenter.fetchUserImage()
        }
    }

    override fun bindUserProfile(user: User) {
        inputFirstName.editText.setText(user.firstName)
        inputLastName.editText.setText(user.lastName)
        inputEmail.editText.setText(user.email)
        inputMobile.editText.setText(user.mobile)
        if (user.gender != null) {
            inputGender.setSelectedPosition(user.gender.ordinal)
        }
    }

    override fun bindUserImage(upload: Upload) {
        //load image profile in circle
        GlideLoader.loadImageCircle(
                applicationContext,
                upload.url, ivProfileImage)
    }

    override fun requireField(requireField: RequireField) {
        val editText: EditText
        when (requireField) {
            RequireField.FirstName -> {
                editText = inputFirstName.editText
            }
            RequireField.LastName -> {
                editText = inputLastName.editText
            }
            RequireField.Email -> {
                editText = inputEmail.editText
            }
            RequireField.Password -> {
                editText = inputPassword.editText
            }
            RequireField.ConfirmPassword -> {
                editText = inputConfirmPassword.editText
            }
        }
        ValidatorUtils.setErrorInput(applicationContext, editText, R.string.error_please_fill)
    }

    override fun showMobileInvalid() {
        ValidatorUtils.setErrorInput(applicationContext, inputMobile.editText, R.string.error_mobile_invalid)
    }

    override fun showEmailInvalid() {
        ValidatorUtils.setErrorInput(applicationContext, inputEmail.editText, R.string.error_email_invalid)
    }

    override fun showPasswordInvalid() {
        ValidatorUtils.setErrorInput(applicationContext, inputPassword.editText, R.string.error_password_invalid)
    }

    override fun showConfirmPasswordNotMatch() {
        ValidatorUtils.setErrorInput(applicationContext, inputConfirmPassword.editText, R.string.error_password_not_match)
    }

    override fun registerSuccess() {
        MainActivity.start()
    }

    override fun registerFailed() {
        DialogAlert.show(this, R.string.dialog_register_failed)
    }

    override fun updateProfileSuccess() {
        finish()
    }

    override fun error(message: String) {
        DialogAlert.show(this, message)
    }

    private fun bindAction() {
        rootView.setOnTouchListener(DismissKeyboardListener(this))
        scrollView.setOnTouchListener(DismissKeyboardListener(this))

        ivProfileImage.setOnClickListener {
            openGalleryIntent()
        }
        btnRegister.setOnClickListener {
            KeyboardUtils.dismissKeyboard(this)
            if (isEditMode) {
                presenter.editProfile(getRegisterEntity())
            } else {
                presenter.register(getRegisterEntity())
            }
        }
    }

    private fun initInputType() {
        inputFirstName.setInputName(R.string.register_first_name, R.string.register_first_name, InputType.TYPE_CLASS_TEXT)
        inputLastName.setInputName(R.string.register_last_name, R.string.register_last_name, InputType.TYPE_CLASS_TEXT)
        inputMobile.setInputName(R.string.register_mobile, R.string.register_mobile, InputType.TYPE_CLASS_NUMBER)
        inputEmail.setInputName(R.string.register_email, R.string.register_email, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        inputGender.setInputName(R.string.register_gender, R.string.register_gender, InputType.TYPE_CLASS_NUMBER)
        inputPassword.setInputName(R.string.register_password, R.string.register_password, InputType.TYPE_TEXT_VARIATION_PASSWORD)
        inputConfirmPassword.setInputName(R.string.register_confirm_password, R.string.register_confirm_password, InputType.TYPE_TEXT_VARIATION_PASSWORD)

        inputGender.setSpinner(R.array.gender_list, true)
        inputMobile.setMaxLength(10)
        inputMobile.setTitleColor(R.color.color_red_dark)
        inputEmail.setTitleColor(R.color.color_red_dark)

    }

    private fun getRegisterEntity(): RegisterEntity {
        val entity = RegisterEntity()
        entity.firstName = inputFirstName.input
        entity.lastName = inputLastName.input
        entity.mobile = inputMobile.input
        entity.email = inputEmail.input
        entity.gender = Gender.parse(inputGender.getSelectedPosition())
        entity.password = inputPassword.input
        entity.confirmPassword = inputConfirmPassword.input

        if (imageUri != null) {
            val file = FileUtils.getResizedBitmap(this, File(FileUtils.getRealPathFromURI(this, imageUri)))
            entity.imageUri = Uri.fromFile(file)
        }

        return entity
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