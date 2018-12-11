package bawei.com.xieyu20181211;

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
    private static volatile OkHttpUtils okHttpUtils;
    private OkHttpClient mClient;
    private Handler handler=new Handler(Looper.getMainLooper());

    public static OkHttpUtils getIntance(){
        if(okHttpUtils==null){
            synchronized (OkHttpUtils.class){
                if(okHttpUtils==null){
                    okHttpUtils=new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    private OkHttpUtils() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();

    }

    public void postEqueue(String string, Map<String,String> map, final Class clas, final MyCallBack myCallBack){
        FormBody.Builder builder=new FormBody.Builder();
        for(Map.Entry<String,String> map1:map.entrySet()){
            builder.add(map1.getKey(),map1.getValue());
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .post(body)
                .url(string)
                .build();

        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       myCallBack.onFail(e);
                   }
               });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string1 = response.body().toString();
                Gson gson = new Gson();
                Object o = gson.fromJson(string1, clas);
                myCallBack.onSuccess(o);
            }
        });
    }
}
