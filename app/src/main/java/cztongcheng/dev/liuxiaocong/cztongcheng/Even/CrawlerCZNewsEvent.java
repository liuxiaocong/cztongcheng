package cztongcheng.dev.liuxiaocong.cztongcheng.Even;

import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.SourceModel;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public class CrawlerCZNewsEvent {
    public SourceModel sourceModel;
    public String htmlContent;

    public CrawlerCZNewsEvent(SourceModel sourceModel, String htmlContent) {
        this.sourceModel = sourceModel;
        this.htmlContent = htmlContent;
    }
}
