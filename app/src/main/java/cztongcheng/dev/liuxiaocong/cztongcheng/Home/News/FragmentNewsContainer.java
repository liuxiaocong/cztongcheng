package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Base.FragmentBase;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.NewsContainerViewPageReadyEvent;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

/**
 * Created by LiuXiaocong on 9/1/2016.
 */
public class FragmentNewsContainer extends FragmentBase {
    private View mRoot;
    @BindView(R.id.viewpager)
    public ViewPager mViewpager;
    private NewsViewPageAdapter mNewsViewPageAdapter;
    private FragmentNews mJYNewCommonFragment;
    private FragmentNews mCZNewCommonFragment;
    private FragmentNews mCZMinShengNewFragment;
    private FragmentNews mJSNewFragment;
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
        mJYNewCommonFragment = new FragmentNews();
        mJYNewCommonFragment.setENewsType(ENewsType.EJieyang);
        fragmentNewses.add(mJYNewCommonFragment);
        titles.add("揭阳");

        mCZNewCommonFragment = new FragmentNews();
        mCZNewCommonFragment.setENewsType(ENewsType.ECZCommon);
        fragmentNewses.add(mCZNewCommonFragment);
        titles.add("潮州");

        mCZMinShengNewFragment = new FragmentNews();
        mCZMinShengNewFragment.setENewsType(ENewsType.EMinSheng);
        fragmentNewses.add(mCZMinShengNewFragment);
        titles.add("民生");

        mJSNewFragment = new FragmentNews();
        mJSNewFragment.setENewsType(ENewsType.EJianshu);
        fragmentNewses.add(mJSNewFragment);
        titles.add("简书");

        newsViewPageAdapter.setFragment(fragmentNewses, titles);
        return newsViewPageAdapter;
    }

    @Override
    public void onActivityDestroy() {

    }
}
