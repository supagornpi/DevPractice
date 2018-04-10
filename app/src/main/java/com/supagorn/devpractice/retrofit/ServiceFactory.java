package com.supagorn.devpractice.retrofit;

import com.supagorn.devpractice.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    public static <T> T create(final Class<T> clazz) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)   // connect timeout
                .readTimeout(30, TimeUnit.SECONDS);     // socket timeout

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build().create(clazz);
    }
}
