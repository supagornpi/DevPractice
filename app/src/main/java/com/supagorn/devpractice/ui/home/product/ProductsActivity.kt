package com.supagorn.devpractice.ui.home.product

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.model.home.ProductEntity
import com.supagorn.devpractice.retrofit.ServiceFactory
import com.supagorn.devpractice.service.ProductService
import com.supagorn.devpractice.ui.home.product.adapter.ProductAdapter
import com.supagorn.devpractice.utils.ScrollLoadMoreListener
import kotlinx.android.synthetic.main.view_recyclerview_with_progress.*

class ProductsActivity : AbstractActivity(), ProductContract.View, ScrollLoadMoreListener.LoadMoreListener {

    private var adapter = ProductAdapter()
    private val presenter: ProductContract.Presenter
    private lateinit var onScrollLoadMoreListener: ScrollLoadMoreListener

    init {
        presenter = ProductPresenter(this, ServiceFactory.create(ProductService::class.java))
    }

    companion object {
        fun start() {
            val intent = Intent(MyApplication.instance, ProductsActivity::class.java)
            MyApplication.instance.startActivity(intent)
        }
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_product
    }

    override fun setupView() {
        showBackButton()
        initRecyclerView()
        bindAction()
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
        onScrollLoadMoreListener = ScrollLoadMoreListener(this)
        recyclerView.addOnScrollListener(onScrollLoadMoreListener)

        adapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClicked(product: ProductEntity) {
//                ProductDetailActivity.start(context, product)
            }
        })
    }

    private fun bindAction() {

    }

    private fun setLoadMoreData() {
        Handler().postDelayed(Runnable {
            presenter.loadMore()
        }, 1000)

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showNotFound() {
        tvNotFound.visibility = View.VISIBLE
    }

    override fun hideNotFound() {
        tvNotFound.visibility = View.GONE
    }

    override fun showProducts(products: MutableList<ProductEntity>) {
        if (products.size < 10) {
            onScrollLoadMoreListener.isLoadingMore = true
            adapter.setProducts(products, false)
        } else {
            onScrollLoadMoreListener.isLoadingMore = false
            adapter.setProducts(products, true)
        }
    }

    override fun addProducts(products: List<ProductEntity>) {
        onScrollLoadMoreListener.isLoadingMore = false
        adapter.addProducts(products)
    }

    override fun showNotFoundLoadMore() {
        adapter.clearLoadMore()
    }

    override fun onLoadMore() {
        if (adapter.itemCount > 0) {
            setLoadMoreData()
        }
    }
}
