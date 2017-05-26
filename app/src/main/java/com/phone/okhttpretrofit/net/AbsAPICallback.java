package com.phone.okhttpretrofit.net;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by MLXPHONE on 2017/5/26.
 */

public abstract class AbsAPICallback<T> extends Subscriber<T> {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    protected AbsAPICallback() {
    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;

        //获取根源异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    break;
                case FORBIDDEN:
                    break;
                case NOT_FOUND:
                    break;
                case REQUEST_TIMEOUT:
                    break;
                case GATEWAY_TIMEOUT:
                    break;
                case INTERNAL_SERVER_ERROR:
                    break;
                case BAD_GATEWAY:
                    break;
                case SERVICE_UNAVAILABLE:
                    break;
                default:
                    //Toast.makeText(App.getInstance(), R.string.server_http_error, Toast.LENGTH_SHORT).show();
                    break;
            }
        }else if (e instanceof SocketTimeoutException) {
            //Toast.makeText(App.getInstance(), R.string.network_error, Toast.LENGTH_SHORT).show();
        } else if (e instanceof ResultException) {//服务器返回的错误
            ResultException resultException = (ResultException) e;
            //  Toast.makeText(App.getInstance(), resultException.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            // Toast.makeText(App.getInstance(), R.string.data_error, Toast.LENGTH_SHORT).show(); //均视为解析错误
        } else if(e instanceof ConnectException){
            // Toast.makeText(App.getInstance(), R.string.server_http_error, Toast.LENGTH_SHORT).show();
        } else {//未知错误

        }

        onCompleted();

    }

    protected abstract void onDone(T t);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        onDone(t);
    }
}
