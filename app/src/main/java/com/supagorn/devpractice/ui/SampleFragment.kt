package com.supagorn.devpractice.ui

import android.os.Bundle
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractFragment

/**
 * Created by apple on 2/17/2018 AD.
 */

class SampleFragment : AbstractFragment() {

    private var mTitle: String? = null

    companion object {
        private const val ARG_TITLE = "ARG_TITLE"
        fun newInstance(title: String): SampleFragment {
            val fragment = SampleFragment()
            val bundle = Bundle()
            bundle.putString(ARG_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_sample
    }

    override fun setupView() {
        //set title toolbar
        mTitle = arguments?.getString(ARG_TITLE)
        if(mTitle != null) {
            setTitle(mTitle!!)
        }
    }
}
