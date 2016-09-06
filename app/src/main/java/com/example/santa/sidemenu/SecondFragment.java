package com.example.santa.sidemenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by santa on 16/7/12.
 */
public class SecondFragment extends Fragment {
    private volatile View self;
    private ArrayList<HashMap<String, String>> mData;
    private ListView mListView;
    private String NAME = "name";
    private String TIME = "time";
    private String IMAGE = "image";

    private static SecondFragment instance;

    public static SecondFragment getInstance() {
        if (instance == null) {
            synchronized (SecondFragment.class) {
                if (instance == null)
                    instance = new SecondFragment();
            }
        }
        return instance;
    }
    public void initData() {
        mData = new ArrayList<>();

        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "周杰伦的床边故事");
            map.put(TIME, "2016-06-24");
            map.put(IMAGE, "drawable://" + R.drawable.jay14);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "哎哟不错哦");
            map.put(TIME, "2014-12-26");
            map.put(IMAGE, "drawable://" + R.drawable.jay13);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "十二新作");
            map.put(TIME, "2012-12-28");
            map.put(IMAGE, "drawable://" + R.drawable.jay12);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "惊叹号");
            map.put(TIME, "2011-11-11");
            map.put(IMAGE, "drawable://" + R.drawable.jay11);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "跨时代");
            map.put(TIME, "2010-05-18");
            map.put(IMAGE, "drawable://" + R.drawable.jay10);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "摩杰座");
            map.put(TIME, "2008-10-09");
            map.put(IMAGE, "drawable://" + R.drawable.jay9);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "我很忙");
            map.put(TIME, "2007-11-01");
            map.put(IMAGE, "drawable://" + R.drawable.jay8);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "依然范特西");
            map.put(TIME, "2006-09-05");
            map.put(IMAGE, "drawable://" + R.drawable.jay7);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "十一月的肖邦");
            map.put(TIME, "2005-11-01");
            map.put(IMAGE, "drawable://" + R.drawable.jay6);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "七里香");
            map.put(TIME, "2004-08-03");
            map.put(IMAGE, "drawable://" + R.drawable.jay5);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "叶惠美");
            map.put(TIME, "2003-07-31");
            map.put(IMAGE, "drawable://" + R.drawable.jay4);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "八度空间");
            map.put(TIME, "2002-07-19");
            map.put(IMAGE, "drawable://" + R.drawable.jay3);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "范特西");
            map.put(TIME, "2001-09-14");
            map.put(IMAGE, "drawable://" + R.drawable.jay2);
            mData.add(map);
        }
        {
            HashMap<String ,String> map = new HashMap<>();
            map.put(NAME, "Jay");
            map.put(TIME, "2000-11-13");
            map.put(IMAGE, "drawable://" + R.drawable.jay1);
            mData.add(map);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.self == null) {
            this.self = inflater.inflate(R.layout.second_fragment, null);
            initData();
            //创建默认的ImageLoader配置参数
            ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getActivity());

            //Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(configuration);

            mListView = (ListView) this.self.findViewById(R.id.listView);
            mListView.setDividerHeight(0);
            mListView.setAdapter(new DvdAdapter(getActivity()));
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }


    public class DvdAdapter extends BaseAdapter {
        private Context mContext;

        private DisplayImageOptions options;

        public DvdAdapter(Context context) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_stub)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
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
            DvdHolder holder;
            if (null == view) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_secondfragment, null);
                holder = new DvdHolder();
                holder.songName = (DescribeImage) view.findViewById(R.id.describe_image);
                holder.timeLine = (TimeLineIdxView) view.findViewById(R.id.time_line);
                view.setTag(holder);
            } else {
                holder = (DvdHolder) view.getTag();
            }
            holder.songName.setText(mData.get(i).get(NAME), mData.get(i).get(TIME));
            holder.songName.setmImage(mData.get(i).get(IMAGE), options);
            holder.timeLine.setText(mData.get(i).get(TIME).split("-")[0]);

//            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.jay1, holder.songName.getImage(), options);


            return view;
        }

        public class DvdHolder{
            private TimeLineIdxView timeLine;
            private DescribeImage songName;
        }
    }
}