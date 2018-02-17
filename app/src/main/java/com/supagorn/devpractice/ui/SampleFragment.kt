package com.supagorn.devpractice.ui

import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractFragment

/**
 * Created by apple on 2/17/2018 AD.
 */

class SampleFragment : AbstractFragment() {

    companion object {
        fun newInstance(): SampleFragment {
            return SampleFragment()
        }
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_sample
    }

    override fun setupView() {

    }
}
