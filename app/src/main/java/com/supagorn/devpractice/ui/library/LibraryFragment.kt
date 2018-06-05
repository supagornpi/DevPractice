package com.supagorn.devpractice.ui.library

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.leocardz.link.preview.library.LinkPreviewCallback
import com.leocardz.link.preview.library.SourceContent
import com.leocardz.link.preview.library.TextCrawler
import com.supagorn.devpractice.R
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.singleton.AppEventLogger
import com.supagorn.devpractice.ui.library.adapter.LibraryAdapter
import com.supagorn.devpractice.ui.library.model.LinkPreviewEntity
import kotlinx.android.synthetic.main.fragment_library.*

/**
 * Created by apple on 2/24/2018 AD.
 */

class LibraryFragment : AbstractFragment() {

    private var libraryAdapter = LibraryAdapter()

    override fun setLayoutView(): Int {
        return R.layout.fragment_library
    }

    override fun setupView() {
        AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEW_LIBRARY)
        setTitle(R.string.title_library)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        //start animation of place holder
        shimmerViewContainer.startShimmerAnimation()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = libraryAdapter

        setLinkData()
    }

    private fun setLinkData() {
        val links = Links.getLinks()
        //load detail from url
        for (link: String in links) {
            val textCrawler = TextCrawler()
            textCrawler.makePreview(object : LinkPreviewCallback {
                override fun onPre() {}
                override fun onPos(sourceContent: SourceContent, isNull: Boolean) {
                    if (!isNull) {
                        //hide shimmer
                        if (shimmerViewContainer == null) {
                            return
                        }
                        if (shimmerViewContainer.isShown) {
                            //stop animation place holder
                            shimmerViewContainer.stopShimmerAnimation()
                            //hide place holder
                            shimmerViewContainer.visibility = View.GONE
                        }
                        val imageUrl = sourceContent.images.lastOrNull() ?: ""
                        libraryAdapter.addLinks(LinkPreviewEntity(sourceContent.title, sourceContent.description,
                                sourceContent.url, imageUrl))
                    }
                }
            }, link)
        }
    }
}
