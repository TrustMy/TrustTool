package com.trust.demo.trusttool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Trust on 2018/7/11.
 */

public class TestPaintView extends View {
    private float[] pos;//当前点实际位置
    private float[] tan;//当前点tangent 用于计算图片需要旋转的角度
    private Bitmap bitmap;//箭头图标
    private Matrix matrix;//对图片进行从操作
    private Paint deafultPaint;
    private int viewWidth,viewHeight;
    private float currentValue = 0;//记录当前位置
    public TestPaintView(Context context) {
        this(context,null);
    }

    public TestPaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;//缩放图片
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher,options);
        matrix = new Matrix();

        deafultPaint = new Paint();
        deafultPaint.setColor(Color.BLACK);
        deafultPaint.setStrokeWidth(5);
        deafultPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(300,50);



        Path path = new Path();

        path.addCircle(100,100,100,Path.Direction.CW);

        PathMeasure measure = new PathMeasure(path,false);
        currentValue += 0.005;
        if (currentValue>=1) {
            currentValue = 0;
        }
        measure.getPosTan(measure.getLength() * currentValue,pos,tan);
//        canvas.drawCircle(tan[0],tan[1],20,deafultPaint);

        matrix.reset();

        float degrees = (float)(Math.atan2(tan[1],tan[0]) * 180.0 /Math.PI);
        matrix.postRotate(degrees,bitmap.getWidth() /2 ,bitmap.getHeight() /2);

        matrix.postTranslate(pos[0] - bitmap.getWidth()/2,pos[1] - bitmap.getHeight() /2);

        canvas.drawPath(path,deafultPaint);
        canvas.drawBitmap(bitmap,matrix,deafultPaint);
        invalidate();

    }
}
