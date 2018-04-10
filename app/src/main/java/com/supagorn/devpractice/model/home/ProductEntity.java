package com.supagorn.devpractice.model.home;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.math.BigDecimal;
import java.util.List;

@Parcel
public class ProductEntity {

    @SerializedName("ProductId")
    private int productId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Detail")
    private String detail;
    @SerializedName("NormalPrice")
    private BigDecimal NormalPrice;
    @SerializedName("PromotionPrice")
    private BigDecimal promotionPrice;
    @SerializedName("IsPromotion")
    private boolean isPromotion;
    @SerializedName("Images")
    private String imageUrl;
    @SerializedName("ProductUrl")
    private String productUrl;
    @SerializedName("ImagesList")
    private List<String> imageList;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getNormalPrice() {
        return NormalPrice;
    }

    public void setNormalPrice(BigDecimal normalPrice) {
        NormalPrice = normalPrice;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
