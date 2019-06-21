package com.example.myvideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //两秒后执行
                startMainActivity();
            }
        }, 2000);
    }
    //掉转的首页
    private void startMainActivity(){
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(null);
        super.onDestroy();
    }
}
