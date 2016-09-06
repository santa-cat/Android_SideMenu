package com.example.santa.sidemenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/12.
 */
public class ThirdFragment extends Fragment {
    private volatile View self;
    private ListView mListView;
    private static ThirdFragment instance;
    private ArrayList<String> mData;

    public static ThirdFragment getInstance() {
        if (instance == null) {
            synchronized (ThirdFragment.class) {
                if (instance == null)
                    instance = new ThirdFragment();
            }
        }
        return instance;
    }

    public void initData() {
        mData = new ArrayList<>();
        mData.add("床边故事");
        mData.add("前世情人");
        mData.add("晴天");
        mData.add("七里香");
        mData.add("告白气球");
        mData.add("不该");
        mData.add("一路向北");
        mData.add("夜曲");
        mData.add("彩虹");
        mData.add("一点点");
        mData.add("稻香");
        mData.add("最长的电影");
        mData.add("不能说的秘密");
        mData.add("简单爱");
        mData.add("龙卷风");
        mData.add("轨迹");
        mData.add("青花瓷");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        if (this.self == null) {
            this.self = inflater.inflate(R.layout.third_fragment, null);
            mListView = (ListView) this.self.findViewById(R.id.listView);
            ViewStub viewStub= new ViewStub(getActivity());
            mListView.addHeaderView(viewStub);
            mListView.setAdapter(new SongAdapter(getActivity()));
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }


    public class SongAdapter extends BaseAdapter {
        private Context mContext;

        public SongAdapter(Context context) {
            mContext = context;
        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int i) {

            return mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SongHolder holder;
            if (null == view) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_thirdfragment, null);
                holder = new SongHolder();
                holder.songIndex = (TextView) view.findViewById(R.id.index);
                holder.songName = (TextView) view.findViewById(R.id.name);
                view.setTag(holder);
            } else {
                holder = (SongHolder) view.getTag();
            }
            holder.songIndex.setText(i+1+"");
            holder.songName.setText(mData.get(i));


            return view;
        }

        public class SongHolder{
            private TextView songIndex;
            private TextView songName;
        }
    }
}