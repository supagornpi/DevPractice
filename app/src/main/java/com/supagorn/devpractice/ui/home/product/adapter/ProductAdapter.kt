package com.supagorn.devpractice.ui.home.product.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supagorn.devpractice.R
import com.supagorn.devpractice.model.home.ProductEntity
import com.supagorn.devpractice.utils.GlideLoader
import com.supagorn.devpractice.utils.PriceUtil
import com.supagorn.devpractice.utils.ResolutionUtils
import kotlinx.android.synthetic.main.layout_product_list_item.view.*
import java.util.*

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var products: MutableList<ProductEntity> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    companion object {
        const val TYPE_LOAD = 0
        const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_list_item, parent, false)

            return ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_load_more, parent, false)
            return ViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (products[position].productId == 0) {
            return TYPE_LOAD
        } else {
            return TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {

        if (getItemViewType(position) == TYPE_ITEM) {

            val product = products[position]
            ResolutionUtils.setViewSize(holder.itemView.context, 3f, 3f, holder.itemView.ivProduct)
            GlideLoader.load(holder.itemView.context, product.imageUrl, holder.itemView.ivProduct)
            holder.itemView.tvProductName.text = product.name
            holder.itemView.priceView.setPrice(product.normalPrice, product.promotionPrice, product.isPromotion)
            val percentage = PriceUtil.getDiscountPercentage(product.normalPrice, product.promotionPrice)
            holder.itemView.tvPercentageBadge.text = holder.itemView.context.getString(R.string.price_percentage, percentage)
            //show percentage if more than 0
            holder.itemView.layoutPercentageBadge.visibility = if (product.isPromotion && percentage.toInt() > 0) View.VISIBLE else View.GONE
            holder.itemView.setOnClickListener(View.OnClickListener {
                if (onItemClickListener == null) {
                    return@OnClickListener
                }
                onItemClickListener!!.onItemClicked(product)
            })

        }
    }

    override fun getItemCount(): Int {
        return this.products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setProducts(products: MutableList<ProductEntity>, isLoadMore: Boolean) {
        if (isLoadMore)
            products.add(ProductEntity())
        this.products = products
        notifyDataSetChanged()
    }

    fun addProducts(products: List<ProductEntity>) {
        val start = this.products.size
        this.products.addAll(products.size - 1, products)
        notifyItemRangeInserted(start, this.products.size)
    }

    interface OnItemClickListener {
        fun onItemClicked(product: ProductEntity)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun clearLoadMore() {
        this.products.removeAt(products.size - 1)
        notifyItemRemoved(products.size)
    }


}
