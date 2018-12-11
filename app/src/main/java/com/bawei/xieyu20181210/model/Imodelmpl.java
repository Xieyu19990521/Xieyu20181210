package com.bawei.xieyu20181210.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.bawei.xieyu20181210.callback.MyCallback;
import com.bawei.xieyu20181210.okhttp.IcallBack;
import com.bawei.xieyu20181210.okhttp.OkHttpUtils;
import com.bawei.xieyu20181210.util.NetUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Imodelmpl implements Imodel{
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onStartRequestData(final String string, final Class clas, final MyCallback myCallback) {
        Map<String,String> map=new HashMap<>();
        OkHttpUtils.getmInstance().postEnqueue(string, map, clas, new IcallBack() {
            @Override
            public void onSuccess(Object data) {
                myCallback.onSuccess(data);
            }

            @Override
            public void onfail(Object data) {
                myCallback.onSuccess(data);
            }
        });
        /*new AsyncTask<String, Void, Object>() {
            Object o=null;
            @Override
            protected Object doInBackground(String... strings) {
                try {
                    OkHttpUtils.getmInstance().postEnqueue(string, map, clas, new IcallBack() {
                        @Override
                        public void onSuccess(Object data) {
                            o=data;
                        }

                        @Override
                        public void onfail(Object data) {
                            o=data;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return o;
            }

            @Override
            protected void onPostExecute(Object o) {
                myCallback.onSuccess(o);
            }
        }.execute(string);*/
    }
}
