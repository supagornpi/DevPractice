package com.supagorn.devpractice.ui.sidebar

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.supagorn.devpractice.R
import com.supagorn.devpractice.dialog.DialogAlert
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.ui.login.LoginActivity
import com.supagorn.devpractice.ui.register.RegisterActivity
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.fragment_sidebar.*

/**
 * A simple [Fragment] subclass.
 */
class SidebarFragment : Fragment(), SidebarContract.View {

    private val presenter: SidebarContract.Presenter = SidebarPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sidebar, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
        bindAction()
        presenter.start()
    }

    override fun bindUserProfile(user: User) {
        tvUsername.text = user.firstName + " " + user.lastName
    }

    override fun bindUserImage(upload: Upload) {
        //load image profile in circle
        GlideLoader.loadImageCircle(
                activity!!.applicationContext,
                upload.url, ivProfileImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    private fun startAnimation() {
        imageviewNewPendingGift.startAnimation(
                AnimationUtils.loadAnimation(activity, R.anim.rotate_indefinitely))
        //load image profile in circle
        GlideLoader.loadImageCircle(
                activity!!.applicationContext,
                R.mipmap.ic_launcher, ivProfileImage)

        checkLogin()
    }

    private fun bindAction() {
        btnLogout.setOnClickListener {
            DialogAlert.show(activity!!, R.string.dialog_logout, DialogInterface.OnClickListener { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                checkLogin()
            })
        }
        ivProfileImage.setOnClickListener {
            if (!UserManager.isLoggedIn) {
                LoginActivity.start()
            } else {
                RegisterActivity.startEditMode()
            }
        }
    }

    private fun checkLogin() {
        btnLogout.visibility = if (UserManager.isLoggedIn) View.VISIBLE else View.GONE
    }
}
