package com.trust.viewlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : tyh
 * @time : 2018/06/14
 * @for :
 */
public class Flowlayoutsss extends ViewGroup {


    private Holder mHodler;
    private boolean isMeasured = false;
    private boolean isLayouted = false;

    public Flowlayoutsss(Context context) {
        super(context);
    }

    public Flowlayoutsss(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Flowlayoutsss(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    private static final String TAG = "===Flowlayout===";

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //防止一个流程中的第二次调用
        if (isMeasured) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            isMeasured = false;
            return;
        }
        isMeasured = true;
        Log.i(TAG, "onMeasure: 开始测量");
        mHodler = new Holder();
        mHodler.measure(this, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mHodler.measuredWith, mHodler.measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //防止一个流程中的第二次调用
        if (isLayouted) {
            isMeasured = false;
            isLayouted = false;
            return;
        }
        isLayouted = true;
        Log.i(TAG, "onLayout: 开始布局");
        mHodler.layout(this);
        mHodler = null;
    }

    class Holder {
        /**
         * 测量后的宽高
         */
        private int measuredWith;
        private int measuredHeight;
        /**
         * 行高列表
         */
        private List<Integer> lstLineHight = new ArrayList<>();

        /**
         * 每行的view
         */
        private List<List<View>> lstViews = new ArrayList<>();

        Holder() {}

        /**
         * 执行测量
         */
        void measure(ViewGroup parent, int widthMeasureSpec, int heightMeasureSpec) {
            //解析尺寸数据
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            //是否需要测量宽高
            boolean needWidth = widthMode != MeasureSpec.EXACTLY;
            boolean needHeight = heightMode != MeasureSpec.EXACTLY;

            //最大宽高-->达到最大宽高了,才会自动换行/列
            int maxWidth = needWidth ? Integer.MAX_VALUE : widthSize;
            //todo:预留  如果是横向排列的,就会用到这个参数,留着以后完善
            int maxHeight = needHeight ? Integer.MAX_VALUE : heightSize;

            //当前行的宽度,高度
            int iCurLineWidth = 0;
            int iCurLineHeight = 0;

            int iChildWidth;
            int iChildHeight;

            int count = parent.getChildCount();
            List<View> iCurLstLineChildView = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
                measureChildWithMargins(child, widthMeasureSpec, params.leftMargin + params.rightMargin,
                        heightMeasureSpec, params.topMargin + params.bottomMargin);

                iChildWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                iChildHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

                //判断是否要换行了
                if (iCurLineWidth + iChildWidth > maxWidth - getPaddingLeft() - getPaddingRight()) {
                    //需要换行
                    //1.记录上一行内容 -->一行中的child集合
                    lstViews.add(iCurLstLineChildView);
                    lstLineHight.add(iCurLineHeight);
                    measuredHeight += iCurLineHeight;
                    measuredWith = Math.max(measuredWith, iCurLineWidth);
                    //2.保存当前行内容
                    iCurLstLineChildView = new ArrayList<>();
                    iCurLstLineChildView.add(child);
                    iCurLineWidth = iChildWidth;
                    iCurLineHeight = iChildHeight;
                } else {
                    //不需要换行
                    //1.记录宽高信息
                    iCurLineWidth += iChildWidth;
                    iCurLineHeight = Math.max(iCurLineHeight, iChildHeight);
                    //2.将child添加到当前list
                    iCurLstLineChildView.add(child);
                }

                //如果是最后一个view了,则为最后一行,要记录行信息
                if (i == count - 1) {
                    lstViews.add(iCurLstLineChildView);
                    lstLineHight.add(iCurLineHeight);
                    measuredHeight += iCurLineHeight;
                    measuredWith = Math.max(measuredWith, iCurLineWidth);
                }
            }

            if (!needWidth) {
                measuredWith = maxWidth;
            } else {
                //增加padding
                measuredWith += getPaddingLeft() + getPaddingRight();
            }

            if (!needHeight) {
                measuredHeight = maxHeight;
            } else {
                //增加padding
                measuredHeight += getPaddingTop() + getPaddingBottom();
            }
        }

        public int getMeasuredWith() {
            return measuredWith;
        }

        public int getMeasuredHeight() {
            return measuredHeight;
        }

        public void layout(ViewGroup parent) {
            //子view布局参数
            int left, top, right, bottm;

            //每个子view布局的起点
            int curLeft = parent.getPaddingLeft();
            int curTop = parent.getPaddingTop();

            //逐行去layout
            for (int i = 0; i < mHodler.lstViews.size(); i++) {
                List<View> iLstLineViews = mHodler.lstViews.get(i);
                for (int j = 0; j < iLstLineViews.size(); j++) {
                    View child = iLstLineViews.get(j);
                    MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

                    left = curLeft + params.leftMargin;
                    top = curTop + params.topMargin;
                    right = left + child.getMeasuredWidth();
                    bottm = top + child.getMeasuredHeight();
                    curLeft += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                    child.layout(left, top, right, bottm);
                }
                //另起一行
                curLeft = parent.getPaddingLeft();
                curTop += mHodler.lstLineHight.get(i);
            }
        }
    }

}
