package com.example.santa.sidemenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/12.
 */
public class FourthFragment extends Fragment {
    private volatile View self;
    private RecyclePlayView recyclePlayView;
    private static FourthFragment instance;
    private ArrayList<String> mData;
    private final static int MSG_CREATEVIEW = 1;
    private final static String MSG_INFO = "msg_info";
    private StripBuilder stripBuilder;
    private Thread thread;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CREATEVIEW:
                    Bundle bundle = msg.getData();
                    createStrip(bundle.getCharSequence(MSG_INFO).toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static FourthFragment getInstance() {
        if (instance == null) {
            synchronized (FourthFragment.class) {
                if (instance == null)
                    instance = new FourthFragment();
            }
        }
        return instance;
    }

    public void initData() {
        mData = new ArrayList<>();
        mData.add("JAY,JAY!!");
        mData.add("好听好听,简单爱.");
        mData.add("杰伦,加油.");
        mData.add("我爱你,爱你爱你爱你.");
        mData.add("小周周生日快乐");
        mData.add("杰伦,要减肥啦.");
        mData.add("稻香");
        mData.add("前世情人");
        mData.add("不能说的秘密");
        mData.add("地表最强");
        mData.add("地表最强");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("FourthFragment", "onCreateView is called");

        if (this.self == null) {
            this.self = inflater.inflate(R.layout.fouth_fragment, null);
            initData();
            recyclePlayView = (RecyclePlayView) this.self.findViewById(R.id.recyclePlayView);
            stripBuilder = new StripBuilder();
            Log.d("FourthFragment", "onCreateView is called");

        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }


        return this.self;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stripBuilder != null) {
            stripBuilder.setFinish(false);
            new Thread(stripBuilder).start();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (stripBuilder != null) {
            stripBuilder.setFinish(true);
        }
    }


    private class StripBuilder implements Runnable{
        private boolean isFinish = false;
        @Override
        public void run() {
            while (!isFinish) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = MSG_CREATEVIEW;
                Bundle bundle = new Bundle();
                int i = (int) (Math.random() * mData.size());
                bundle.putCharSequence(MSG_INFO, mData.get(i));
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
        public void setFinish(boolean isFinish) {
            this.isFinish = isFinish;
        }
    }

    private void createStrip(String text) {
        TextStrip textStrip = new TextStrip(getActivity());
        textStrip.setText(text);
        recyclePlayView.addStrip(textStrip);
    }

}