package com.siddharth.coolcustomviews.graphAnimator

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import java.lang.Math.tan
import kotlin.math.sin

class GraphView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var currentX = 0F
    private var currentY = 0F
    private var paint = Paint()
    private var xAnimator = ValueAnimator()
    private var pointList: MutableList<Pair<Float, Float>> = mutableListOf()

    init {
        isClickable = true
        paint.apply {
            color = Color.WHITE
            isAntiAlias = true
            strokeWidth = 0F
        }
        setupxValueAnimator()
        for (i in 1..50)
            pointList.add(Pair(i.toFloat(), getY(i.toFloat())))
    }

    override fun performClick(): Boolean {
        if(super.performClick()) return true
        startAnim()
        return true
    }

    private fun setupxValueAnimator() {
        xAnimator = ValueAnimator.ofFloat(0F, 800F)
        xAnimator.apply {
            duration = 3000
            this.repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            this.addUpdateListener {
                Log.d("GraphView","setupxValueAnim")
                currentX = it?.animatedValue as Float
                for (i in 0 until pointList.size) {
                    pointList[i] = Pair(currentX + i + 5, getY(currentX + i + 5))
                }
                invalidate()
            }
        }
    }

    private fun startAnim() {
        xAnimator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("onDraw", "GraphView")
        for (i in pointList)
            canvas?.drawCircle(i.first, i.second, 5F, paint)
    }

    fun getY(x: Float): Float {
        return (sin((x/50)) *200 + 700)
    }
}