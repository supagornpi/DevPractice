package com.supagorn.devpractice.ui.category.viewpager

import android.content.Intent
import android.view.View
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.customs.widget.SwipeOrientationViewPager
import com.supagorn.devpractice.customs.adapter.java.SimplePagerAdapter
import kotlinx.android.synthetic.main.activity_custom_viewpager.*
import kotlinx.android.synthetic.main.item_custom_viewpager.view.*


class ViewPagerActivity : AbstractActivity() {

    private var simplePagerAdapter: SimplePagerAdapter<String>? = null

    companion object {
        fun start() {
            val intent = Intent(MyApplication.instance, ViewPagerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_custom_viewpager
    }

    override fun setupView() {
        initViewPager()
    }

    private fun initViewPager() {
        simplePagerAdapter = SimplePagerAdapter<String>().setOnInflateViewListener(object : SimplePagerAdapter.OnInflateViewListener {
            override fun <T> onBindViewHolder(item: T, itemView: View, position: Int) {
                itemView.tvPageTitle.text = item as String
            }

            override fun getLayout(): Int {
                return R.layout.item_custom_viewpager
            }
        })
        viewPager.adapter = simplePagerAdapter
        viewPager.setSwipeOrientation(SwipeOrientationViewPager.VERTICAL)

        val lists = mutableListOf("Mango", "Banana", "Melon", "Strawberry", "Apple")

        simplePagerAdapter!!.setListItems(lists)

    }
}
