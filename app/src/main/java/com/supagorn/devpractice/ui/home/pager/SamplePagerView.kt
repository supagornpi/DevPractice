package com.supagorn.devpractice.ui.home.pager

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.model.SamplePagerEntity
import com.supagorn.devpractice.ui.home.pager.adapter.SamplePagerAdapter
import kotlinx.android.synthetic.main.view_sample_pager.view.*

/**
 * Created by apple on 18/2/2018 AD.
 */

class SamplePagerView : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        View.inflate(context, R.layout.view_sample_pager, this)
    }

    fun setSampleEntity(sampleEntities: ArrayList<SamplePagerEntity>) {
        val pagerAdapter = SamplePagerAdapter()
        pagerAdapter.setSamplePagerEntities(sampleEntities)

        //set product image list
        viewPager.adapter = pagerAdapter
        circleindicator.removeAllViews()
        if (pagerAdapter.count > 1) {
            circleindicator.setViewPager(viewPager)
        }
    }

}
