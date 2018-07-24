package com.trust.demo.basis.trust.weight;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.trust.demo.basis.R;


public class ImplRecyclerViewRefreshStateCall implements TrustRecyclerView.RecyclerViewRefreshStateCall {

    private TextView mTv;
    private ImageView mIv;
    private ObjectAnimator mAni;
    View mHeaderView;


    public ImplRecyclerViewRefreshStateCall(TrustRecyclerView pullRefreshRecyclerView) {
        mHeaderView = pullRefreshRecyclerView.getRefreshHeaderView();
        mTv = (TextView) mHeaderView.findViewById(R.id.tv);
        mIv = (ImageView) mHeaderView.findViewById(R.id.iv);
        mAni = ObjectAnimator.ofFloat(mIv, "rotation", 0, 360).setDuration(300);
        mAni.setInterpolator(new LinearInterpolator());
        mAni.setRepeatCount(ValueAnimator.INFINITE);
        mAni.setRepeatMode(ValueAnimator.RESTART);
    }


    /**
     * 下拉时执行缩放
     * @param scrollY        下拉的距离
     * @param headviewHeight 头布局高度
     * @param deltaY         moveY-lastMoveY,正值为向下拉
     */
    @Override
    public void onPullDownRefreshState(int scrollY, int headviewHeight, int deltaY) {
        mTv.setText("下拉刷新");
        float f = -((float) scrollY / (float) headviewHeight);
        Log.e("tag", f+ "");
        Log.e("tag", -scrollY + "scrollY");


        mIv.setScaleX(f);
        mIv.setScaleY(f);
    }

    /**
     *
     * 松手时调用
     * @param scrollY 下拉的距离
     * @param deltaY  moveY-lastMoveY,正值为向下拉
     */
    @Override
    public void onReleaseRefreshState(int scrollY, int deltaY) {
        mTv.setText("松手刷新");

    }

    /**
     * 正在刷新时
     */
    @Override
    public void onRefreshingState() {
        mTv.setText("正在刷新");
        mIv.setScaleX(1.0f);
        mIv.setScaleY(1.0f);
        mAni.start();

    }

    @Override
    public void onDefaultState() {
        if (mAni.isRunning()){
            mAni.end();
            mIv.setRotation(0);
        }
    }

}
