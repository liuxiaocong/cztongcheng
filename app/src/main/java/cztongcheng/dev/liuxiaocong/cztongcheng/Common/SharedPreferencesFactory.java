package cztongcheng.dev.liuxiaocong.cztongcheng.Common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LiuXiaocong on 8/12/2016.
 */
public class SharedPreferencesFactory {
    private static final String KEY_WEATHER_DATA = "KEY_WEATHER_DATA_V5";
    private static final String KEY_NEWS_DATA = "KEY_NEWS_DATA_V5";
    private static final String KEY_CONFIG_DATA = "KEY_CONFIG_DATA_V5";

    public static void setNewsDay(Context context, String value) {
        set(context, KEY_NEWS_DATA, value);
    }

    public static String getNewsDay(Context context) {
        return get(context, KEY_NEWS_DATA, null);
    }

    public static void setConfigData(Context context, String value) {
        set(context, KEY_CONFIG_DATA, value);
    }

    public static String getConfigData(Context context) {
        return get(context, KEY_CONFIG_DATA, null);
    }

    public static void setWeatherData(Context context, String cid, String value) {
        set(context, KEY_WEATHER_DATA + cid, value);
    }

    public static String getWeatherData(Context context, String cid) {
        return get(context, KEY_WEATHER_DATA + cid, null);
    }


    private static String get(Context mContext, String _key, String _defaultValue) {
        if (null != mContext) {
            SharedPreferences spf = mContext.getSharedPreferences(_key, Context.MODE_PRIVATE);
            if (null != spf) {
                return spf.getString(_key, _defaultValue);
            }
        }

        return _defaultValue;
    }

    private static void set(Context mContext, String _key, String _value) {
        if (null != mContext) {
            SharedPreferences.Editor dateEditor = mContext.getSharedPreferences(_key, Context.MODE_PRIVATE).edit();
            dateEditor.putString(_key, _value);
            dateEditor.apply();
        }
    }
}
