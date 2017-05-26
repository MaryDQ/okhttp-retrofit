package com.phone.okhttpretrofit.net;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by MLXPHONE on 2017/5/26.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;
    private final Type mType;

    public GsonResponseBodyConverter(Gson gson,Type type){
        this.mGson=gson;
        this.mType=type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response=value.string();
        Log.d("Network", "response>> "+response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            if (jsonObject.getString("status").equals("true")){
                String data=jsonObject.getString("data");

                return mGson.fromJson(data,mType);
            }else {
                //获取失败将消息解析为异常消息文本
                ErrResponse errResponse=mGson.fromJson(response,ErrResponse.class);
                throw new ResultException(0,errResponse.getMsg());
            }
        }catch (JSONException e){
            e.printStackTrace();
            Log.e("Network", e.getMessage());
            return null;
        }finally {
            //
        }
    }
}
