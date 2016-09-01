package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuXiaocong on 9/1/2016.
 */
public class NewsViewPageAdapter extends FragmentPagerAdapter {
    private final List<FragmentNews> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public NewsViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragment(List<FragmentNews> fragmentNewsList, List<String> titleList) {
        mFragmentList.clear();
        mFragmentTitleList.clear();
        mFragmentList.addAll(fragmentNewsList);
        mFragmentTitleList.addAll(titleList);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
