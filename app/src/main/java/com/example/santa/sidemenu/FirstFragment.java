package com.example.santa.sidemenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by santa on 16/7/12.
 */
public class FirstFragment extends Fragment {
    private volatile View self;
    private String string = "2000年发行首张个人专辑《Jay》。2001年发行的专辑《范特西》奠定其融合中西方音乐的风格[1]  。2002年举行“The One”世界巡回演唱会[2]  。2003年成为美国《时代周刊》的封面人物[3-4]  。2004年获得世界音乐大奖中国区最畅销艺人奖[5]  。2005年凭借动作片《头文字D》获得台湾电影金马奖、香港电影金像奖最佳新人奖[6]  。2006年起连续三年获得世界音乐大奖中国区最畅销艺人奖[7]  。2007年自编自导的文艺片《不能说的秘密》获得台湾电影金马奖年度台湾杰出电影奖[8]  。\n" +
            "2008年凭借歌曲《青花瓷》获得第19届金曲奖最佳作曲人奖[9]  。2009年入选美国CNN评出的“25位亚洲最具影响力的人物”[10]  ；同年凭借专辑《魔杰座》获得第20届金曲奖最佳国语男歌手奖[11]  。2010年入选美国《Fast Company》评出的“全球百大创意人物”[12]  。2011年凭借专辑《跨时代》再度获得金曲奖最佳国语男歌手奖，并且第4次获得金曲奖最佳国语专辑奖[13]  ；同年主演好莱坞电影《青蜂侠》[14]  。2012年登福布斯中国名人榜榜首[15]  。2013年自编自导的爱情片《天台爱情》被选为纽约电影节闭幕片[16]  。2016年发行专辑《周杰伦的床边故事》[17]  。";

    private static FirstFragment instance;

    public static FirstFragment getInstance() {
        if (instance == null) {
            synchronized (FirstFragment.class) {
                if (instance == null)
                    instance = new FirstFragment();
            }
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.self == null) {
            this.self = inflater.inflate(R.layout.first_fragment, null);
            TextView textView = (TextView) this.self.findViewById(R.id.textView);
            if (textView != null) {
                textView.setText(string);
            }
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }
}