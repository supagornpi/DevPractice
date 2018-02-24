package com.supagorn.devpractice.ui.library

import android.support.v7.widget.LinearLayoutManager
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.ui.library.adapter.LibraryAdapter
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
        setTitle(R.string.title_library)
        initRecyclerView()
    }

    private fun  initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = libraryAdapter
        libraryAdapter.setLinks(Links.getLinks())
    }

}
