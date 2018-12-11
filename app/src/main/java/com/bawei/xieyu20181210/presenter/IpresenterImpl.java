package com.bawei.xieyu20181210.presenter;

import com.bawei.xieyu20181210.callback.MyCallback;
import com.bawei.xieyu20181210.model.Imodelmpl;
import com.bawei.xieyu20181210.view.IView;

public class IpresenterImpl implements Ipresenter{

    Imodelmpl imodelmpl;
    IView iView;

    public IpresenterImpl(IView iView) {
        this.iView=iView;
        imodelmpl=new Imodelmpl();
    }

    @Override
    public void onRequestData(String string, Class clax) {
        imodelmpl.onStartRequestData(string, clax, new MyCallback() {
            @Override
            public void onSuccess(Object data) {
                iView.onsetData(data);
            }
        });
    }
}
