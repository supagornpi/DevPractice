package com.supagorn.devpractice.customs.view

import android.content.Context
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.supagorn.devpractice.R
import com.supagorn.devpractice.utils.PriceUtil
import kotlinx.android.synthetic.main.view_price.view.*
import java.math.BigDecimal

class PriceView : LinearLayout {

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
        View.inflate(context, R.layout.view_price, this)
    }

    fun setPrice(price: BigDecimal, discountedPrice: BigDecimal, discounted: Boolean) {
        if (discounted) {
            tvPrice.visibility = View.VISIBLE
            layoutSaved.visibility = View.VISIBLE
            tvPrice.paintFlags = (tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            tvPrice.text = context.getString(R.string.price_format, price)
            tvDiscountedPrice.text = context.getString(R.string.price_format, discountedPrice)
            tvPercentage.text = context.getString(R.string.price_percentage, PriceUtil.getDiscountPercentage(price, discountedPrice))
        } else {
            tvPrice.visibility = View.GONE
            layoutSaved.visibility = View.GONE
            tvDiscountedPrice.text = context.getString(R.string.price_format, price)
        }
    }
}
