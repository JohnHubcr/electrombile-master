package com.zenchn.electrombile.widget;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by wangr on 2016/11/5 0005.
 */
public class AnimationFactory {

    /**
     * 创建一个启动界面放大的动画
     *
     * @param shareInterpolator
     * @param durationMillis
     * @return
     */
    public static AnimationSet createWelcomeScaleAnimation(boolean shareInterpolator, long durationMillis) {

        ScaleAnimation sa = new ScaleAnimation(
                1f,
                1.1f,
                1f,
                1.1f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        sa.setDuration(durationMillis);
        sa.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(shareInterpolator);
        animationSet.addAnimation(sa);

        return animationSet;
    }

    public static AnimationSet createAlertDialogMissAnimation(boolean shareInterpolator, long durationMillis) {
        AnimationSet animationSet = new AnimationSet(shareInterpolator);

        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0f,
                Animation.RELATIVE_TO_PARENT,
                0.5f);

        ta.setDuration(durationMillis);
        ta.setInterpolator(new AnticipateInterpolator());
        ta.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(
                1,
                0.9f,
                1,
                0.9f,
                ScaleAnimation.RELATIVE_TO_SELF,
                0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,
                0.5f);
        sa.setDuration(durationMillis);

        AlphaAnimation aa = new AlphaAnimation(
                1,
                0.2f);
        aa.setDuration(durationMillis);
        aa.setFillAfter(true);

        animationSet.addAnimation(ta);
        animationSet.addAnimation(aa);
        animationSet.addAnimation(sa);

        return animationSet;
    }

    /**
     * 创建一个输入框获取输入焦点的动画
     *
     * @param shareInterpolator
     * @param durationMillis
     * @param hasFocus
     * @return
     */
    public static AnimationSet createInputFocusAnimation(boolean shareInterpolator, long durationMillis, boolean hasFocus) {
        AnimationSet animationSet = new AnimationSet(shareInterpolator);

        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0,
                Animation.RELATIVE_TO_SELF,
                0,
                Animation.RELATIVE_TO_PARENT,
                hasFocus ? 0 : 0.3f,
                Animation.RELATIVE_TO_PARENT,
                hasFocus ? 0.3f : 0);

        ta.setDuration(durationMillis);
        ta.setInterpolator(hasFocus ? new AnticipateInterpolator() : new OvershootInterpolator());
        ta.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(
                hasFocus ? 1 : 0,
                hasFocus ? 0 : 1);

        aa.setDuration(durationMillis);
        animationSet.addAnimation(ta);
        animationSet.addAnimation(aa);
        return animationSet;
    }

    /**
     * 创建一个上升进入动画
     *
     * @param shareInterpolator
     * @param durationMillis
     * @return
     */
    public static AnimationSet createRiseInAnimation(boolean shareInterpolator, long durationMillis) {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation ta = new TranslateAnimation(type, 0, type, 0, type, 1, type, 0);
        ta.setDuration(durationMillis);
        ta.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(durationMillis);

        AnimationSet animationSet = new AnimationSet(shareInterpolator);
        animationSet.addAnimation(ta);
        animationSet.addAnimation(aa);
        return animationSet;
    }

    /**
     * 创建一个下降消失动画
     *
     * @param shareInterpolator
     * @param durationMillis
     * @return
     */
    public static AnimationSet createFallOutAnimation(boolean shareInterpolator, long durationMillis) {
        TranslateAnimation ta = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,
                0,
                TranslateAnimation.RELATIVE_TO_SELF,
                0,
                TranslateAnimation.RELATIVE_TO_SELF,
                0,
                TranslateAnimation.RELATIVE_TO_SELF,
                1);
        ta.setDuration(durationMillis);
        ta.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(1, 0);
        aa.setDuration(durationMillis);
        aa.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(shareInterpolator);
        animationSet.addAnimation(ta);
        animationSet.addAnimation(aa);
        return animationSet;
    }

    /**
     * 展现一个旋转的动画
     *
     * @param view
     * @param durationMillis
     * @param listener
     */
    public static void showCircleView(final View view, long durationMillis, final onOnAnimationEndListener listener) {
        RotateAnimation animation = new RotateAnimation(
                0f,
                360 * 3f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f
        );
        animation.setDuration(durationMillis);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onOnAnimationEnd();
                }
            }
        });
        view.startAnimation(animation);
        animation.start();
    }

    public interface onOnAnimationEndListener {

        void onOnAnimationEnd();

    }
}
