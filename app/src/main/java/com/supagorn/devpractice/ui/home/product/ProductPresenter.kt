package com.supagorn.devpractice.ui.home.product

import com.supagorn.devpractice.service.ProductService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection

class ProductPresenter constructor(val view: ProductContract.View,
                                   private val service: ProductService) :
        ProductContract.Presenter {

    private var pageIndex = 1

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        pageIndex = 1
        view.showProgress()
        getPromotions()
    }

    override fun stop() {
        compositeDisposable.dispose()
    }

    override fun loadMore() {
        pageIndex++
        getPromotions()
    }

    private fun getPromotions() {
        val disposable = service.getProducts(0, pageIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseCode = it.code()
                    val body = it.body()
                    view.hideProgress()
                    if (responseCode == HttpURLConnection.HTTP_OK &&
                            body != null && body.isSuccess && body.products != null && body.products.size != 0) {
                        if (pageIndex == 1)
                            view.showProducts(body.products)
                        else
                            view.addProducts(body.products)
                    } else {
                        if (pageIndex == 1) {
                            view.showNotFound()
                        } else {
                            view.showNotFoundLoadMore()
                        }
                    }
                }, {
                    if (pageIndex == 1) {
                        view.hideProgress()
                        view.showNotFound()
                    } else {
                        view.showNotFoundLoadMore()
                    }
                })
        compositeDisposable.add(disposable)

    }


}