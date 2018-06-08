package com.supagorn.devpractice.ui.category

import com.supagorn.devpractice.base.BasePresenter
import com.supagorn.devpractice.enums.Categories

interface CategoryContract {
    interface Presenter : BasePresenter {

    }

    interface View {
        fun showCategory(categories: MutableList<Categories>)
    }
}