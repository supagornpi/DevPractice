package com.supagorn.devpractice.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import com.supagorn.devpractice.R;

import java.math.BigDecimal;
import java.math.MathContext;

public class PriceUtil {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public static BigDecimal getDiscountPercentage(BigDecimal originalPrice, BigDecimal discountedPrice) {
        return (originalPrice.subtract(discountedPrice))
                .divide(originalPrice, MathContext.DECIMAL128)
                .multiply(ONE_HUNDRED);
    }

    public static void setProductPrice(BigDecimal discountedPrice, BigDecimal originalPrice, boolean isDiscount, TextView txt, Context context) {
        setNoPriceRange(originalPrice, discountedPrice, isDiscount, txt, context);
    }

    private static void setNoPriceRange(BigDecimal originalPrice, BigDecimal discountedPrice, boolean discount, TextView textView, Context context) {
        String priceWithFormat = context.getString(R.string.price_format, originalPrice);
        String discountedPriceWithFormat = context.getString(R.string.price_format, discountedPrice);

        ForegroundColorSpan redForegroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.color_red));
        ForegroundColorSpan grayForegroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.color_black));

        if (discount) {
            SpannableStringBuilder ssbForPriceTxtWithStrikeTrough = new SpannableStringBuilder(priceWithFormat);
            ssbForPriceTxtWithStrikeTrough.setSpan(grayForegroundColorSpan, 0, ssbForPriceTxtWithStrikeTrough.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssbForPriceTxtWithStrikeTrough.append("  ");
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            ssbForPriceTxtWithStrikeTrough.append(discountedPriceWithFormat);
            ssbForPriceTxtWithStrikeTrough.setSpan(strikethroughSpan, 0, priceWithFormat.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssbForPriceTxtWithStrikeTrough.setSpan(new RelativeSizeSpan(1.5f), priceWithFormat.length(), ssbForPriceTxtWithStrikeTrough.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssbForPriceTxtWithStrikeTrough.setSpan(redForegroundColorSpan, priceWithFormat.length(), ssbForPriceTxtWithStrikeTrough.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableStringBuilder ssbForPriceTxtWithOutStrikeTrough = new SpannableStringBuilder(ssbForPriceTxtWithStrikeTrough);
            ssbForPriceTxtWithOutStrikeTrough.setSpan(redForegroundColorSpan, priceWithFormat.length(), ssbForPriceTxtWithStrikeTrough.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(ssbForPriceTxtWithStrikeTrough, TextView.BufferType.EDITABLE);
        } else {
            SpannableStringBuilder ssbForPriceTxtWithOutStrikeTrough = new SpannableStringBuilder(priceWithFormat);
            ssbForPriceTxtWithOutStrikeTrough.setSpan(new RelativeSizeSpan(1.5f), 0, ssbForPriceTxtWithOutStrikeTrough.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssbForPriceTxtWithOutStrikeTrough.setSpan(redForegroundColorSpan, 0, ssbForPriceTxtWithOutStrikeTrough.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(ssbForPriceTxtWithOutStrikeTrough, TextView.BufferType.EDITABLE);
        }
    }
}
