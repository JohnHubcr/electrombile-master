package com.zenchn.electrombile.widget.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.widget.AnimationFactory;
import com.zenchn.mlibrary.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangr on 2016/11/5 0005.
 */
public class SelectDialog extends Dialog implements View.OnClickListener, Animation.AnimationListener {

    private static final int CANCEL_BUTTON_ID = -1;
    private static final int TITLE_BUTTON_ID = -2;
    private static final int BG_VIEW_ID = -3;
    private static final int ANIMATION_DURATION = 300;

    private Context mContext;
    private Attributes mAttrs;
    private MenuItemClickListener mListener;
    private View mView;
    private LinearLayout mPanel;
    private View mBg;
    private List<String> items;
    private String cancelTitle;
    private String title;
    private boolean mCancelableOnTouchOutside;
    private boolean mDismissed = true;
    private boolean isCancel = true;


    public SelectDialog(Context context) {
        super(context, android.R.style.Theme_Light_NoTitleBar);// 全屏
        this.mContext = context;
        initViews();
        getWindow().setGravity(Gravity.BOTTOM);
        Drawable drawable = new ColorDrawable();
        drawable.setAlpha(0);// 设置透明背景
        getWindow().setBackgroundDrawable(drawable);
    }

    /**
     * 初始化组件
     */
    private void initViews() {
        /* 隐藏软键盘 */
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            View focusView = ((Activity) mContext).getCurrentFocus();
            if (focusView != null)
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
        mAttrs = readAttribute();// 获取主题属性
        mView = createView();
        mPanel.startAnimation(AnimationFactory.createRiseInAnimation(false, ANIMATION_DURATION));
    }

    /**
     * 创建基本的背景视图
     */
    private View createView() {
        //创建父控件
        FrameLayout parent = new FrameLayout(mContext);
        FrameLayout.LayoutParams parentParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parentParams.gravity = Gravity.BOTTOM;
        parent.setLayoutParams(parentParams);

        //创建背景控件
        mBg = new View(mContext);
        mBg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mBg.setBackgroundColor(Color.argb(136, 0, 0, 0));
        mBg.setTag(BG_VIEW_ID);
        mBg.setOnClickListener(this);

        //创建子控件
        mPanel = new LinearLayout(mContext);
        FrameLayout.LayoutParams mPanelParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mPanelParams.gravity = Gravity.CENTER;
        mPanel.setLayoutParams(mPanelParams);
        mPanel.setOrientation(LinearLayout.VERTICAL);
        mPanel.setGravity(Gravity.CENTER);
        parent.addView(mBg);
        parent.addView(mPanel);
        return parent;
    }

    /**
     * 创建MenuItem
     */
    public void createItems() {

        setTitleAndCancel(TITLE_BUTTON_ID);

        addSeparate();

        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {

                Button bt = new Button(mContext);
                bt.setTag(i);
                bt.setOnClickListener(this);
                Drawable otherButtonBg = getOtherButtonBg(items.toArray(new String[items.size()]), i);
                bt.setBackgroundDrawable(otherButtonBg);
                bt.setText(items.get(i));

                bt.setTextColor(mAttrs.otherButtonTextColor);
                bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttrs.actionSheetTextSize);
                LinearLayout.LayoutParams params = createButtonLayoutParams();
                params.topMargin = mAttrs.otherButtonSpacing;
                mPanel.addView(bt, params);

                addSeparate();
            }
        }

        setTitleAndCancel(CANCEL_BUTTON_ID);
    }

    private View addSeparate() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.include_separate_view, null);
        LinearLayout.LayoutParams params = createButtonLayoutParams();
        mPanel.addView(view, params);
        return view;
    }

    private void setTitleAndCancel(Object tag) {
        Button bt = new Button(mContext);
        bt.setTag(tag);

        if (CANCEL_BUTTON_ID == tag) {
            bt.setTextColor(mAttrs.cancelButtonTextColor);
            bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttrs.actionSheetCancelTextSize);
            if (cancelTitle != null && !TextUtils.isEmpty(cancelTitle)) {
                bt.setText(cancelTitle);
            } else {
                bt.setText("取消");
            }
            bt.setBackgroundDrawable(mAttrs.cancelButtonBackground);
            bt.setPadding(0, mAttrs.cancelPadding, 0, mAttrs.cancelPadding);
        } else {
            bt.setTextColor(mAttrs.titleButtonTextColor);
            bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttrs.actionSheetTitleTextSize);
            if (title != null && !TextUtils.isEmpty(title)) {
                bt.setText(title);
            } else {
                bt.setText("请选择");
            }
            bt.setPadding(0, mAttrs.titlePadding, 0, mAttrs.titlePadding);
            bt.setBackgroundDrawable(mAttrs.titleButtonBackground);
        }
        bt.setOnClickListener(this);
        LinearLayout.LayoutParams params = createButtonLayoutParams();
        mPanel.addView(bt, params);

        mPanel.setBackgroundDrawable(mAttrs.background);
        mPanel.setPadding(mAttrs.padding, mAttrs.padding, mAttrs.padding, mAttrs.padding);
    }

    /**
     * 设置按钮的宽高
     *
     * @return
     */
    public LinearLayout.LayoutParams createButtonLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext) * 5 / 6,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        return params;
    }

    /**
     * item按钮的颜色
     *
     * @param titles
     * @param i
     * @return
     */
    private Drawable getOtherButtonBg(String[] titles, int i) {
        if (titles.length == 1)
            return mAttrs.otherButtonSingleBackground;
        else if (titles.length == 2)
            switch (i) {
                case 0:
                    return mAttrs.otherButtonTopBackground;
                case 1:
                    return mAttrs.otherButtonBottomBackground;
            }
        else if (titles.length > 2) {
            if (i == 0)
                return mAttrs.otherButtonTopBackground;
            else if (i == (titles.length - 1))
                return mAttrs.otherButtonBottomBackground;
            return mAttrs.getOtherButtonMiddleBackground();
        }
        return null;
    }

    public void showMenu() {
        if (!mDismissed)
            return;
        show();
        getWindow().setContentView(mView);
        mDismissed = false;
    }

    /**
     * dissmiss Menu菜单
     */
    public void dismissMenu() {
        if (mDismissed)
            return;
        onDismiss();
        mDismissed = true;
    }

    /**
     * dismiss时的处理
     */
    private void onDismiss() {
        AnimationSet outAnimation = AnimationFactory.createFallOutAnimation(false, ANIMATION_DURATION);
        mPanel.startAnimation(outAnimation);
        outAnimation.setAnimationListener(this);
    }

    /**
     * 设置的标题文字
     *
     * @param title
     * @return
     */
    public SelectDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 取消按钮的标题文字
     *
     * @param title
     * @return
     */
    public SelectDialog setCancelButtonTitle(String title) {
        this.cancelTitle = title;
        return this;
    }

    /**
     * 取消按钮的标题文字
     *
     * @param strId
     * @return
     */
    public SelectDialog setCancelButtonTitle(int strId) {
        return setCancelButtonTitle(mContext.getString(strId));
    }

    /**
     * 点击外部边缘是否可取消
     *
     * @param cancelable
     * @return
     */
    public SelectDialog setCancelableOnTouchMenuOutside(boolean cancelable) {
        mCancelableOnTouchOutside = cancelable;
        return this;
    }

    public SelectDialog addItems(String... titles) {
        if (titles == null || titles.length == 0)
            return this;
        items = Arrays.asList(titles);
        return this;
    }

    public SelectDialog addItem(String title) {
        if (title == null && TextUtils.isEmpty(title))
            return this;
        if (items == null) {
            items = new ArrayList<String>();
        }
        items.add(title);
        return this;
    }

    public SelectDialog addItems(List<String> titles) {
        if (titles == null)
            return this;
        items = titles;
        return this;
    }

    public SelectDialog setItemClickListener(MenuItemClickListener listener) {
        this.mListener = listener;
        return this;
    }

    private Attributes readAttribute() {
        Attributes attrs = new Attributes(mContext);
        TypedArray a = mContext.getTheme().obtainStyledAttributes(null, R.styleable.ActionSheet,
                R.attr.actionSheetStyle, 0);
        Drawable background = a.getDrawable(R.styleable.ActionSheet_actionSheetBackground);
        if (background != null)
            attrs.background = background;
        Drawable titleButtonBackground = a.getDrawable(R.styleable.ActionSheet_titleButtonBackground);
        if (titleButtonBackground != null)
            attrs.titleButtonBackground = titleButtonBackground;
        Drawable cancelButtonBackground = a.getDrawable(R.styleable.ActionSheet_cancelButtonBackground);
        if (cancelButtonBackground != null)
            attrs.cancelButtonBackground = cancelButtonBackground;
        Drawable otherButtonTopBackground = a.getDrawable(R.styleable.ActionSheet_otherButtonTopBackground);
        if (otherButtonTopBackground != null)
            attrs.otherButtonTopBackground = otherButtonTopBackground;
        Drawable otherButtonMiddleBackground = a.getDrawable(R.styleable.ActionSheet_otherButtonMiddleBackground);
        if (otherButtonMiddleBackground != null)
            attrs.otherButtonMiddleBackground = otherButtonMiddleBackground;
        Drawable otherButtonBottomBackground = a.getDrawable(R.styleable.ActionSheet_otherButtonBottomBackground);
        if (otherButtonBottomBackground != null)
            attrs.otherButtonBottomBackground = otherButtonBottomBackground;
        Drawable otherButtonSingleBackground = a.getDrawable(R.styleable.ActionSheet_otherButtonSingleBackground);
        if (otherButtonSingleBackground != null)
            attrs.otherButtonSingleBackground = otherButtonSingleBackground;
        attrs.titleButtonTextColor = a.getColor(R.styleable.ActionSheet_titleButtonTextColor,
                attrs.titleButtonTextColor);
        attrs.cancelButtonTextColor = a.getColor(R.styleable.ActionSheet_cancelButtonTextColor,
                attrs.cancelButtonTextColor);
        attrs.otherButtonTextColor = a.getColor(R.styleable.ActionSheet_otherButtonTextColor,
                attrs.otherButtonTextColor);
        attrs.padding = (int) a.getDimension(R.styleable.ActionSheet_actionSheetPadding, attrs.padding);
        attrs.titlePadding = (int) a.getDimension(R.styleable.ActionSheet_actionTitlePadding, attrs.titlePadding);
        attrs.cancelPadding = (int) a.getDimension(R.styleable.ActionSheet_actionCancelPadding, attrs.cancelPadding);
        attrs.otherButtonSpacing = (int) a.getDimension(R.styleable.ActionSheet_otherButtonSpacing,
                attrs.otherButtonSpacing);
        attrs.cancelButtonMarginTop = (int) a.getDimension(R.styleable.ActionSheet_cancelButtonMarginTop,
                attrs.cancelButtonMarginTop);
        attrs.actionSheetTextSize = a.getDimensionPixelSize(R.styleable.ActionSheet_actionSheetTextSize,
                (int) attrs.actionSheetTextSize);
        attrs.actionSheetCancelTextSize = a.getDimensionPixelSize(R.styleable.ActionSheet_actionSheetCancelTextSize,
                (int) attrs.actionSheetCancelTextSize);
        attrs.actionSheetTitleTextSize = a.getDimensionPixelSize(R.styleable.ActionSheet_actionSheetTitleTextSize,
                (int) attrs.actionSheetTitleTextSize);

        a.recycle();
        return attrs;
    }

    @Override
    public void onClick(View v) {
        int flag = (int) v.getTag();
        if (flag == TITLE_BUTTON_ID) {
            return;
        } else if (flag == CANCEL_BUTTON_ID) {
            dismissMenu();
        } else if (flag > -1) {
            if (mListener != null)
                mListener.onSelectDialogItemClick(flag);
            isCancel = false;
            dismissMenu();
        } else {
            if (mCancelableOnTouchOutside)
                dismissMenu();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        dismiss();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /**
     * 自定义属性的控件主题
     */
    private class Attributes {
        private Context mContext;

        private Drawable background;
        public Drawable titleButtonBackground;
        private Drawable cancelButtonBackground;
        private Drawable otherButtonTopBackground;
        private Drawable otherButtonMiddleBackground;
        private Drawable otherButtonBottomBackground;
        private Drawable otherButtonSingleBackground;
        private int titleButtonTextColor;
        private int cancelButtonTextColor;
        private int otherButtonTextColor;
        private int padding;
        private int titlePadding;
        private int cancelPadding;
        private int otherButtonSpacing;
        private int cancelButtonMarginTop;
        private float actionSheetTextSize;
        private float actionSheetTitleTextSize;
        private float actionSheetCancelTextSize;


        public Attributes(Context context) {
            super();
            mContext = context;
            this.background = new ColorDrawable(Color.TRANSPARENT);
            this.cancelButtonBackground = new ColorDrawable(Color.BLACK);
            ColorDrawable gray = new ColorDrawable(Color.GRAY);
            this.otherButtonTopBackground = gray;
            this.otherButtonMiddleBackground = gray;
            this.otherButtonBottomBackground = gray;
            this.otherButtonSingleBackground = gray;
            this.cancelButtonTextColor = Color.WHITE;
            this.otherButtonTextColor = Color.BLACK;
            this.padding = dp2px(20);
            this.otherButtonSpacing = dp2px(2);
            this.cancelButtonMarginTop = dp2px(10);
            this.actionSheetTextSize = dp2px(16);
        }

        private int dp2px(int dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources()
                    .getDisplayMetrics());
        }

        public Drawable getOtherButtonMiddleBackground() {
            if (otherButtonMiddleBackground instanceof StateListDrawable) {
                TypedArray a = mContext.getTheme().obtainStyledAttributes(null, R.styleable.ActionSheet,
                        R.attr.actionSheetStyle, 0);
                otherButtonMiddleBackground = a.getDrawable(R.styleable.ActionSheet_otherButtonMiddleBackground);
                a.recycle();
            }
            return otherButtonMiddleBackground;
        }
    }

    public interface MenuItemClickListener {

        void onSelectDialogItemClick(int itemPosition);
    }

}
