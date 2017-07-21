package com.zenchn.electrombile.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.R;


public class SegmentButton extends LinearLayout implements View.OnClickListener {

    private TextView tvLeft;
    private TextView tvRight;
    private boolean isLeftSelected;

    public boolean isLeftSelected() {
        return isLeftSelected;
    }

    private OnSegmentSelectedListener onSegmentSelectedListener;

    public OnSegmentSelectedListener getOnSegmentSelectedListener() {
        return onSegmentSelectedListener;
    }


    public void setOnSegmentSelectedListener(OnSegmentSelectedListener onSegmentSelectedListener) {
        this.onSegmentSelectedListener = onSegmentSelectedListener;
    }


    public SegmentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.widget_segment_button, this);

        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);


        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SegmentButton);
        String leftButtonText = typedArray.getString(R.styleable.SegmentButton_leftButton);
        String rightButtonText = typedArray.getString(R.styleable.SegmentButton_rightButton);
        typedArray.recycle();

        tvLeft.setText(leftButtonText);
        tvRight.setText(rightButtonText);

        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);

        setSegmentSelected(true);
    }


    /**
     * 设置某一个Segment被选中
     *
     * @param isLeftSelected
     */
    public void setSegmentSelected(boolean isLeftSelected) {
        if (isLeftSelected) {
            //选中左边
            tvLeft.setSelected(true);
            tvRight.setSelected(false);
        } else {
            //选中右边
            tvRight.setSelected(true);
            tvLeft.setSelected(false);
        }
        this.isLeftSelected = isLeftSelected;
        if (onSegmentSelectedListener != null) {
            onSegmentSelectedListener.onSelected(isLeftSelected);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_left) {
            setSegmentSelected(true);
        } else if (v.getId() == R.id.tv_right) {
            setSegmentSelected(false);
        }
    }

    public interface OnSegmentSelectedListener {
        void onSelected(boolean isLeftSelected);
    }

}
