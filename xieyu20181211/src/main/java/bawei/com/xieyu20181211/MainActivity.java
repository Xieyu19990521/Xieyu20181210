package bawei.com.xieyu20181211;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.HashMap;
import java.util.Map;

import bawei.com.xieyu20181211.model.BannerBean;
import bawei.com.xieyu20181211.presenter.IpresenterImpl;
import bawei.com.xieyu20181211.view.Iview;

public class MainActivity extends AppCompatActivity implements Iview {

    private IpresenterImpl ipresenter;
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipresenter=new IpresenterImpl(this);
        banner=findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                BannerBean.DataBean dataBean= (BannerBean.DataBean) path;
                String replace = dataBean.getIcon().replace("https", "http");
                ImageLoader.getInstance().displayImage(replace,imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                return imageView;
            }
        });
        Map<String,String> map=new HashMap<>();
        map.put("type","0");
        ipresenter.onStartRequest("https://www.zhaoapi.cn/ad/getAd",map,BannerBean.class);
    }

    @Override
    public void setData(Object data) {
        BannerBean dataBean= (BannerBean) data;
        banner.setImages(dataBean.getData());
        banner.start();
    }

    @Override
    public void remData(Exception e) {
        Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
    }

}
