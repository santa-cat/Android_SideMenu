package com.example.santa.sidemenu;

/**
 * Created by santa on 16/7/11.
 */
public class Indicator  {
    private int mDefLeftOffset;
    private int mMaxLeftOffset;
    private int mLeftOffset;

    public Indicator(int def, int max, int normal) {
        mDefLeftOffset = 0;
        mMaxLeftOffset = max;
        mLeftOffset = normal;
    }

    public int getMaxLeftOffset() {
        return mMaxLeftOffset;
    }

    public int getLeftOffset(){
        return mLeftOffset;
    }

    public int getDefLeftOffset() {
        return mDefLeftOffset;
    }

}
