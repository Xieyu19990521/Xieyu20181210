package bawei.com.xieyu20181211.model;

import java.util.Map;

import bawei.com.xieyu20181211.MyCallBack;

public interface Imodel {
    void startRequest(String string, Map<String,String> map, Class clas, MyCallBack myCallBack);
}
