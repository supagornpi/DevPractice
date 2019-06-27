package com.supagorn.devpractice.ui.home.product

import android.content.Intent
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.supagorn.devpractice.MyApplication
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractActivity
import com.supagorn.devpractice.customs.adapter.kotlin.CustomAdapter
import com.supagorn.devpractice.customs.listener.ScrollLoadMoreListener
import com.supagorn.devpractice.customs.view.ProductView
import com.supagorn.devpractice.model.home.ProductEntity
import com.supagorn.devpractice.retrofit.ServiceFactory
import com.supagorn.devpractice.service.ProductService
import kotlinx.android.synthetic.main.view_recyclerview_with_progress.*

class ProductsActivity : AbstractActivity(), ProductContract.View {

    private lateinit var adapter: CustomAdapter<ProductEntity>
    private val presenter: ProductContract.Presenter

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
        adapter = CustomAdapter<ProductEntity>(object : CustomAdapter.OnBindViewListener {
            override fun <T> onBindViewHolder(item: T, itemView: View, viewType: Int, position: Int) {
                val product = item as ProductEntity
                (itemView as ProductView).setProduct(product)
            }

            override fun onCreateView(): View {
                return ProductView(this@ProductsActivity)
            }
        }).setOnLoadMoreListener(recyclerView, ProductEntity(), object : ScrollLoadMoreListener.OnLoadMoreListener {
            override fun onLoadMore() {
                if (adapter.itemCount > 0) {
                    Handler().postDelayed({
                        presenter.loadMore()
                    }, 500)
                }
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
    }

    private fun bindAction() {

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
        adapter.setListItem(products)
    }

    override fun addProducts(products: List<ProductEntity>) {
        adapter.addListItem(products)
    }

    override fun showNotFoundLoadMore() {
        adapter.hideLoadMore()
    }
}
