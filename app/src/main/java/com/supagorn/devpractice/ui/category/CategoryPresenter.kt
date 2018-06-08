package com.supagorn.devpractice.ui.category

import com.supagorn.devpractice.enums.Categories

class CategoryPresenter constructor(val view: CategoryContract.View) : CategoryContract.Presenter {

    override fun start() {
        val settings: MutableList<Categories> = mutableListOf()
        settings.add(Categories.Video)
        view.showCategory(settings)
    }

    override fun stop() {

    }
}