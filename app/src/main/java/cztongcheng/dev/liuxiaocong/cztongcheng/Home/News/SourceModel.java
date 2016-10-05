package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.support.annotation.Nullable;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public class SourceModel {
    public String mUrl;
    public String mTitleListFilter;
    @Nullable
    public String mTitleSubFilter;
    public String mTargetUrlFromTileFilter;
    public String mTargetContentFilter;
    //for child page crawl
    public String mTargetUrlDomain;
    //for image display
    public String mTargetDomain;

    public SourceModel(String url, String titleListFilter, String titleSubFilter, String targetUrlFromTileFilter, String targetUrlDomain, String targetContentFilter, String targetDomain) {
        this.mUrl = url;
        this.mTitleListFilter = titleListFilter;
        this.mTitleSubFilter = titleSubFilter;
        this.mTargetUrlFromTileFilter = targetUrlFromTileFilter;
        this.mTargetUrlDomain = targetUrlDomain;
        this.mTargetContentFilter = targetContentFilter;
        this.mTargetDomain = targetDomain;
    }
}
