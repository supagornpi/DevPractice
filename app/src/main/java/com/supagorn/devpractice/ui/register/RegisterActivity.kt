package com.supagorn.devpractice.ui.register

import android.content.Intent
import android.text.InputType
import android.widget.EditText
import com.supagorn.devpractice.MainActivity
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.dialog.DialogAlert
import com.supagorn.devpractice.enums.Gender
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.model.register.RegisterEntity
import com.supagorn.devpractice.utils.DismissKeyboardListener
import com.supagorn.devpractice.utils.KeyboardUtils
import com.supagorn.devpractice.utils.ValidatorUtils
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AbstractActivity(), RegisterContract.View {

    private val presenter: RegisterContract.Presenter = RegisterPresenter(this)

    companion object {
        fun start() {
            val intent = Intent(MyApplication.instance, RegisterActivity::class.java)
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int = R.layout.activity_register

    override fun setupView() {
        setTitle(R.string.button_register)
        showBackButton()
        initInputType()
        bindAction()

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

    override fun error(message: String) {
        DialogAlert.show(this, message)
    }

    private fun bindAction() {
        rootView.setOnTouchListener(DismissKeyboardListener(this))
        scrollView.setOnTouchListener(DismissKeyboardListener(this))

        btnRegister.setOnClickListener {
            KeyboardUtils.dismissKeyboard(this)
            presenter.register(getRegisterEntity())
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

        inputFirstName.editText.setText("name")
        inputLastName.editText.setText("last name")
        inputEmail.editText.setText("email@gmail.com")
        inputPassword.editText.setText("123456")
        inputConfirmPassword.editText.setText("123456")

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
        return entity
    }
}