package cztongcheng.dev.liuxiaocong.cztongcheng;

import android.app.Application;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Configs;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.SharedPreferencesFactory;
import cztongcheng.dev.liuxiaocong.cztongcheng.Data.WeatherManage;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.WeatherDataEvent;

/**
 * Created by LiuXiaocong on 8/15/2016.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private View mToastView;
    private Toast mToast;
    private boolean mSystemReady = false;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        instance = this;
        EventBus.getDefault().register(this);
        LoadData();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    public boolean isSystemReady() {
        return mSystemReady;
    }

    private void LoadData() {
        WeatherManage.getInst().loadData(this);
    }

    public void showNote(String str) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = initToast();
        if (mToast != null && mToastView != null) {
            ((TextView) mToastView.findViewById(R.id.toast_text)).setText(str);
            mToast.show();
        }
    }

    private Toast initToast() {
        Toast toast = null;
        try {
            toast = new Toast(instance);
            LayoutInflater layoutInflater = LayoutInflater.from(instance);
            mToastView = layoutInflater.inflate(R.layout.globe_toast_layout, null);

            toast.setView(mToastView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);

        } catch (Exception e) {

        }
        return toast;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onWeatherDataEvent(WeatherDataEvent event) {
        mSystemReady = true;
    }
}
