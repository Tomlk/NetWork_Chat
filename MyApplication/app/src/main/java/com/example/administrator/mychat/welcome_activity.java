package com.example.administrator.mychat;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//http://www.cnblogs.com/carlos-vic/p/Carlos_V_Android_14.html


public class welcome_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);
        handler.sendEmptyMessageDelayed(0,2000);

    }
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };
    public void getHome()
    {
        Intent intent=new Intent(welcome_activity.this,Login.class);
        startActivity(intent);
        finish();

    }
}
