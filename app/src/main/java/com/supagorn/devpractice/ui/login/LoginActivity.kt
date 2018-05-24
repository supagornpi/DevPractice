package com.supagorn.devpractice.ui.login

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.supagorn.devpractice.MainActivity
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.dialog.DialogAlert
import com.supagorn.devpractice.ui.register.RegisterActivity
import com.supagorn.devpractice.utils.DismissKeyboardListener
import com.supagorn.devpractice.utils.ValidatorUtils
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AbstractActivity(), LoginContract.View {

    private val presenter: LoginContract.Presenter = LoginPresenter(this)

    companion object {
        fun start() {
            val intent = Intent(MyApplication.instance, LoginActivity::class.java)
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int = R.layout.activity_login

    override fun setupView() {
        setTitle(R.string.login_title)
        showBackButton()
        bindAction()

    }

    override fun showEmailInvalid() {
        ValidatorUtils.setErrorInput(applicationContext, edtEmail, R.string.error_email_invalid)
    }

    override fun showPasswordInvalid() {
        ValidatorUtils.setErrorInput(applicationContext, edtPassword, R.string.error_password_invalid)
    }

    override fun loginSuccess() {
        MainActivity.start()
    }

    override fun loginFailed() {
        DialogAlert.show(this, R.string.dialog_login_failed)
    }

    override fun error(message: String) {
        DialogAlert.show(this, message)
    }

    private fun bindAction() {
        rootView.setOnTouchListener(DismissKeyboardListener(this))

        btnLogin.setOnClickListener {
            presenter.validate(edtEmail.text.toString().trim(), edtPassword.text.toString().trim())
        }
        btnClearEmail.setOnClickListener {
            edtEmail.text.clear()
        }
        btnClearPassword.setOnClickListener {
            edtPassword.text.clear()
        }
        btnRegister.setOnClickListener {
            //go to register
            RegisterActivity.start()
        }

        addTextChangeListener(edtEmail, btnClearEmail)
        addTextChangeListener(edtPassword, btnClearPassword)
    }

    private fun addTextChangeListener(editText: EditText, btnClear: View) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btnClear.visibility = if (s?.length!! > 0) View.VISIBLE else View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}