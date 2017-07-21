package com.zenchn.electrombile.widget.PickerView.adapter;

/**
 * Created by wangr on 2017/3/10 0010.
 */
public class AreaAdapter implements WheelAdapter<String> {

    private String[] mData;

    public AreaAdapter(String[] data) {
        this.mData = data;
    }

    @Override
    public int getItemsCount() {
        return mData != null ? mData.length : 0;
    }

    @Override
    public String getItem(int index) {
        return mData[index];
    }

    @Override
    public int indexOf(String o) {
        return indexOf(mData, o);
    }

    private int indexOf(String[] src, String o) {
        int defaultIndex = -1;
        if (null == src)
            return defaultIndex;
        else
            defaultIndex = 0;
        if (null == o)
            return defaultIndex;
        int length = src.length;
        for (int i = 0; i < length; i++) {
            if (o.equals(src[i]))
                return i;
        }
        return defaultIndex;
    }
}
