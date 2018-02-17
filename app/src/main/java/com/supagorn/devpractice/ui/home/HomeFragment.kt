package com.supagorn.devpractice.ui.home

import com.google.gson.Gson
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.model.SamplePagerEntity
import com.supagorn.devpractice.ui.home.pager.PagerView
import com.supagorn.devpractice.utils.JsonUtils
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by apple on 2/18/2018 AD.
 */
class HomeFragment : AbstractFragment() {

    override fun setLayoutView(): Int {
        return R.layout.fragment_home
    }

    override fun setupView() {
        setTitle(R.string.title_home)
        val pagerView = PagerView(context!!)
        val jsonString = JsonUtils().loadJSONFromAsset(context, "json/banner.json")
        pagerView.setSampleEntity(Gson().fromJson(jsonString, mutableListOf<SamplePagerEntity>().javaClass))

        rootView.addView(PagerView(context!!))
    }
}