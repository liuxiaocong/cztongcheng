package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Util;
import cztongcheng.dev.liuxiaocong.cztongcheng.Data.DBHelper;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.GetTitleEvent;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
public class NewsSource {
    boolean mIscrawling = false;

    final String TAG = "NewsSource";

    public NewsSource() {
        //EventBus.getDefault().register(this);
    }

    public void clear() {
        //EventBus.getDefault().unregister(this);
    }

    public synchronized void crawlerNews(final SourceModel sourceModel, final String itemName) {
        if (mIscrawling) {
            //finish refreshing
            EventBus.getDefault().post(new GetTitleEvent(null, sourceModel, itemName));
            return;
        }
        mIscrawling = true;
        List<TitleModel> cacheTitleModels = DBHelper.getInstance().getTitleModelListByItemName(itemName);
        if (cacheTitleModels != null && cacheTitleModels.size() > 0) {
            //return cache first
            Util.DLog(TAG, "From cache");
            Util.DLog(TAG, "cacheTitleModels size:" + cacheTitleModels.size());
            EventBus.getDefault().post(new GetTitleEvent(cacheTitleModels, sourceModel, itemName));
        }
        rx.Observable.create(new rx.Observable.OnSubscribe<List<TitleModel>>() {
            @Override
            public void call(Subscriber<? super List<TitleModel>> subscriber) {
                try {
                    Document doc = null;
                    List<TitleModel> titleModelList = new ArrayList<>();
                    try {
                        doc = Jsoup.connect(sourceModel.mUrl).get();
                        Elements newsHeadlines = doc.select(sourceModel.mTitleListFilter);
                        Iterator iterator = newsHeadlines.iterator();
                        if (newsHeadlines.size() > 0) {
                            //delete cache and rebuild cache
                            DBHelper.getInstance().deleteTitleModelByItemName(itemName);
                        }
                        while (iterator.hasNext()) {
                            String title;
                            String content = "";
                            Element element = (Element) iterator.next();
                            if (!Util.isNullOrEmpty(sourceModel.mTitleSubFilter)) {
                                title = element.select(sourceModel.mTitleSubFilter).text();
                            } else {
                                title = element.text();
                            }
                            String childurl;
                            if (!Util.isNullOrEmpty(sourceModel.mTargetUrlFromTileFilter)) {
                                childurl = element.select(sourceModel.mTargetUrlFromTileFilter).attr("href");
                            } else {
                                childurl = element.attr("href");
                            }
                            if (!Util.isNullOrEmpty(sourceModel.mTargetUrlDomain)) {
                                childurl = sourceModel.mTargetUrlDomain + childurl;
                            }

                            if (!Util.isNullOrEmpty(childurl)) {
                                Document childDoc = Jsoup.connect(childurl).get();
                                if (childDoc != null) {
                                    content = childDoc.select(sourceModel.mTargetContentFilter).html();
                                }
                            }
                            if (!Util.isNullOrEmpty(title) && !Util.isNullOrEmpty(content)) {
                                TitleModel titlemodel = new TitleModel();
                                titlemodel.setItemName(itemName);
                                titlemodel.setTitle(title);
                                titlemodel.setContent(content);
                                DBHelper.getInstance().insertTitleModel(titlemodel);
                                titleModelList.add(titlemodel);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(titleModelList);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                    subscriber.onCompleted();
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<List<TitleModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TitleModel> titleModels) {
                        Util.DLog(TAG, "From network");
                        Util.DLog(TAG, "titleModels size:" + titleModels.size());
                        EventBus.getDefault().post(new GetTitleEvent(titleModels, sourceModel, itemName));
                        mIscrawling = false;
                    }
                });
    }
}
