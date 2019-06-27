package com.supagorn.devpractice.ui.category

import androidx.recyclerview.widget.GridLayoutManager
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.enums.Categories
import com.supagorn.devpractice.singleton.AppEventLogger
import com.supagorn.devpractice.ui.category.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : AbstractFragment(), CategoryContract.View {

    private val categoryAdapter = CategoryAdapter()
    private val presenter: CategoryContract.Presenter = CategoryPresenter(this)

    override fun setLayoutView(): Int = R.layout.fragment_category

    override fun setupView() {
        AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEW_CUSTOMS)
        setTitle(R.string.title_customs)
        initRecyclerView()
        presenter.start()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2, LinearLayout.VERTICAL, false)
        recyclerView.adapter = categoryAdapter
    }

    override fun showCategory(categories: MutableList<Categories>) {
        categoryAdapter.setList(categories)
    }
}