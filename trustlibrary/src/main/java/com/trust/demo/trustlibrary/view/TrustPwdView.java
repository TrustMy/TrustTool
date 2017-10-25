package com.trust.demo.trustlibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * Created by Trust on 2017/9/24.
 */

@SuppressLint("AppCompatCustomView")
public class TrustPwdView extends EditText {

    private Paint mPaint;
    private Paint mPaintCentent;
    private Paint mPaintArc;

    private int maxLineSize ;
    private int maxLength;
    private int mPadding =1;

    private boolean isAddText;

    private int raius;

    private int textLength;
    private int cireieRadiu;

    private float interpllatorTime;

    private String trustPwd;
    private PaintLastAinm paintLastAinm;
    public TrustPwdView(Context context) {
        this(context,null);
    }

    public TrustPwdView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TrustPwdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setStyle(Paint.Style.FILL);


        mPaintCentent = new Paint();
        mPaintCentent.setAntiAlias(true);
        mPaintCentent.setStyle(Paint.Style.FILL);
        mPaintCentent.setColor(Color.WHITE);

        maxLineSize = getMaxLength();
        raius = dp2px(6);

        cireieRadiu = dp2px(6);

        paintLastAinm = new PaintLastAinm();
        paintLastAinm.setDuration(200);
        paintLastAinm.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (textLength == getMaxLength()) {
                    if (enterSuccessCallBack != null) {
                        enterSuccessCallBack.CallBack(trustPwd);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private int dp2px(int i) {
        float scal = getContext().getResources().getDisplayMetrics().density;

        return (int) (i * scal + 0.5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF =new RectF(mPadding,mPadding,getMeasuredWidth() - mPadding,
                getMeasuredHeight()- mPadding);

        canvas.drawRoundRect(rectF,raius,raius,mPaintCentent);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF,raius,raius,mPaint);

        float cy = getMeasuredHeight()/2;

        float half = getMeasuredWidth()/maxLineSize/2;
        mPaint.setStrokeWidth(0.5f);

        for (int i = 1; i < maxLineSize; i++) {
            float x = getMeasuredWidth()/maxLineSize*i;
            canvas.drawLine(x,0,x,getMeasuredHeight(),mPaint);

        }

        for (int i = 0; i < maxLineSize; i++) {
            float x = getMeasuredWidth()/maxLineSize*i+half;

            if (isAddText) {

               if(i<textLength-1){
                    canvas.drawCircle(x,cy,cireieRadiu,mPaintArc);
               } else if (i == textLength-1) {
                    canvas.drawCircle(x,cy,cireieRadiu*interpllatorTime,mPaintArc);
               }

            }else{
                if(i<=textLength-1){
                    canvas.drawCircle(x,cy,cireieRadiu,mPaintArc);
                } else if (i == textLength-1) {
                    canvas.drawCircle(x,cy,cireieRadiu - cireieRadiu*interpllatorTime,mPaintArc);
                }

            }

        }


    }

    public int getMaxLength() {
        int length = 10;
        InputFilter[] inputFilters = getFilters();
        for (InputFilter inputFilter : inputFilters) {
            Class<?> c =inputFilter.getClass();
            if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                Field[] f = c.getDeclaredFields();
                for (Field field : f) {
                    if (field.getName().equals("mMax")) {
                        field.setAccessible(true);
                        try {
                            length = (int) field.get(inputFilter);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return length;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (getText().toString().length()>this.textLength) {
            isAddText = true;
        }else{
            isAddText = false;
        }


        if (textLength<= getMaxLength()) {
            if (paintLastAinm != null) {
                clearAnimation();
                startAnimation(paintLastAinm);
            }

        }

        textLength = text.length();

        trustPwd = text.toString();
    }

    private class PaintLastAinm extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            TrustPwdView.this.interpllatorTime = interpolatedTime;
            postInvalidate();
        }
    }

    public interface onEnterSuccessCallBack{void CallBack(String msg);}
    onEnterSuccessCallBack enterSuccessCallBack;

    public void setOnEnterSuccessCallBack(onEnterSuccessCallBack enterSuccessCallBack){
        this.enterSuccessCallBack = enterSuccessCallBack;
    };
}
