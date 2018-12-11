package com.bawei.xieyu20181210.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.xieyu20181210.R;
import com.bawei.xieyu20181210.model.HomeBean;
import com.bawei.xieyu20181210.presenter.IpresenterImpl;
import com.bawei.xieyu20181210.view.IView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

public class ShowActivity extends AppCompatActivity implements IView {

    private IpresenterImpl ipresenter;
    private Banner banner;
    private Button scan;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ipresenter=new IpresenterImpl(this);
        banner=findViewById(R.id.banner);
        scan=findViewById(R.id.scan);
        textView=findViewById(R.id.text);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                HomeBean.DataBean.BannerBean homeBean= (HomeBean.DataBean.BannerBean) path;
                String replace = homeBean.getIcon().replace("https", "http");
                ImageLoader.getInstance().displayImage(replace,imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        ipresenter.onRequestData("http://www.zhaoapi.cn/home/getHome",HomeBean.class);
    }

    @Override
    public void onsetData(Object data) {
        HomeBean bannerBean= (HomeBean) data;
        banner.setImages(bannerBean.getData().getBanner());
        Log.i("TAG",bannerBean.getData().getBanner().toString());
        banner.start();
    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                //if(ContextCompat.checkSelfPermission(this,new String[]{Manifest.permission.CAMERA},100){

                //}
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);
            }
        }else{
            startActivity(new Intent(ShowActivity.this,ScanActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(ShowActivity.this,ScanActivity.class));
        }
    }
}
