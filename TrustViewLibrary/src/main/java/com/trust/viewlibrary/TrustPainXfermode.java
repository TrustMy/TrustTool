package com.trust.viewlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Trust on 2018/6/21.
 */

public class TrustPainXfermode extends View {
    private  Paint  oldPaint , newPaint;

    public TrustPainXfermode(Context context) {
        super(context);
    }

    public TrustPainXfermode(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrustPainXfermode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }





    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        oldPaint = new Paint();
        oldPaint.setAntiAlias(true);
        oldPaint.setColor(Color.RED);

        newPaint = new Paint();
        newPaint.setAntiAlias(true);
        newPaint.setColor(Color.BLUE);


        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //dst
        canvas.drawRect(60,60,200,200,newPaint);
        //src
        canvas.drawCircle(110,110,80,oldPaint);


        //dst
        canvas.drawCircle(420,140,80,oldPaint);
        newPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //src
        canvas.drawRect(360,60,500,200,newPaint);



    }
}
