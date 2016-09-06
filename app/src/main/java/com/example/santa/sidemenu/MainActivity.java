package com.example.santa.sidemenu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mData;
    public static final String[] titles = {"简介","专辑","歌曲","动态"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPager();





        initData();
        SideMenuLayout sideMenuLayout = (SideMenuLayout) findViewById(R.id.SideMenuLayout);
        sideMenuLayout.setAdapter(new MenuAdapter(this));


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(false)
                .build();
//
//        //创建默认的ImageLoader配置参数
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
//
//        //Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(configuration);
//
//
//        ImageView imageView = (ImageView) findViewById(R.id.test);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.catface, imageView, options);

//        TextView tv = (TextView) findViewById(R.id.textview);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    private void initPager() {
        PagerTag pagerTag = (PagerTag) findViewById(R.id.pagerTag);
        ViewPager viewPage = (ViewPager) this.findViewById(R.id.viewPager);
        List<Fragment> listFragment = new LinkedList<Fragment>();
        FirstFragment first = FirstFragment.getInstance();
        SecondFragment second = SecondFragment.getInstance();
        ThirdFragment third =  ThirdFragment.getInstance();
        FourthFragment fourth = FourthFragment.getInstance();
        listFragment.add(first);
        listFragment.add(second);
        listFragment.add(third);
        listFragment.add(fourth);

        viewPage.setAdapter(new TabsFragmentAdapter(this.getSupportFragmentManager(), titles, listFragment));
        pagerTag.setViewPager(viewPage);
    }

    public void initData() {
        mData = new ArrayList<>();
        mData.add("");
        mData.add("");
        mData.add("发现");
        mData.add("我的");
        mData.add("朋友");
        mData.add("设置");
    }

    public class MenuAdapter extends BaseAdapter{
        private Context mContext;

        public MenuAdapter(Context context) {
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
            MenuHolder menuHolder;
            if (null == view) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
                menuHolder = new MenuHolder();
                menuHolder.textView = (TextView) view.findViewById(R.id.text_view);
                view.setTag(menuHolder);
            } else {
                menuHolder = (MenuHolder) view.getTag();
            }
            menuHolder.textView.setText(mData.get(i));
            return view;
        }

        public class MenuHolder{
            private TextView textView;
        }
    }

    public class TabsFragmentAdapter extends FragmentPagerAdapter {
        private String[] titles;
        private List<Fragment> fragments;

        public TabsFragmentAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        /**
         * This method may be called by the ViewPager to obtain a title string
         * to describe the specified page. This method may return null
         * indicating no title for this page. The default implementation returns
         * null.
         *
         * @param position The position of the title requested
         * @return A title for the requested page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            if (position < titles.length) {
                return titles[position];
            } else {
                return "";
            }
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position position
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = this.fragments.get(position);
            if (fragment != null) {
                return this.fragments.get(position);
            } else {
                return null;
            }
        }


        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return this.fragments.size();
        }

    }
}
