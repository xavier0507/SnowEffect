package com.xy.snoweffect.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.percent.PercentFrameLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pools;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.xy.snoweffect.R;
import com.xy.snoweffect.util.RandomTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier Yin on 4/13/17.
 */

public class SnowEffectFrameLayout extends PercentFrameLayout {
    private final String TAG = this.getClass().getSimpleName();

    private static final int DEFAULT_SNOW_BASIC_COUNT = 20;
    private static final int DEFAULT_DROP_AVERAGE_DURATION = 18000;
    private static final int DEFAULT_DROP_FREQUENCY = 4000;
    private static final float RELATIVE_DROP_DURATION_OFFSET = 0.55F;
    private static int SNOW_BASIC_SIZE;

    private int snowBasicCount;
    private int dropAverageDuration;
    private int dropFrequency;

    private List<Drawable> snowList;
    private Pools.SynchronizedPool<ImageView> snowPool;
    private int windowHeight;

    {
        SNOW_BASIC_SIZE = this.dip2px(12);
    }

    public SnowEffectFrameLayout(Context context) {
        this(context, null);
    }

    public SnowEffectFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnowEffectFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    public void addDrawable(int drawableResId) {
        this.snowList.add(ContextCompat.getDrawable(getContext(), drawableResId));
    }

    public void clearDrawableList() {
        this.snowList.clear();
    }

    public void setSnowBasicCount(int snowBasicCount) {
        this.snowBasicCount = snowBasicCount;
    }

    public void setDropAverageDuration(int dropAverageDuration) {
        this.dropAverageDuration = dropAverageDuration;
    }

    public void startEffect() {
        this.initSnowPool();
        this.launchAnim();
    }

    private int dip2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SnowEffectFrameLayout);
        this.snowBasicCount = typedArray.getInteger(R.styleable.SnowEffectFrameLayout_snowBasicCount, DEFAULT_SNOW_BASIC_COUNT);
        this.dropAverageDuration = typedArray.getInteger(R.styleable.SnowEffectFrameLayout_dropAverageDuration, DEFAULT_DROP_AVERAGE_DURATION);
        this.dropFrequency = DEFAULT_DROP_FREQUENCY;

        this.snowList = new ArrayList<>();
        if ((this.snowList == null || this.snowList.size() == 0)) {
            this.snowList.add(ContextCompat.getDrawable(this.getContext(), R.drawable.snow));
        }
    }

    private void initSnowPool() {
        final int snowCount = this.snowList.size();
        if (snowCount == 0) {
            throw new IllegalStateException("There are no drawables.");
        }

        this.cleanSnowPool();

        final int expectedMaxSnowCountOnScreen = (int) ((1 + RELATIVE_DROP_DURATION_OFFSET) * snowBasicCount * dropAverageDuration / ((float) dropFrequency));
        this.snowPool = new Pools.SynchronizedPool<>(expectedMaxSnowCountOnScreen);
        for (int i = 0; i < expectedMaxSnowCountOnScreen; i++) {
            final ImageView snow = this.generateSnowImage(this.snowList.get(i % snowCount));
            this.addView(snow, 0);
            this.snowPool.release(snow);
        }

        RandomTool.setSeed(10);
    }

    private void cleanSnowPool() {
        if (this.snowPool != null) {
            ImageView currentSnow;
            while ((currentSnow = this.snowPool.acquire()) != null) {
                this.removeView(currentSnow);
            }
        }
    }

    private ImageView generateSnowImage(Drawable drawable) {
        ImageView snow = new ImageView(this.getContext());
        snow.setImageDrawable(drawable);

        final int width = (int) (SNOW_BASIC_SIZE * (1.0 + RandomTool.positiveGaussian()));
        final int height = (int) (SNOW_BASIC_SIZE * (1.0 + RandomTool.positiveGaussian()));
        final LayoutParams params = new LayoutParams(width, height);
        params.getPercentLayoutInfo().leftMarginPercent = RandomTool.floatStandard();
        params.topMargin = -height;
        snow.setLayoutParams(params);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            snow.setElevation(500);
        }

        return snow;
    }

    private void launchAnim() {
        Log.i(TAG, "snowBasicCount: " + this.snowBasicCount);
        Log.i(TAG, "dropAverageDuration: " + this.dropAverageDuration);
        Log.i(TAG, "dropFrequency: " + this.dropFrequency);

        this.windowHeight = this.getHeight();

        ImageView snow;
        while (((snow = this.snowPool.acquire()) != null)) {
            this.startDropAnimationForSingleSnow(snow);
        }
    }

    private void startDropAnimationForSingleSnow(final ImageView snow) {
        final int currentDuration = (int) (this.dropAverageDuration * RandomTool.floatInRange(1, RELATIVE_DROP_DURATION_OFFSET));
        final TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, RandomTool.floatInRange(0, 5),
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.ABSOLUTE, this.windowHeight);
        translateAnimation.setDuration(currentDuration);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                snowPool.release(snow);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        snow.startAnimation(translateAnimation);

//        final RotateAnimation rotateAnimation = new RotateAnimation(
//                0,
//                RandomTool.floatInRange(0, 360),
//                Animation.RELATIVE_TO_SELF,
//                0.5f,
//                Animation.RELATIVE_TO_SELF,
//                0.5f);

//        final AnimationSet animationSet = new AnimationSet(false);
//        animationSet.addAnimation(rotateAnimation);
//        animationSet.addAnimation(translateAnimation);
//        animationSet.setDuration(currentDuration);
//        animationSet.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                snowPool.release(snow);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//
//        snow.startAnimation(animationSet);
    }
}
