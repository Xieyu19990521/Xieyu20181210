package com.bawei.xieyu20181210.model;

import com.bawei.xieyu20181210.callback.MyCallback;

public interface Imodel {
    void onStartRequestData(String string, Class clas, MyCallback myCallback);
}
