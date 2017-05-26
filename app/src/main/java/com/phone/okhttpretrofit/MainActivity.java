package com.phone.okhttpretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.phone.okhttpretrofit.Bean.HealthClassifyBean;
import com.phone.okhttpretrofit.net.AbsAPICallback;
import com.phone.okhttpretrofit.net.LocalService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestData();
    }

    private void requestData() {
        LocalService.getService().getHealthClassify()
                    .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<HealthClassifyBean>>() {
                    @Override
                    protected void onDone(List<HealthClassifyBean> healthClassifyBeen) {
                        //请求成功，做相应的操作
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
