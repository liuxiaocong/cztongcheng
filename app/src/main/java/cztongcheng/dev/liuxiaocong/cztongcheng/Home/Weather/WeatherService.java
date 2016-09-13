package cztongcheng.dev.liuxiaocong.cztongcheng.Home.Weather;

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Configs;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by LiuXiaocong on 8/12/2016.
 */
public interface WeatherService {
    @GET(Configs.remoteWeatherUrl)
    Observable<ResponseBody> getWeather(@Query("cityid") String cid);
}
