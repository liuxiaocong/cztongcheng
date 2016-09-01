package cztongcheng.dev.liuxiaocong.cztongcheng.Home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.FragmentNews;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.Weather.FragmentWeather;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

public class HomeActivity extends AppCompatActivity {
    View mRoot;
    FragmentManager mFragmentManager;
    FragmentWeather mFragmentWeather;
    FragmentNews mFragmentNews;
    private int currentFragmentIndex = 0;
    private Window mWindows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_home);
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
                            replaceContent(mFragmentNews);
                            currentFragmentIndex = 0;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mWindows.setStatusBarColor(Color.TRANSPARENT);
                            mWindows.setStatusBarColor(Color.parseColor("#1B6EB4"));
                        }
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
                        //Snackbar.make(mRoot, getResources().getString(R.string.weather), Snackbar.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        });

//        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        mBottomBar.setItemsFromMenu(R.menu.menu, new OnMenuTabSelectedListener() {
//            @Override
//            public void onMenuItemSelected(int itemId) {
//                switch (itemId) {
//                    case R.id.news:
//                        if (currentFragmentIndex != 0) {
//                            replaceContent(mFragmentNews);
//                            currentFragmentIndex = 0;
//                        }
//                        Snackbar.make(mRoot, getResources().getString(R.string.news), Snackbar.LENGTH_LONG).show();
//                        break;
//                    case R.id.weather:
//                        if (currentFragmentIndex != 1) {
//                            replaceContent(mFragmentWeather);
//                            currentFragmentIndex = 1;
//                        }
//                        Snackbar.make(mRoot, getResources().getString(R.string.weather), Snackbar.LENGTH_LONG).show();
//                        break;
//                }
//            }
//        });
//        bottomBar.setActiveTabColor("#ff0000");
//        mBottomBar.useDarkTheme(false);
        bottomBar.setDefaultTabPosition(1);
        mFragmentManager = getFragmentManager();
        mFragmentWeather = new FragmentWeather();
        mFragmentNews = new FragmentNews();
        replaceContent(mFragmentWeather);
    }

    private void replaceContent(Fragment fragment) {
        if (fragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        mFragmentNews.onActivityDestroy();
        mFragmentWeather.onActivityDestroy();
        super.onDestroy();
    }
}
