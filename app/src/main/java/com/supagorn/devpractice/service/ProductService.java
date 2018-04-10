package com.supagorn.devpractice.service;

import com.supagorn.devpractice.model.home.ProductListEntity;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProductService {

    @POST("Product")
    @FormUrlEncoded
    Observable<Response<ProductListEntity>> getProducts(@Field("ProductCateId") int categoryId,
                                                        @Field("PageIndex") int pageIndex);
}
