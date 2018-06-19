package com.trust.viewlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.trust.viewlibrary.R.*;

/**
 * Created by Trust on 2018/6/19.
 *
 */

public class TrustPaintView extends View{
    private float posts [] = {0.05F,0.15F,0.25F,0.35F,0.45F} ;
    private int mWidth,mHeight;
    private  Paint paint ,mPaintRadar,mBitPaint;
    private Bitmap bitmap ;
    private Shader scanShader;//扫描渲染颜色
    private Matrix matrix = new Matrix();//旋转矩阵
    private int scanSpeed = 10;//扫描速度
    private int scanAngle;//扫描角度
    private boolean isFirst = true;
    public TrustPaintView(Context context) {
        super(context);
    }

    private synchronized void initPaint() {
        if (isFirst) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(1);
            paint.setAlpha(100);
            paint.setStyle(Paint.Style.STROKE);

            mPaintRadar = new Paint();
            mPaintRadar.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaintRadar.setAntiAlias(true);
//            post(run);


            mBitPaint = new Paint();
            bitmap = BitmapFactory.decodeResource(getResources(), drawable.two);

        }
    }

    public TrustPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrustPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight =getMeasuredHeight();
        mWidth = mHeight = Math.min(mWidth,mHeight);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //雷达图
//        leidatu(canvas);
        initPaint();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        canvas.drawBitmap(bitmap,0,0,null);

        Matrix matrix = new Matrix();
         matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0,
                           height / 2, width, height / 2, matrix, false);


        Paint testPaint = new Paint();

        LinearGradient shader = new LinearGradient(0,
                             bitmap.getHeight(), 0, reflectionImage.getHeight()
                                     + 4,0x70ffffff, 0x00ffffff,
                            Shader.TileMode.MIRROR);
        testPaint.setShader(shader);
        testPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));

        canvas.drawBitmap(reflectionImage,0,bitmap.getHeight()+4,null);
        canvas.drawRect(0, bitmap.getHeight(), bitmap.getWidth(), reflectionImage.getHeight()
                + bitmap.getHeight(), testPaint);
    }

    private void leidatu(Canvas canvas) {
        initPaint();


        for (int i = 0; i < posts.length; i++) {
            canvas.drawCircle(mWidth/2,mHeight/2,posts[i] * mWidth,paint);
        }
        //画布旋转变换需要save restore
        canvas.save();
        scanShader = new SweepGradient(mWidth/2,mHeight/2,
                new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")},null);
        mPaintRadar.setShader(scanShader);
        canvas.concat(matrix);
        canvas.drawCircle(mWidth/2,mHeight/2,mWidth * posts[4],mPaintRadar);

        canvas.restore();
    }


    private Runnable run  = new Runnable() {
        @Override
        public void run() {
            scanAngle =  (scanSpeed + scanAngle) %360;//旋转的角度
            matrix.postRotate(scanSpeed,mWidth/2,mHeight/2);
            invalidate();
            postDelayed(run,50);
        }
    };
}
