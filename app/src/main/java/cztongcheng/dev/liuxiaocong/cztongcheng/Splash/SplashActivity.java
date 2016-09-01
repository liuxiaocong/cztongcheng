package cztongcheng.dev.liuxiaocong.cztongcheng.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cztongcheng.dev.liuxiaocong.cztongcheng.Even.WeatherDataEvent;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.HomeActivity;
import cztongcheng.dev.liuxiaocong.cztongcheng.MyApplication;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;


/**
 * Created by LiuXiaocong on 8/16/2016.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SplashActivity123", "onCreate");
        setContentView(R.layout.at_splash);
        if (MyApplication.getInstance().isSystemReady()) {
            gotoNext(HomeActivity.class);
        } else {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void gotoNext(Class<?> cls) {
        Intent ci = new Intent(this, cls);
        ci.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(ci);
        this.finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherDataEvent(WeatherDataEvent event) {
        gotoNext(HomeActivity.class);
    }
}
