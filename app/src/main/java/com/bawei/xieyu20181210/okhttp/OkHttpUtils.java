package com.bawei.xieyu20181210.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {

    private static volatile OkHttpUtils mInstance;
    private OkHttpClient mClient;

    private Handler mhandler=new Handler(Looper.getMainLooper());

    public static OkHttpUtils getmInstance() {
        if(mInstance==null){
            synchronized (OkHttpUtils.class){
                mInstance=new OkHttpUtils();
            }
        }
        return mInstance;
    }
    private OkHttpUtils(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient=new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    //异步post方法
    public void postEnqueue(String url, Map<String,String> parms, final Class clax, final IcallBack back){
        FormBody.Builder builder=new FormBody.Builder();
        for(Map.Entry<String,String> entry:parms.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        back.onfail(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, clax);
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        back.onSuccess(o);
                    }
                });
            }
        });
    }
}
