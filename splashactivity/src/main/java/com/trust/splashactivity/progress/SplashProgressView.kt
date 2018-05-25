package com.trust.splashactivity.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.trust.splashactivity.R

/**
 * Created by Trust on 2018/5/15.
 */
class SplashProgressView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    //实心圆画笔
    private var mCirclePaint:Paint? =null
    //圆环画笔
    private var mRingPaint:Paint? = null
    //圆环画笔本景色
    private var mRingPaintBg : Paint? = null
    //字体画笔
    private var mTextPain:Paint? = null
    //字体颜色
    private var mTextColor:Int = 0
    //实心圆颜色
    private var mCirecleColor:Int = 0
    //圆环颜色
    private var mRingColor:Int = 0
    //实心圆半径
    private var mRadius:Float = 0F
    //圆环半径
    private var mRingRadius:Float = 0F
    //圆环宽度
    private var mStrokeWidth:Float = 0F
    //圆环背景色
    private var mRingBgColor:Int = 0
    //圆心x坐标
    private var mXCenter:Int = 0
    //圆心y坐标
    private var mYCenter:Int = 0
    //字长度
    private var mTextWidth:Float = 0F
    //字高度
    private var mTextHeight:Float = 0F
    //总进度
    private var mTotalProgress:Float = 1000.0F
    //当前进度
    private var mProgress:Float = 0F


    init {
        var typedArray = context!!.theme.obtainStyledAttributes(attrs,
                R.styleable.SplashProgressView,0,0)
        mRadius = typedArray.getDimension(R.styleable.SplashProgressView_radius,80F)
        mStrokeWidth = typedArray.getDimension(R.styleable.SplashProgressView_strokeWidth,10F)
        mCirecleColor = typedArray.getColor(R.styleable.SplashProgressView_circleColor, Color.parseColor("#00ff00"))
        mRingColor  = typedArray.getColor(R.styleable.SplashProgressView_ringColor,Color.parseColor("#ff0000"))
        mRingBgColor  = typedArray.getColor(R.styleable.SplashProgressView_ringBgColor,Color.parseColor("#0000ff"))
        mTextColor  = typedArray.getColor(R.styleable.SplashProgressView_textColor,Color.parseColor("#ffffff"))

        mRingRadius = mRadius + mStrokeWidth / 2
        initVariable()
    }

    private fun initVariable() {
        //内圆
        mCirclePaint = Paint()
        mCirclePaint?.isAntiAlias= true
        mCirclePaint?.color = mCirecleColor
        mCirclePaint?.style = Paint.Style.FILL


        //外圆弧 背景
        mRingPaintBg = Paint()
        mRingPaintBg?.isAntiAlias = true
        mRingPaintBg?.color = mRingBgColor
        mRingPaintBg?.style = Paint.Style.STROKE
        mRingPaintBg?.strokeWidth = mStrokeWidth

        //外圆弧
        mRingPaint = Paint()
        mRingPaint?.isAntiAlias = true
        mRingPaint?.color = mRingColor
        mRingPaint?.style = Paint.Style.STROKE
        mRingPaint?.strokeWidth = mStrokeWidth


        //中间字
        mTextPain = Paint()
        mTextPain?.isAntiAlias = true
        mTextPain?.style = Paint.Style.FILL
        mTextPain?.color = mTextColor
        mTextPain?.textSize = mRadius/2

        val fm  = mTextPain?.fontMetrics
        mTextHeight = Math.ceil((fm!!.descent - fm.ascent).toDouble()).toInt().toFloat()
    }


    override fun onDraw(canvas: Canvas?) {
        mXCenter = width/2
        mYCenter = height/2
        //内圆
        canvas!!.drawCircle(mXCenter.toFloat(), mYCenter.toFloat(),mRadius,mCirclePaint)

        //外圆弧背景
        var oval1 = RectF()
        oval1.left = (mXCenter -mRingRadius)
        oval1.top = (mYCenter -mRingRadius)
        oval1.right = mRingRadius *2 + (mXCenter - mRingRadius)
        oval1.bottom = mRingRadius *2 + (mYCenter - mRingRadius)
        //圆弧所在的椭圆对象、圆弧的起始角度、圆弧的角度、是否显示半径连线
        canvas.drawArc(oval1,0F,360F,false,mRingPaintBg)

        //外圆弧
        if(mProgress >= 0){
            var oval  = RectF()
            oval.left = (mXCenter - mRingRadius)
            oval.top = (mYCenter -mRingRadius)
            oval.right = mRingRadius *2 + (mXCenter - mRingRadius)
            oval.bottom = mRingRadius *2 + (mYCenter - mRingRadius)
            canvas.drawArc(oval,-90F, (mProgress / mTotalProgress) * 360.toFloat(),false,mRingPaint)

            //字体
//            var txt:String = mProgress.toString()+""
            var txt:String = "跳过"
            mTextWidth = mTextPain!!.measureText(txt,0,txt.length)
            canvas.drawText(txt,mXCenter-mTextWidth/2,mYCenter+mTextHeight/4,mTextPain)
        }
    }

    fun setProgress(progress:Float){
        mProgress = progress
        postInvalidate()//重绘
    }
}