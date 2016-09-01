package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Base.BasePresenter;
import cztongcheng.dev.liuxiaocong.cztongcheng.Base.BaseView;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public interface NewContact {
    interface View extends BaseView<Presenter> {
        void addNewsTitleData(List<TitleModel> titleModels);
    }

    interface Presenter extends BasePresenter<View> {
        void loadNewsData();
    }
}