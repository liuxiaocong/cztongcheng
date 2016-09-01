package cztongcheng.dev.liuxiaocong.cztongcheng.Home.Weather;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Base.FragmentBase;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Configs;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.GsonImpl;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.SharedPreferencesFactory;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.UI.RadiusDrawable;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Util;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiuXiaocong on 8/23/2016.
 */
public class FragmentWeather extends FragmentBase {
    Observer<ResponseBody> mCall;
    @BindView(R.id.air)
    TextView airTextView;
    @BindView(R.id.city)
    TextView cityTextView;
    @BindView(R.id.temp_now)
    TextView tempNowTextView;
    @BindView(R.id.wind_style)
    TextView windStyleTextView;
    @BindView(R.id.wind_power)
    TextView windPowerTextView;
    @BindView(R.id.now_weather_icon)
    SimpleDraweeView mWeatherIconDraweeView;
    @BindView(R.id.now_weather_text)
    TextView mWeatherNowTextView;
    @BindView(R.id.future_weather_list)
    RecyclerView mFutureWeather;
    @BindView(R.id.suggest_one)
    TextView suggestOne;
    @BindView(R.id.suggest_two)
    TextView suggestTwo;
    @BindView(R.id.suggest_three)
    TextView suggestThree;
    @BindView(R.id.weather_status_wrap)
    LinearLayout weatherStatusWrap;
    private WeatherAdapter mWeatherAdapter;
    List<String> mRandomColorList = new ArrayList<>();
    Random random;
    private View mRoot;
    WeatherService mGetWeatherService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) return mRoot;
        mRoot = inflater.inflate(R.layout.fg_weather, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (suggestOne == null) {
            ButterKnife.bind(this, mRoot);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mFutureWeather.setLayoutManager(linearLayoutManager);
            mWeatherAdapter = new WeatherAdapter();
            mFutureWeather.setAdapter(mWeatherAdapter);
            mRandomColorList.add("#00b271");
            mRandomColorList.add("#479ac7");
            mRandomColorList.add("#b45b3e");
            mRandomColorList.add("#66cccc");
            mRandomColorList.add("#336699");
            mRandomColorList.add("#8080c0");
            random = new Random();
            int target = random.nextInt(mRandomColorList.size());
            setFirstSuggestBg(mRandomColorList.get(target));
            mRandomColorList.remove(target);
            target = random.nextInt(mRandomColorList.size());
            setSecondSuggestBg(mRandomColorList.get(target));
            mRandomColorList.remove(target);
            target = random.nextInt(mRandomColorList.size());
            setThirdSuggestBg(mRandomColorList.get(target));
            try {
                RadiusDrawable cd = new RadiusDrawable(Util.getPxFromDp(5),
                        Util.getPxFromDp(5),
                        Util.getPxFromDp(5),
                        Util.getPxFromDp(5),
                        Color.parseColor("#ffffff"));
                weatherStatusWrap.setBackgroundDrawable(cd);
            } catch (Exception e) {

            }

            String localData = SharedPreferencesFactory.getWeatherDay(getActivity(), Configs.cityId);
            if (localData != null && localData.length() > 0) {
                WeatherBean bean = GsonImpl.get().toObject(localData, WeatherBean.class);
                refreshUI(bean);
            }
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.heweather.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        mGetWeatherService = retrofit.create(WeatherService.class);
        load();
    }

    private void load() {
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
                            res = res.replace("HeWeather data service 3.0", "root");
                            WeatherBean bean = GsonImpl.get().toObject(res, WeatherBean.class);
                            Log.d("retrofit", res);
                            SharedPreferencesFactory.setWeatherDay(getActivity(), Configs.cityId, res);
                            refreshUI(bean);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void refreshUI(WeatherBean bean) {
        if (bean == null) return;
        airTextView.setText(bean.getRoot().get(0).getAqi().getCity().getQlty());
        cityTextView.setText(bean.getRoot().get(0).getBasic().getCity());
        tempNowTextView.setText(bean.getRoot().get(0).getNow().getTmp() + "");
        windStyleTextView.setText(bean.getRoot().get(0).getNow().getWind().getDir());
        windPowerTextView.setText(bean.getRoot().get(0).getNow().getWind().getSc());
        Uri uri = Uri.parse("http://files.heweather.com/cond_icon/" + bean.getRoot().get(0).getNow().getCond().getCode() + ".png");
        mWeatherIconDraweeView.setImageURI(uri);
        mWeatherNowTextView.setText(bean.getRoot().get(0).getNow().getCond().getTxt());
        mWeatherAdapter.setData(bean.getRoot().get(0).getDaily_forecast());
        suggestOne.setText(bean.getRoot().get(0).getSuggestion().getComf().getTxt());
        suggestTwo.setText(bean.getRoot().get(0).getSuggestion().getDrsg().getTxt());
        suggestThree.setText(bean.getRoot().get(0).getSuggestion().getFlu().getTxt());
    }

    private void setFirstSuggestBg(String color) {
        try {
            RadiusDrawable cd = new RadiusDrawable(Util.getPxFromDp(5),
                    Util.getPxFromDp(5),
                    0,
                    0,
                    Color.parseColor(color));
            suggestOne.setBackgroundDrawable(cd);
        } catch (Exception e) {
            Log.d("setFirstSuggestBg", e.toString());
        }
    }

    private void setSecondSuggestBg(String color) {
        try {
            RadiusDrawable cd = new RadiusDrawable(0,
                    0,
                    0,
                    0,
                    Color.parseColor(color));
            suggestTwo.setBackgroundDrawable(cd);
        } catch (Exception e) {
            Log.d("setSecondSuggestBg", e.toString());
        }
    }

    private void setThirdSuggestBg(String color) {
        try {
            RadiusDrawable cd = new RadiusDrawable(0,
                    0,
                    Util.getPxFromDp(5),
                    Util.getPxFromDp(5),
                    Color.parseColor(color));
            suggestThree.setBackgroundDrawable(cd);
        } catch (Exception e) {
            Log.d("setThirdSuggestBg", e.toString());
        }
    }

    @Override
    public void onActivityDestroy() {

    }
}
