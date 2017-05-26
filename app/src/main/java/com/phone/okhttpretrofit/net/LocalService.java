package com.phone.okhttpretrofit.net;

import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by MLXPHONE on 2017/5/26.
 */

public class LocalService {
    //baseUrl
    public static final String API_BASE_URL = "http://www.tngou.net/api/info/";

    private static final LocalApi service=getRetrofit().create(LocalApi.class);

    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;

    //设置不同类型超时的超时时间标准
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 60;
    public final static int WRITE_TIMEOUT = 60;

    private static OkHttpClient getOkHttpClient() {
        //拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("source-terminal", "Android")
                                .addHeader("device-model", Build.MODEL)
                                .addHeader("os-version", Build.VERSION.RELEASE)
                                //.addHeader("app-name",name)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return okHttpClient;
    }

    protected static Retrofit getRetrofit(){
        if (null==mRetrofit){

            if (null==mOkHttpClient){
                mOkHttpClient=getOkHttpClient();
            }

            mRetrofit=new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ResponseConverterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    public static LocalApi getService() {
        return service;
    }
}