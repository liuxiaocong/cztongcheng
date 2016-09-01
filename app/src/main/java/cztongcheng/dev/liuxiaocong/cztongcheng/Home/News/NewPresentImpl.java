package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Even.GetTitleEvent;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public class NewPresentImpl implements NewContact.Presenter {
    private Context mContext;
    List<SourceModel> mNewsSourceModelList = new ArrayList<>();
    NewsSource mNewsSource;
    private NewContact.View mView;

    public NewPresentImpl(Context context) {
        mContext = context;
        mNewsSource = new NewsSource();
        EventBus.getDefault().register(this);
    }

    @Override
    public void loadNewsData() {
        SourceModel mSourceModel = new SourceModel("http://3g.czbtv.com/wapczxw/",
                "#czxw a",
                "",
                "",
                "http://3g.czbtv.com/wapczxw/",
                "#czxw");
        mNewsSourceModelList.add(mSourceModel);
        mNewsSource.crawlerNews(mSourceModel);
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
        if (getTitleEvent != null && getTitleEvent.titleModels != null) {
            if (mView != null) {
                mView.addNewsTitleData(getTitleEvent.titleModels);
            }
        }
    }
}
