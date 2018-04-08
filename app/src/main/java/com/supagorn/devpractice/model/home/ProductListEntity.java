package com.supagorn.devpractice.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListEntity {

    @SerializedName("ProductList")
    private List<ProductEntity> products;
    @SerializedName("ErrCode")
    private int errorCode;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ErrMessage")
    private String errorMessage;

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
