package com.supagorn.devpractice.ui.home

import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.model.SamplePagerEntity
import com.supagorn.devpractice.ui.home.pager.SamplePagerView
import com.supagorn.devpractice.ui.home.product.ProductsActivity
import com.supagorn.devpractice.utils.JsonUtils
import com.supagorn.devpractice.utils.ResolutionUtils
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * Created by apple on 2/18/2018 AD.
 */

class HomeFragment : AbstractFragment() {

    override fun setLayoutView(): Int {
        return R.layout.fragment_home
    }

    override fun setupView() {
        bindUI(view!!)
        setTitle(R.string.title_home)
        showUserToggleWithAction()
        setSampleViewPager()
    }

    private fun bindUI(view: View) {
        bindAction()
    }

    private fun bindAction() {
        btnAllProduct.setOnClickListener {
            ProductsActivity.start(activity)
        }
    }

    private fun setSampleViewPager() {
        val samplePagerView = SamplePagerView(activity)
        //get json from asset
        val jsonString = JsonUtils().loadJSONFromAsset(activity, "json/banner.json")
        val samplePagerEntities = Gson().fromJson<ArrayList<SamplePagerEntity>>(jsonString, object : TypeToken<ArrayList<SamplePagerEntity>>() {

        }.type)
        //set fragmentNavigation
        samplePagerView.setNavigation(fragmentNavigation!!)
        samplePagerView.setSampleEntity(samplePagerEntities)
        //set banner Height
        layoutPagerView.layoutParams.height = ResolutionUtils.getBannerHeightFromRatio(activity)
        layoutPagerView.addView(samplePagerView)
    }
}
