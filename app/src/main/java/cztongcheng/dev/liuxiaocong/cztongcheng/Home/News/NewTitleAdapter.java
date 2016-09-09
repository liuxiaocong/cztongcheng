package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cztongcheng.dev.liuxiaocong.cztongcheng.Common.WebViewActivity;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

/**
 * Created by LiuXiaocong on 8/18/2016.
 */
public class NewTitleAdapter extends RecyclerView.Adapter<NewTitleAdapter.NewTitleHolder> {

    LayoutInflater mLayoutInflater;
    List<TitleModel> mTitleModelList = new ArrayList<>();
    Context mContext;

    @Override
    public NewTitleAdapter.NewTitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
            mContext = parent.getContext();
        }
        View view = mLayoutInflater.inflate(R.layout.new_item, parent, false);

        return new NewTitleHolder(view);
    }

    public void setData(List<TitleModel> data) {
        mTitleModelList.clear();
        mTitleModelList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<TitleModel> data) {
        mTitleModelList.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        mTitleModelList.clear();
    }

    @Override
    public void onBindViewHolder(NewTitleHolder holder, int position) {
        TitleModel data = mTitleModelList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mTitleModelList.size();
    }

    public class NewTitleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;

        public NewTitleHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        public void bind(TitleModel story) {
            itemView.setTag(story);
            mTitle.setText(story.getTitle());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.root: {
                    TitleModel titleModel = (TitleModel) view.getTag();
                    Toast.makeText(mContext, titleModel.getTitle(), Toast.LENGTH_SHORT).show();
                    WebViewActivity.startWithTitleModel(mContext, titleModel);
                }
                break;
            }
        }
    }
}
