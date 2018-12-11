package bawei.com.xieyu20181211.presenter;

import java.util.Map;

import bawei.com.xieyu20181211.MyCallBack;
import bawei.com.xieyu20181211.model.Imodelmpl;
import bawei.com.xieyu20181211.view.Iview;

public class IpresenterImpl implements Ipresenter {
    private Iview iview;
    private Imodelmpl imodelmpl;

    public IpresenterImpl(Iview iview) {
        this.iview = iview;
        imodelmpl=new Imodelmpl();
    }

    @Override
    public void onStartRequest(String string, Map<String,String> map, Class clas) {
        imodelmpl.startRequest(string, map, clas, new MyCallBack() {

            @Override
            public void onFail(Exception e) {
                iview.remData(e);
            }

            @Override
            public void onSuccess(Object data) {
                iview.setData(data);
            }
        });
    }
}
