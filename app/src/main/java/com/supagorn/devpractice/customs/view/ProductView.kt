package com.supagorn.devpractice.customs.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.model.home.ProductEntity
import com.supagorn.devpractice.utils.GlideLoader
import com.supagorn.devpractice.utils.PriceUtil
import com.supagorn.devpractice.utils.ResolutionUtils
import kotlinx.android.synthetic.main.layout_product_list_item.view.*

class ProductView : LinearLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_product_list_item, this)
    }

    fun setProduct(product: ProductEntity) {
        ResolutionUtils.setViewSize(context, 3f, 3f, ivProduct)
        GlideLoader.load(context, product.imageUrl, ivProduct)
        tvProductName.text = product.name
        priceView.setPrice(product.normalPrice, product.promotionPrice, product.isPromotion)
        val percentage = PriceUtil.getDiscountPercentage(product.normalPrice, product.promotionPrice)
        tvPercentageBadge.text = context.getString(R.string.price_percentage, percentage)
        //show percentage if more than 0
        layoutPercentageBadge.visibility = if (product.isPromotion && percentage.toInt() > 0) View.VISIBLE else View.GONE

    }
}
