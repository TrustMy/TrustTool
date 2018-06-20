package com.trust.viewlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Trust on 2018/6/20.
 */

public class TrustZommImageView extends View {
    private int RADIUS = 100;
    private int FACTOR = 2;

    private Bitmap mBitmap;
    private Bitmap mBigBitmap;
    private ShapeDrawable mShapeDrawable;
    private Matrix mMatrix;

    public TrustZommImageView(Context context) {
        super(context);


    }

    public TrustZommImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrustZommImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.two);
        mBigBitmap = mBitmap;

        mBigBitmap = Bitmap.createScaledBitmap(mBigBitmap,mBigBitmap.getWidth() * FACTOR,
                mBigBitmap.getHeight() * FACTOR,true);

        //放大图片
        BitmapShader bitmapShader = new BitmapShader(mBigBitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);

        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        //切除矩形区域,绘制圆
        mShapeDrawable.setBounds(0,0,RADIUS * 2 , RADIUS * 2);

        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(mBitmap,0,0,null);

        mShapeDrawable.draw(canvas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y  = (int) event.getY();

        mMatrix.setTranslate(RADIUS - x * FACTOR,RADIUS - y * FACTOR);
        mShapeDrawable.getPaint().getShader().setLocalMatrix(mMatrix);
        //切出手势区域点位置的图
        mShapeDrawable.setBounds(x - RADIUS ,  y - RADIUS ,  x + RADIUS , y +RADIUS);
        invalidate();
        return true;
    }
}
