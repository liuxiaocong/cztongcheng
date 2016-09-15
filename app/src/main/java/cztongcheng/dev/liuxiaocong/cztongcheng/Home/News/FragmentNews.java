package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Base.FragmentBase;
import cztongcheng.dev.liuxiaocong.cztongcheng.Config.ConfigBean;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

/**
 * Created by LiuXiaocong on 8/23/2016.
 */
public class FragmentNews extends FragmentBase implements NewContact.View {
    private View mRoot;
    @BindView(R.id.news_list)
    RecyclerView mRecyclerView;
    //    @BindView(R.id.loading)
//    ProgressBar mProgressBar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    NewContact.Presenter mPresenter;
    NewTitleAdapter mNewTitleAdapter;

    public ConfigBean.ItemListBean mItemListBean;

    public void setItemListBean(ConfigBean.ItemListBean itemListBean) {
        mItemListBean = itemListBean;
    }

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
        mRoot = inflater.inflate(R.layout.fg_news, container, false);
        ButterKnife.bind(this, mRoot);
        setPresenter(new NewPresentImpl(getActivity()));
        mPresenter.setView(this);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mNewTitleAdapter != null) return;
//        int color = 0xFF1D8AE7;
//        mProgressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        //mProgressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        //mProgressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNewTitleAdapter = new NewTitleAdapter();
        mRecyclerView.setAdapter(mNewTitleAdapter);

    }

    private void loadData() {
        if (mItemListBean == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        mPresenter.loadNewsData(mItemListBean);
//        switch (mENewsName) {
//            case ECZCommon: {
//                mPresenter.loadCZCommonNewsData();
//            }
//            break;
//            case EJianshu: {
//                mPresenter.loadJianshuNewsData();
//            }
//            break;
//            case EMinSheng: {
//                mPresenter.loadCZMinShengNewsData();
//            }
//            break;
//            case EJieyang: {
//                mPresenter.loadJieyangNewsData();
//            }
//            break;
//            case EShantou: {
//                mPresenter.loadShantouNewsData();
//            }
//            break;
//        }
    }

    @Override
    public void setPresenter(NewContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityDestroy() {
        if (mPresenter != null) mPresenter.destroy();
        if (mNewTitleAdapter != null) mNewTitleAdapter.clear();
    }

    @Override
    public void addNewsTitleData(List<TitleModel> titleModels) {
        //mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        if (mNewTitleAdapter != null) {
            mNewTitleAdapter.setData(titleModels);
        }
    }

    @Override
    public ConfigBean.ItemListBean getItemListBean() {
        return mItemListBean;
    }

    @Override
    public void finishRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
