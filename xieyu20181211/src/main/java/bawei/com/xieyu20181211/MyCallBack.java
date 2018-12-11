package bawei.com.xieyu20181211;

public interface MyCallBack<T> {
    void onFail(Exception e);
    void onSuccess(T data);
}
