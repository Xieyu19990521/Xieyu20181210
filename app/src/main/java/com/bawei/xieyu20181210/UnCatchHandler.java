package com.bawei.xieyu20181210;

import android.content.Context;
import android.util.Log;

public class UnCatchHandler implements Thread.UncaughtExceptionHandler {

    private static UnCatchHandler unCatchHandler;
    private Context mContext;

    public UnCatchHandler(Context context) {
        init(context);
    }
    public static UnCatchHandler getIntance(Context context){
        if(unCatchHandler==null){
            synchronized (UnCatchHandler.class){
                unCatchHandler=new UnCatchHandler(context);
            }
        }
        return unCatchHandler;
    }

    public void init(Context context){
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext=context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.i("TAG",e.getLocalizedMessage());
    }
}
