package cztongcheng.dev.liuxiaocong.cztongcheng.Even;

import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.ENewsType;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.SourceModel;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.TitleModel;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public class GetTitleEvent {
    public List<TitleModel> titleModels;
    public SourceModel sourceModel;
    public String itemName;

    public GetTitleEvent(List<TitleModel> titleModels, SourceModel sourceModel, String itemName) {
        this.titleModels = titleModels;
        this.sourceModel = sourceModel;
        this.itemName = itemName;
    }
}
