package com.supagorn.devpractice.ui.category

import com.supagorn.devpractice.enums.Categories

class CategoryPresenter constructor(val view: CategoryContract.View) : CategoryContract.Presenter {

    override fun start() {
        val categories: MutableList<Categories> = mutableListOf()
        categories.add(Categories.Video)
        categories.add(Categories.ViewPagerVertical)
        categories.add(Categories.FragmentViewPagerVertical)
        categories.add(Categories.SlideToDismissActivity)
        view.showCategory(categories)
    }

    override fun stop() {

    }
}