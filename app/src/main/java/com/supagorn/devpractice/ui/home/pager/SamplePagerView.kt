package com.supagorn.devpractice.ui.home.pager

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.request.RequestOptions
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.FragmentNavigation
import com.supagorn.devpractice.customs.adapter.java.SimplePagerAdapter
import com.supagorn.devpractice.model.SamplePagerEntity
import com.supagorn.devpractice.ui.home.pager.detail.SamplePagerDetailFragment
import com.supagorn.devpractice.utils.GlideLoader
import com.supagorn.devpractice.utils.ResolutionUtils
import kotlinx.android.synthetic.main.view_sample_pager.view.*

/**
 * Created by apple on 18/2/2018 AD.
 */

class SamplePagerView : LinearLayout {

    private var fragmentNavigation: FragmentNavigation? = null

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

    fun setNavigation(fragmentNavigation: FragmentNavigation) {
        this.fragmentNavigation = fragmentNavigation
    }

    fun setSampleEntity(sampleEntities: ArrayList<SamplePagerEntity>) {
        val pagerAdapter = SimplePagerAdapter<SamplePagerEntity>().setOnInflateViewListener(object : SimplePagerAdapter.OnInflateViewListener {
            override fun getLayout(): Int {
                return R.layout.layout_item_sample_pager
            }

            override fun <T> onBindViewHolder(item: T, itemView: View, position: Int) {
                //cast object
                item as SamplePagerEntity
                val imgBanner = itemView.findViewById(R.id.imgBanner) as ImageView
                //set banner height
                imgBanner.layoutParams.height = ResolutionUtils.getBannerHeightFromRatio(this@SamplePagerView.context)
                GlideLoader.load(this@SamplePagerView.context, item.imageUrl,
                        RequestOptions().centerCrop(), imgBanner)
            }
        }).setOnItemClickListener(object : SimplePagerAdapter.OnItemClickListener {
            override fun <T> onItemClicked(item: T) {
                if (fragmentNavigation != null) {
                    item as SamplePagerEntity
                    fragmentNavigation!!.open(SamplePagerDetailFragment.newInstance(item))
                }
            }
        })

        pagerAdapter.setListItems(sampleEntities)

        //set product image list
        viewPager.adapter = pagerAdapter
//        circleindicator.removeAllViews()
//        if (pagerAdapter.count > 1) {
//            circleindicator.setViewPager(viewPager)
//        }
    }

}
