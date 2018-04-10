package com.supagorn.devpractice.ui.home.product

import com.supagorn.devpractice.base.BasePresenter
import com.supagorn.devpractice.base.BaseView
import com.supagorn.devpractice.model.home.ProductEntity

interface ProductContract {
    interface Presenter : BasePresenter {
        fun loadMore()

    }

    interface View : BaseView {
        fun showProducts(products: MutableList<ProductEntity>)
        fun addProducts(products: List<ProductEntity>)
        fun showNotFoundLoadMore()
    }
}
