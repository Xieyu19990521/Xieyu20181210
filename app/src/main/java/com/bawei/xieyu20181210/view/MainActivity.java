package com.bawei.xieyu20181210.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.xieyu20181210.R;
import com.bawei.xieyu20181210.model.LoginBean;
import com.bawei.xieyu20181210.presenter.IpresenterImpl;
import com.bawei.xieyu20181210.util.NetUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private EditText name,pwd;
    private Button login;
    private ImageView imageView;
    private TextView regin,remember;
    private IpresenterImpl ipresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString();
                String pwds = pwd.getText().toString();
                if(!NetUtil.isMobile(names)){
                    Toast.makeText(MainActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }else if(pwds.equals("") ){
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    ipresenter.onRequestData("http://www.zhaoapi.cn/user/login?mobile="+names+"&password="+pwds,LoginBean.class);
                }
            }
        });

        regin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ReginActivity.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareConfig config=new UMShareConfig();
                config.isNeedAuthOnGetUserInfo(true);
                UMShareAPI umShareAPI=UMShareAPI.get(MainActivity.this);
                umShareAPI.setShareConfig(config);
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        String screen_name = map.get("screen_name");
                        String profile_image_url = map.get("profile_image_url");
                        Toast.makeText(MainActivity.this, map.toString(), Toast.LENGTH_SHORT).show();
                        if(!map.toString().equals("")){
                            Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    private void initView() {
        name=findViewById(R.id.name);
        pwd=findViewById(R.id.pwd);
        login=findViewById(R.id.login);
        regin=findViewById(R.id.regin);
        imageView=findViewById(R.id.image_qq);
        ipresenter=new IpresenterImpl(this);
    }

    @Override
    public void onsetData(Object data) {
        LoginBean loginBean= (LoginBean) data;
        if(loginBean.getCode()==0){
            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,ShowActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
