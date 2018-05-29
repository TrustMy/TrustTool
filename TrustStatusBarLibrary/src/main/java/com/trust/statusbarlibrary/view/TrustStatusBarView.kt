package com.trust.statusbarlibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Trust on 2018/5/28.
 *
 */
class TrustStatusBarView (context: Context?, attrs: AttributeSet?) : View(context, attrs){
    private var angle:Int = 0

    private var colorArray:IntArray? = intArrayOf(Color.parseColor("#ff0000"),
            Color.parseColor("#0000ff"))

    companion object {
         var TOP:Int = 0
         var LEFT:Int = 1
         var RIGHT:Int = 2
         var BOTTOM:Int = 3
    }



    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //获取view宽高
        val width = width
        val height = height
        val paint = Paint()

        val backGradient = when(angle){
            TOP->{LinearGradient(0F, 0F,0F, height.toFloat() ,
                    colorArray , null, Shader.TileMode.CLAMP)}
            LEFT->{LinearGradient(0F, height.toFloat()/2, width.toFloat(), height.toFloat()/2,
                    colorArray , null, Shader.TileMode.CLAMP)}
            BOTTOM->{LinearGradient(0F, height.toFloat(),0F, 0F,
                    colorArray , null, Shader.TileMode.CLAMP)}
            RIGHT->{LinearGradient( width.toFloat(), height.toFloat()/2, 0F, height.toFloat()/2,
                    colorArray , null, Shader.TileMode.CLAMP)}
            else -> {
                LinearGradient(0F, 0F, 0F, height.toFloat(),
                        colorArray , null, Shader.TileMode.CLAMP)
            }
        }

        paint.shader = backGradient
        canvas!!.drawRect(0F,0F,width.toFloat(),height.toFloat(),paint)
    }

    fun setGradients(colorArray:IntArray?){
        if(colorArray!= null){
            this.colorArray = colorArray
            invalidate()
        }
    }

    fun setAngle(angle:Int){
        this.angle = angle
        invalidate()
    }

}