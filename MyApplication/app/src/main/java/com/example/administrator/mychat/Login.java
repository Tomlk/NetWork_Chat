package com.example.administrator.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class Login extends AppCompatActivity {
    MyCache myCache;
    public static String login_account;
    Button button_login;
    EditText editText_a;
    EditText editText_p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
    }
    private void findView()
    {
     button_login=(Button)findViewById(R.id.btn_login);
        editText_a=(EditText)findViewById(R.id.et_account);
        editText_p=(EditText)findViewById(R.id.et_password);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /// /  Toast.makeText(Login.this,"已点击",Toast.LENGTH_SHORT);
                Login();
            }
        });

    }


    private void Login()
    {
        LoginInfo info = new LoginInfo(editText_a.getText().toString().toLowerCase(),editText_p.getText().toString().toLowerCase()); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        login_account=editText_a.getText().toString().toLowerCase();
                        //Log.i("tag","登录成功");
                      //  myCache.setAccount(editText_a.getText().toString().toLowerCase(),editText_p.getText().toString().toLowerCase());
                        Toast.makeText(Login.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,activity_friend.class));
                        finish();
                    }

                    @Override
                    public void onFailed(int i) {
                       // Log.i("tag","账户名或密码错误");
                        Toast.makeText(Login.this,"账户或者密码错误",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.i("tag","抛出异常");
                     //   Toast.makeText(Login.this,"抛出异常",Toast.LENGTH_SHORT);
                    }
                };

        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
