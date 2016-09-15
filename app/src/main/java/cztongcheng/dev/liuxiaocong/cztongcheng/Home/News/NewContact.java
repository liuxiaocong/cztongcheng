package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Base.BasePresenter;
import cztongcheng.dev.liuxiaocong.cztongcheng.Base.BaseView;
import cztongcheng.dev.liuxiaocong.cztongcheng.Config.ConfigBean;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public interface NewContact {
    interface View extends BaseView<Presenter> {
        void addNewsTitleData(List<TitleModel> titleModels);
        ConfigBean.ItemListBean getItemListBean();
        void finishRefreshing();
    }

    interface Presenter extends BasePresenter<View> {
        void loadNewsData(ConfigBean.ItemListBean itemListBean);
    }
}
