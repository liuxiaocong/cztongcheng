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
    public void loadCZCommonNewsData() {
        SourceModel mSourceModel = new SourceModel("http://3g.czbtv.com/wapczxw/",
                "#czxw a",
                "",
                "",
                "http://3g.czbtv.com/wapczxw/",
                "#czxw");
        mNewsSourceModelList.add(mSourceModel);
        mNewsSource.crawlerNews(mSourceModel, ENewsType.ECZCommon);

    }

    @Override
    public void loadCZMinShengNewsData() {
        SourceModel mSourceModel2 = new SourceModel("http://www.chaozhoudaily.com/index.php/news/cate/pcid/1/cid/14.html",
                "#main .main_content tr",
                "td:eq(1)",
                "td:eq(1) a",
                "http://www.chaozhoudaily.com/",
                "#main .news_content");
        mNewsSourceModelList.add(mSourceModel2);
        mNewsSource.crawlerNews(mSourceModel2, ENewsType.EMinSheng);
    }

    @Override
    public void loadJianshuNewsData() {
        SourceModel mSourceModel = new SourceModel("http://www.jianshu.com/collection/9c97d62b7f6a",
                "#list-container .have-img",
                ".title",
                ".title a",
                "http://www.jianshu.com",
                ".show-content");
        mNewsSourceModelList.add(mSourceModel);
        mNewsSource.crawlerNews(mSourceModel, ENewsType.EJianshu);
    }

    @Override
    public void loadJieyangNewsData() {
        SourceModel mSourceModel = new SourceModel("http://www.jynews.net/Category_182/index.aspx",
                "#right_caitou_v8 table table table table tr a",
                "",
                "",
                "http://www.jynews.net/",
                "span.STYLE666");
        mNewsSourceModelList.add(mSourceModel);
        mNewsSource.crawlerNews(mSourceModel, ENewsType.EJieyang);
    }

    @Override
    public void loadShantouNewsData() {
        SourceModel mSourceModel = new SourceModel("http://dahuawang.com/gundong/showdetail1.asp?CNo=1101",
                ".newsList li a",
                "",
                "",
                "http://dahuawang.com/",
                "#Content .content");
        mNewsSourceModelList.add(mSourceModel);
        mNewsSource.crawlerNews(mSourceModel, ENewsType.EShantou);
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
            if (mView != null && mView.getNewsType().equals(getTitleEvent.eNewsType)) {
                mView.addNewsTitleData(getTitleEvent.titleModels);
            }
        }
    }
}
