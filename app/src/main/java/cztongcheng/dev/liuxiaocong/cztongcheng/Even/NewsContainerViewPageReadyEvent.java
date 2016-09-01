package cztongcheng.dev.liuxiaocong.cztongcheng.Even;

import android.support.v4.view.ViewPager;

/**
 * Created by LiuXiaocong on 9/1/2016.
 */
public class NewsContainerViewPageReadyEvent {
    public ViewPager viewPager;

    public NewsContainerViewPageReadyEvent(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
