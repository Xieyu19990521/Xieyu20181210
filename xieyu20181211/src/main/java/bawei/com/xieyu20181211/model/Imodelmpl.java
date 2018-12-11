package bawei.com.xieyu20181211.model;

import java.util.Map;

import bawei.com.xieyu20181211.MyCallBack;
import bawei.com.xieyu20181211.OkHttpUtils;

public class Imodelmpl implements Imodel {


    @Override
    public void startRequest(String string, Map<String,String> map, Class clas, final MyCallBack myCallBack) {
        OkHttpUtils.getIntance().postEqueue(string, map, clas, new MyCallBack() {

            @Override
            public void onFail(Exception e) {
                myCallBack.onFail(e);
            }

            @Override
            public void onSuccess(Object data) {
                myCallBack.onSuccess(data);
            }
        });
    }
}
