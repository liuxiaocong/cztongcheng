package cztongcheng.dev.liuxiaocong.cztongcheng.Even;

import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.TitleModel;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public class GetTitleEvent {
    public List<TitleModel> titleModels;

    public GetTitleEvent(List<TitleModel> titleModels) {
        this.titleModels = titleModels;
    }
}
