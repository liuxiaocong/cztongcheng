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

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Util;
import cztongcheng.dev.liuxiaocong.cztongcheng.Config.ConfigManage;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.ConfigDataEvent;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.WeatherDataEvent;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.Weather.WeatherManage;

/**
 * Created by LiuXiaocong on 8/15/2016.
 */
//{"itemList":{"ECZCommon":true,"EJianshu":false,"EMinSheng":true,"EJieyang":true,"EShantou":true},"sourceList":{"ECZCommonSourceArray":[{"url":"http://3g.czbtv.com/wapczxw/","titleListFilter":"#czxw a","titleSubFilter":"","targetUrlFromTileFilter":"","targetUrlDomain":"http://3g.czbtv.com/wapczxw/","targetContentFilter":"#czxw"}],"EMinShengSourceArray":[{"url":"http://www.chaozhoudaily.com/index.php/news/cate/pcid/1/cid/14.html","titleListFilter":"#main .main_content tr","titleSubFilter":"td:eq(1)","targetUrlFromTileFilter":"td:eq(1) a","targetUrlDomain":"http://www.chaozhoudaily.com/","targetContentFilter":"#main .news_content"}],"EJieyangSourceArray":[{"url":"http://www.jynews.net/Category_182/index.aspx","titleListFilter":"#right_caitou_v8 table table table table tr a","titleSubFilter":"","targetUrlFromTileFilter":"","targetUrlDomain":"http://www.jynews.net/","targetContentFilter":"span.STYLE666"}],"EShantouSourceArray":[{"url":"http://dahuawang.com/gundong/showdetail1.asp?CNo=1101","titleListFilter":".newsList li a","titleSubFilter":"","targetUrlFromTileFilter":"","targetUrlDomain":"http://dahuawang.com/","targetContentFilter":"#Content .content"}]}}
//
//


public class MyApplication extends Application {
    private final String TAG = "MyApplication";
    private static MyApplication instance;
    private View mToastView;
    private Toast mToast;
    private boolean mWeatherDataReady = false;
    private boolean mConfigDataReady = false;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        instance = this;
        EventBus.getDefault().register(this);
        Util.DLog(TAG, "onCreate");
        LoadData();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ConfigManage.getInst().clear();
        EventBus.getDefault().unregister(this);
    }

    public boolean isWeatherDataReady() {
        return mWeatherDataReady;
    }

    public boolean isConfigDataReady() {
        return mConfigDataReady;
    }


    private void LoadData() {
        WeatherManage.getInst().loadData(this);
        ConfigManage.getInst().loadData(this);
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
    public void onEvent(WeatherDataEvent event) {
        mWeatherDataReady = true;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventConfigData(ConfigDataEvent event) {
        mConfigDataReady = true;
    }
}
