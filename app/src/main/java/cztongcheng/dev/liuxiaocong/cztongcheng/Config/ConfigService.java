package cztongcheng.dev.liuxiaocong.cztongcheng.Config;

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Configs;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiuXiaocong on 8/12/2016.
 */
public interface ConfigService {
    @GET(Configs.remoteConfigUrl)
    Observable<ResponseBody> getConfig();
}
