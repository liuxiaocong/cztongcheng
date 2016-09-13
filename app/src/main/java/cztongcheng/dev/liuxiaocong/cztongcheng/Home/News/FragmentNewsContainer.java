package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Base.FragmentBase;
import cztongcheng.dev.liuxiaocong.cztongcheng.Common.Util;
import cztongcheng.dev.liuxiaocong.cztongcheng.Config.ConfigBean;
import cztongcheng.dev.liuxiaocong.cztongcheng.Config.ConfigManage;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.NewsContainerViewPageReadyEvent;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

/**
 * Created by LiuXiaocong on 9/1/2016.
 */
public class FragmentNewsContainer extends FragmentBase {
    private String TAG = "FragmentNewsContainer";
    private View mRoot;
    @BindView(R.id.viewpager)
    public ViewPager mViewpager;
    private NewsViewPageAdapter mNewsViewPageAdapter;
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) return mRoot;
        mRoot = inflater.inflate(R.layout.fg_news_container, container, false);
        ButterKnife.bind(this, mRoot);
        return mRoot;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mNewsViewPageAdapter == null) {
            mNewsViewPageAdapter = genNewsViewPageAdapter();
            mViewpager.setOffscreenPageLimit(3);
            mViewpager.setAdapter(mNewsViewPageAdapter);
            EventBus.getDefault().post(new NewsContainerViewPageReadyEvent(mViewpager));
        }
    }

    private NewsViewPageAdapter genNewsViewPageAdapter() {
        if (getActivity() != null) {
            mFragmentManager = getActivity().getFragmentManager();
        }
        NewsViewPageAdapter newsViewPageAdapter = new NewsViewPageAdapter(mFragmentManager);
        ArrayList<FragmentNews> fragmentNewses = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        ConfigBean configBean = ConfigManage.getInst().getConfigData(getActivity());
        List<ConfigBean.ItemListBean> itemListBeen = configBean.getItemList();
        Util.DLog(TAG, "genNewsViewPageAdapter:" + itemListBeen.size());

        if (itemListBeen != null && itemListBeen.size() > 0) {
            for (ConfigBean.ItemListBean itemListBean : itemListBeen) {
                FragmentNews fragmentNews = new FragmentNews();
                fragmentNews.setItemListBean(itemListBean);
                fragmentNewses.add(fragmentNews);
                titles.add(itemListBean.getTitle());
            }
        }
        newsViewPageAdapter.setFragment(fragmentNewses, titles);
        return newsViewPageAdapter;
    }

    @Override
    public void onActivityDestroy() {

    }
}
