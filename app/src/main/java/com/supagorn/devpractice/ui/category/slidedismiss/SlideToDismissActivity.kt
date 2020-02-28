package com.supagorn.devpractice.ui.category.slidedismiss

import android.content.Intent
import android.view.View
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrListener
import com.r0adkll.slidr.model.SlidrPosition
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.customs.adapter.java.SimplePagerAdapter
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_slide_dismiss.*
import kotlinx.android.synthetic.main.item_zoom_image.view.*


class SlideToDismissActivity : AbstractActivity() {

    private var simplePagerAdapter: SimplePagerAdapter<String>? = null

    companion object {
        fun start() {
            val intent = Intent(MyApplication.instance, SlideToDismissActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_slide_dismiss
    }


    override fun setupView() {
        val config = SlidrConfig.Builder()
                .position(SlidrPosition.TOP)
                .sensitivity(1f)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(2400f)
                .distanceThreshold(0.35f)
//                .edge(false)
//                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                .listener(object : SlidrListener {
                    override fun onSlideClosed(): Boolean {
                        finish()
                        return true

                    }

                    override fun onSlideStateChanged(state: Int) {

                    }

                    override fun onSlideChange(percent: Float) {

                    }

                    override fun onSlideOpened() {

                    }
                })
                .build()

        Slidr.attach(this, config)
        initViewPager()
    }

    private fun initViewPager() {
        simplePagerAdapter = SimplePagerAdapter<String>().setOnInflateViewListener(object : SimplePagerAdapter.OnInflateViewListener {
            override fun <T> onBindViewHolder(item: T, itemView: View, position: Int) {
                GlideLoader.load(item as String, itemView.photoView)
            }

            override fun getLayout(): Int {
                return R.layout.item_zoom_image
            }
        })
        viewPager.adapter = simplePagerAdapter

        val lists = mutableListOf("https://scontent-sin2-2.xx.fbcdn.net/v/t1.0-0/p640x640/75472705_2965893840303052_6807025600788692992_o.jpg?_nc_cat=110&_nc_oc=AQk38saObH0UuRWYteiuolvCyd6OTNLrcK60IjCNpl-hoSBHiusrZj9j4U9CWanabCg&_nc_ht=scontent-sin2-2.xx&oh=3da84b47a538476354bde8f1c207587d&oe=5E498FE7",
                "https://scontent-sin2-2.xx.fbcdn.net/v/t1.0-9/74627097_2940814732810963_2209676041461432320_o.jpg?_nc_cat=111&_nc_oc=AQl9EqJoeHBZHIQ5ZrXS9wXstQCauvQkFFFW4TUBecACemNjfC_ijvcty6vpNiFP45o&_nc_ht=scontent-sin2-2.xx&oh=1e40f26b6340e7fc00184dbb02a2e6f0&oe=5E5BC11B",
                "https://scontent-sin2-2.xx.fbcdn.net/v/t1.0-9/72064350_2934687360090367_8422385808954097664_o.jpg?_nc_cat=110&_nc_oc=AQlXAHigWd9BpoL466VBa7I3ATjIarTYSqEokxjbOfnbSZCNCfciGuhgYfcfz8zNehU&_nc_ht=scontent-sin2-2.xx&oh=5527bb9d96704b514c668d16dfeb5b93&oe=5E444F95",
                "https://scontent-sin2-2.xx.fbcdn.net/v/t1.0-9/29684053_2469384836620624_3700711424608807416_n.jpg?_nc_cat=108&_nc_oc=AQnG0GXkg1I77QdSCCqx_1xN3QyWBRH1_-G8dP7DvTy7_89IescUsIixGwi2821EECo&_nc_ht=scontent-sin2-2.xx&oh=9c5f3590ba3ab5f272b06a8ec709085d&oe=5E4F2980",
                "https://scontent-sin2-2.xx.fbcdn.net/v/t31.0-8/12087093_1900972403461873_8419513160756800339_o.jpg?_nc_cat=103&_nc_oc=AQluReoSwW1B4j_R2Fy3ZbHa6Pg9qXB_SAfTSoUREk_MprwXr5AZnn10gaUWQokKyuE&_nc_ht=scontent-sin2-2.xx&oh=f9bf7778466b11f72d63e50874888a0d&oe=5E8A717F",
                "https://scontent-sin2-2.xx.fbcdn.net/v/t1.0-9/70251108_2904238489801921_3882712699724890112_o.jpg?_nc_cat=111&_nc_oc=AQmzlIBCmt2io-lsW1pWnvKrc283c3721CLuVND87xWrj9r0BbfW8QcWwsJ2zCeBavU&_nc_ht=scontent-sin2-2.xx&oh=7e21422281da5f9f4179791125d1e4cf&oe=5E4CE18C",
                "https://scontent-sin2-2.xx.fbcdn.net/v/t1.0-9/69024051_2890581961167574_6206121712862363648_o.jpg?_nc_cat=107&_nc_oc=AQkVJ-DnVIwoBKZnIJxDnzA0O0hFrNWgjBvRLszDppfznTBQQyt5Zy0Xr_sXtPDbIv8&_nc_ht=scontent-sin2-2.xx&oh=ba639c363e075bd41edeeff4623e2818&oe=5E8CC5FA",
                "https://scontent-sin2-2.xx.fbcdn.net/v/t1.0-9/67705171_2874465632779207_1233681063163723776_o.jpg?_nc_cat=109&_nc_oc=AQn15_op9MiPdbLBVDczaDy-1_pSGVPnWD8Qw05uXzBZnD5-PyDwKoytg7vU9Ee3NSg&_nc_ht=scontent-sin2-2.xx&oh=7bea2d3bf52a978934a6bbfcad89d3f3&oe=5E476B2D")

        simplePagerAdapter!!.setListItems(lists)

    }
}
