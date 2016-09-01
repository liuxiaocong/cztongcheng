package cztongcheng.dev.liuxiaocong.cztongcheng.Even;

import cztongcheng.dev.liuxiaocong.cztongcheng.Home.Weather.WeatherBean;

/**
 * Created by LiuXiaocong on 8/24/2016.
 */
public class WeatherDataEvent {
    public WeatherBean weatherBean;

    public WeatherDataEvent(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
    }
}
