package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Base.FragmentBase;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

/**
 * Created by LiuXiaocong on 8/23/2016.
 */
public class FragmentNews extends FragmentBase implements NewContact.View {
    private View mRoot;
    @BindView(R.id.news_list)
    RecyclerView mRecyclerView;
    NewContact.Presenter mPresenter;
    NewTitleAdapter mNewTitleAdapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNewTitleAdapter = new NewTitleAdapter();
        mRecyclerView.setAdapter(mNewTitleAdapter);
        mPresenter.loadNewsData();
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
        if (mNewTitleAdapter != null) {
            mNewTitleAdapter.addData(titleModels);
        }
    }
}
