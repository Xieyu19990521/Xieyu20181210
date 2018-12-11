package com.bawei.xieyu20181210.okhttp;

public interface IcallBack<T> {
    void onSuccess(T data);
    void onfail(T data);
}
