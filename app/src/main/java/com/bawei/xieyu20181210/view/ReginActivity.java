package com.bawei.xieyu20181210.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.xieyu20181210.R;
import com.bawei.xieyu20181210.model.LoginBean;
import com.bawei.xieyu20181210.presenter.IpresenterImpl;
import com.bawei.xieyu20181210.util.NetUtil;

public class ReginActivity extends AppCompatActivity implements IView {

    private EditText name,pwd;
    private Button login;
    private IpresenterImpl ipresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regin);
        initView();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwds = pwd.getText().toString();
                String names = name.getText().toString();
                if(!NetUtil.isMobile(names)){
                    Toast.makeText(ReginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else if(pwds.equals("")){
                    Toast.makeText(ReginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    ipresenter.onRequestData("http://www.zhaoapi.cn/user/reg?mobile="+names+"&password="+pwds,LoginBean.class);
                }
            }
        });
    }

    private void initView() {
        name=findViewById(R.id.name);
        pwd=findViewById(R.id.pwd);
        login=findViewById(R.id.login);
        ipresenter=new IpresenterImpl(this);
    }

    @Override
    public void onsetData(Object data) {
        LoginBean loginBean= (LoginBean) data;
        if(loginBean.getCode()==0){
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}
