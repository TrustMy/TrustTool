package com.trust.viewlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Trust on 2018/6/20.
 */

public class TrustNeonView extends AppCompatTextView {
    private TextPaint mPaint;
    private int row;
    private LinearGradient mLinearGradient;
    private float mTranslate;
    private float DELTAX = 20;
    int curRow = 1;
    boolean isBack = false;
    private Matrix mMatrix;
    public TrustNeonView(Context context) {
        super(context);
    }

    public TrustNeonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrustNeonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = getPaint();
        String text = getText().toString();
        float textWith = mPaint.measureText(text);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float height = fontMetrics.bottom  -fontMetrics.top;
        float size = getTextSize();
        row = (int) (height / size);

        int gradientSize  = (int) (textWith /text.length() *3);
        mLinearGradient = new LinearGradient(-gradientSize,size * curRow,0,
                size * curRow,new int[]{0x22ffffff, 0xffffffff, 0x22ffffff},null,
                Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isBack) {
            mTranslate += DELTAX;
        }else{
            mTranslate -= DELTAX;
        }
        float textWidth = getPaint().measureText(getText().toString());
        Log.d("lhh", "textWidth: "+textWidth );
        Log.d("lhh", "mTranslate: "+mTranslate );
        if (mTranslate >textWidth +1 || mTranslate <1) {
            if(mTranslate >textWidth +1){
                isBack = true;
            }else if (mTranslate <1) {
                mTranslate = 0;
                isBack = false;
            }

            curRow++;
            if (curRow >row) {

                curRow = 1;
            }
        }

        mMatrix = new Matrix();
        mMatrix.setTranslate(mTranslate,0);
        mLinearGradient.setLocalMatrix(mMatrix);
        postInvalidateDelayed(100);
    }
}
