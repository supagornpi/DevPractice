package com.supagorn.devpractice.ui.sidebar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.supagorn.devpractice.R
import com.supagorn.devpractice.ui.login.LoginActivity
import com.supagorn.devpractice.ui.register.RegisterActivity
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.fragment_sidebar.*

/**
 * A simple [Fragment] subclass.
 */
class SidebarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sidebar, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
        bindAction()
    }

    private fun startAnimation() {
        imageviewNewPendingGift.startAnimation(
                AnimationUtils.loadAnimation(activity, R.anim.rotate_indefinitely))
        //load image profile in circle
        GlideLoader.loadImageCircle(
                activity!!.applicationContext,
                R.mipmap.ic_launcher, imgProfileImage)
    }

    private fun bindAction() {
        btnLogin.setOnClickListener {
            LoginActivity.start()
        }
    }
}
