package com.bit.whdalive.flipboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MapView mMapView;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.map_view);

        //三段动画
        //1."右侧" 翻折，"左侧" 不动
        //2."右侧" 旋转，"左侧" 配合旋转
        //3.收尾 "左侧" 翻折，"右侧" 不动
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mMapView,"degreeY",0,-45);
        animator1.setDuration(1000);
        animator1.setStartDelay(500);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mMapView,"degreeZ",0,270);
        animator2.setDuration(800);
        animator2.setStartDelay(500);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mMapView,"fixY",0,30);
        animator3.setDuration(500);
        animator3.setStartDelay(500);

        final AnimatorSet animatorSet = new AnimatorSet();
        //循环播放
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                /*
                //用handler的目的在这里只是为了模拟 原图中，在动画最后短暂停留
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            */
                                //将属性重置
                                mMapView.reset();
                                animatorSet.start();
                            }
                        });
        /*
                    }
                },500);
            }
        });
        */

        //顺序播放动画
        animatorSet.playSequentially(animator1,animator2,animator3);
        animatorSet.start();
    }
}
