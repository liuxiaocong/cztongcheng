package cztongcheng.dev.liuxiaocong.cztongcheng.Config;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Configs;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.GsonImpl;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.SharedPreferencesFactory;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Util;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.ConfigDataEvent;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiuXiaocong on 8/24/2016.
 */
public class ConfigManage {
    String TAG = "ConfigManage";
    static ConfigManage gInst = null;
    ConfigService mConfigService;
    //change under event
    ConfigBean mConfigBean;

    public static synchronized ConfigManage getInst() {
        if (gInst == null) {
            gInst = new ConfigManage();
        }
        return gInst;
    }

    private ConfigManage() {
        EventBus.getDefault().register(this);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configs.remoteConfigHost)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        mConfigService = retrofit.create(ConfigService.class);
    }

    public void clear() {
        EventBus.getDefault().unregister(this);
    }

    public ConfigBean getConfigData(Context context) {
        if (mConfigBean != null) {
            return mConfigBean;
        } else {
            String localConfigData = SharedPreferencesFactory.getConfigData(context);
            if (localConfigData == null || localConfigData.length() <= 0) {
                localConfigData = loadLocalConfig(context);
            }
            mConfigBean = GsonImpl.get().toObject(localConfigData, ConfigBean.class);
            return mConfigBean;
        }
    }

    private String loadLocalConfig(Context context) {
        InputStream is = getClass().getClassLoader().getResourceAsStream("assets/" + "defaulltConfig.json");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line);
            }
            reader.close();
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Util.DLog(TAG, stringBuilder.toString());
        return stringBuilder.toString();
    }


    public void loadData(final Context context) {
        rx.Observable.create(new rx.Observable.OnSubscribe<ConfigBean>() {
            @Override
            public void call(final Subscriber<? super ConfigBean> subscriber) {
                String localConfigData = SharedPreferencesFactory.getConfigData(context);
                if (localConfigData != null && localConfigData.length() > 0) {
                    ConfigBean bean = GsonImpl.get().toObject(localConfigData, ConfigBean.class);
                    EventBus.getDefault().post(new ConfigDataEvent(bean));
                }
                mConfigService.getConfig()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.d("onCompleted", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("Error", e.toString());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String res = responseBody.string();
                                    ConfigBean bean = GsonImpl.get().toObject(res, ConfigBean.class);
                                    Log.d("retrofit", res);
                                    Util.DLog(TAG, "Local Source item count:" + bean.getItemList().size());
                                    SharedPreferencesFactory.setConfigData(context, res);
                                    subscriber.onNext(bean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    subscriber.onNext(null);
                                }
                            }
                        });

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ConfigBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ConfigDataEvent(null));
                    }

                    @Override
                    public void onNext(ConfigBean configBean) {
                        EventBus.getDefault().post(new ConfigDataEvent(configBean));
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventConfigData(ConfigDataEvent event) {
        mConfigBean = event.configBean;
    }
}
