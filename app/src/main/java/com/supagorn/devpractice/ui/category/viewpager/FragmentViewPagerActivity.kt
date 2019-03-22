package com.supagorn.devpractice.ui.category.viewpager

import android.content.Intent
import android.support.v4.app.Fragment
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.customs.VerticalPageTransformer
import com.supagorn.devpractice.customs.adapter.java.SimpleFragmentPagerAdapter
import com.supagorn.devpractice.customs.widget.SwipeOrientationViewPager
import kotlinx.android.synthetic.main.activity_custom_viewpager.*


class FragmentViewPagerActivity : AbstractActivity() {

    companion object {
        fun start() {
            val intent = Intent(MyApplication.instance, FragmentViewPagerActivity::class.java)
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
        val lists = mutableListOf("Mango", "Banana", "Melon", "Strawberry", "Apple",
                "Water melon", "Orange", "Peach")

        val adapter: SimpleFragmentPagerAdapter<String> = SimpleFragmentPagerAdapter(supportFragmentManager,
                lists, object : SimpleFragmentPagerAdapter.OnBindViewListener {
            override fun <T : Any?> getItem(item: T, position: Int): Fragment {
                val text = item as String
                return SampleFragment.newInstance(position, "$text #$position")
            }

            override fun getPageTitle(position: Int): CharSequence {
                return "Page $position"
            }
        })

        viewPager.adapter = adapter
        viewPager.setSwipeOrientation(SwipeOrientationViewPager.VERTICAL)
        viewPager.setPageTransformer(true, VerticalPageTransformer())

    }
}
