package com.trust.viewlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Trust on 2018/6/21.
 */

public class TrustRenderingView extends View {
    private Bitmap mBitmap;
    private Bitmap mChangeBitmap;
    private Paint mChangePaint;
    public TrustRenderingView(Context context) {
        super(context);
    }

    public TrustRenderingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrustRenderingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.two);
        mChangeBitmap = mBitmap;
        mChangePaint = new Paint();

        /**
         *
         * 初始通道  这个通道不修改的话 和原图颜色一样
         1,0,0,0,0,  red
         0,1,0,0,0,  green
         0,0,1,0,0,  blue
         0,0,0,1,0,
         */
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1,0,0,0,100,//红色增加100
                0,2,0,0,0,//绿色增加原来的两倍
                0,0,1,0,0,
                0,0,0,1,0,
        });

        mChangePaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap,0,0,null);

        canvas.drawBitmap(mChangeBitmap,0,mBitmap.getHeight()+4,mChangePaint);

    }
}
