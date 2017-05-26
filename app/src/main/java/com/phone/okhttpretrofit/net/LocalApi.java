package com.phone.okhttpretrofit.net;

import com.phone.okhttpretrofit.Bean.HealthClassifyBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by MLXPHONE on 2017/5/26.
 */

public interface LocalApi {

    @GET("classify")
    Observable<List<HealthClassifyBean>> getHealthClassify();

}
