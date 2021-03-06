package cztongcheng.dev.liuxiaocong.cztongcheng.Home.Weather;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Configs;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.GsonImpl;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.SharedPreferencesFactory;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Util;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.WeatherDataEvent;
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
public class WeatherManage {
    static WeatherManage gInst = null;
    WeatherService mGetWeatherService;

    public static synchronized WeatherManage getInst() {
        if (gInst == null) {
            gInst = new WeatherManage();
        }
        return gInst;
    }

    private WeatherManage() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configs.remoteWeatherHost)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        mGetWeatherService = retrofit.create(WeatherService.class);
    }


    public void loadData(final Context context) {
        rx.Observable.create(new rx.Observable.OnSubscribe<WeatherBean>() {
            @Override
            public void call(final Subscriber<? super WeatherBean> subscriber) {
                String localWeatherData = SharedPreferencesFactory.getWeatherData(context, Configs.cityId);
                if (localWeatherData != null && localWeatherData.length() > 0) {
                    WeatherBean bean = GsonImpl.get().toObject(localWeatherData, WeatherBean.class);
                    EventBus.getDefault().post(new WeatherDataEvent(bean));
                }
                mGetWeatherService.getWeather(Configs.cityId)
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
                                    res = res.replace("HeWeather5", "root");
                                    WeatherBean bean = GsonImpl.get().toObject(res, WeatherBean.class);
                                    Log.d("retrofit", res);
                                    SharedPreferencesFactory.setWeatherData(context, Configs.cityId, res);
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
                .subscribe(new Subscriber<WeatherBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new WeatherDataEvent(null));
                    }

                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        EventBus.getDefault().post(new WeatherDataEvent(weatherBean));
                    }
                });

    }
}
