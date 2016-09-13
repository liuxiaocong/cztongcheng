package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Config.ConfigBean;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.GetTitleEvent;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public class NewPresentImpl implements NewContact.Presenter {
    private Context mContext;
    NewsSource mNewsSource;
    private NewContact.View mView;

    public NewPresentImpl(Context context) {
        mContext = context;
        mNewsSource = new NewsSource();
        EventBus.getDefault().register(this);
    }

    @Override
    public void loadNewsData(ConfigBean.ItemListBean itemListBean) {
        List<ConfigBean.ItemListBean.SourceListBean> sourceList = itemListBean.getSourceList();
        if (sourceList != null && sourceList.size() > 0) {
            for (ConfigBean.ItemListBean.SourceListBean sourceListBean : sourceList) {
                SourceModel mSourceModel = new SourceModel(sourceListBean.getUrl(),
                        sourceListBean.getTitleListFilter(),
                        sourceListBean.getTitleSubFilter(),
                        sourceListBean.getTargetUrlFromTileFilter(),
                        sourceListBean.getTargetUrlDomain(),
                        sourceListBean.getTargetContentFilter());
                mNewsSource.crawlerNews(mSourceModel, itemListBean.getName());
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onUIReady() {

    }

    @Override
    public void setView(NewContact.View view) {
        mView = view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GetTitleEvent getTitleEvent) {
        if (getTitleEvent != null && getTitleEvent.titleModels != null && getTitleEvent.titleModels.size() > 0) {
            if (mView != null && mView.getItemListBean().getName().equals(getTitleEvent.itemName)) {
                mView.addNewsTitleData(getTitleEvent.titleModels);
            }
        }
    }
}
