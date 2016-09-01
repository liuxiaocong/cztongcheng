package cztongcheng.dev.liuxiaocong.cztongcheng.Home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Even.NewsContainerViewPageReadyEvent;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.FragmentNewsContainer;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.Weather.FragmentWeather;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

public class HomeActivity extends AppCompatActivity {
    View mRoot;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.container)
    FrameLayout mContainer;

    FragmentManager mFragmentManager;
    FragmentWeather mFragmentWeather;
    FragmentNewsContainer mFragmentNewsContainer;
    private int currentFragmentIndex = 0;
    private Window mWindows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_home);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        mRoot = findViewById(R.id.root);
        mWindows = getWindow();
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_news: {
                        if (currentFragmentIndex != 0) {
                            replaceContent(mFragmentNewsContainer);
                            currentFragmentIndex = 0;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mWindows.setStatusBarColor(Color.TRANSPARENT);
                            mWindows.setStatusBarColor(Color.parseColor("#1B6EB4"));
                        }
                        setAppBarLayoutStatus(true);

                        //Snackbar.make(mRoot, getResources().getString(R.string.news), Snackbar.LENGTH_LONG).show();
                    }
                    break;
                    case R.id.tab_weather: {
                        if (currentFragmentIndex != 1) {
                            replaceContent(mFragmentWeather);
                            currentFragmentIndex = 1;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mWindows.setStatusBarColor(Color.parseColor("#056408"));
                        }
                        mContainer.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setAppBarLayoutStatus(false);
                            }
                        }, 200);
                        //Snackbar.make(mRoot, getResources().getString(R.string.weather), Snackbar.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        });

        bottomBar.setDefaultTabPosition(1);
        mFragmentManager = getFragmentManager();
        mFragmentWeather = new FragmentWeather();
        mFragmentNewsContainer = new FragmentNewsContainer();
        replaceContent(mFragmentWeather);
    }

    private void setAppBarLayoutStatus(boolean isVisable) {
        if (isVisable) {
            mAppBarLayout.setVisibility(View.VISIBLE);
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) mContainer.getLayoutParams();
            params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            mContainer.requestLayout();
        } else {
            mAppBarLayout.setVisibility(View.GONE);
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) mContainer.getLayoutParams();
            params.setBehavior(null);
            mContainer.requestLayout();
        }
    }

    private void replaceContent(Fragment fragment) {
        if (fragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        mFragmentNewsContainer.onActivityDestroy();
        mFragmentWeather.onActivityDestroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewsContainerViewPageReadyEvent newsContainerViewPageReadyEvent) {
        mTabLayout.setupWithViewPager(newsContainerViewPageReadyEvent.viewPager);
    }
}
