package com.siddharth.coolcustomviews.nuromorphicUi

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.siddharth.coolcustomviews.R
import kotlin.math.min

class NeuroProgressBar(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var paintProgress = Paint()
    private var paintContainer = Paint()
    private var paintDarkShadow = Paint()
    private var paintNormal = Paint()
    private var paintInnerShadow = Paint()
    private var radius: Int = 0
    private var length = min(height, width).toFloat()
    private var animatorSweepAngle = ValueAnimator()
    private var animatorColorAngle = ValueAnimator()
    private var isCircle = false
    private var sweepAngle = 0F
    private var startAngle = 0F
    private val ovalSpace = RectF()
    private var isLoading = false


    init {
        isClickable = true
        setupValueAnimator()
    }

    private fun setupValueAnimator() {
        animatorSweepAngle = ValueAnimator.ofFloat(0F, 360F)
        animatorSweepAngle.duration = 3000
        animatorSweepAngle.addUpdateListener {
            sweepAngle = it.animatedValue as Float
            if (!isLoading) {
                isLoading = true
                // paintProgress.strokeWidth = 35f
            }
            if (sweepAngle == 360F) {
                isLoading = false
                //paintProgress.strokeWidth = 20f
            }
            invalidate()
        }
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true
        animatorSweepAngle.start()
        return true
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        length = min(w.toFloat(), h.toFloat())
        setupPaints(w, h)
        setSpace(ovalSpace)
    }

    private fun setupPaints(w: Int, h: Int) {
        paintProgress.apply {
            style = Paint.Style.STROKE
            color = context.getColor(R.color.purple_200)
            isAntiAlias = true
            strokeWidth = getPaintStrokeWidthForProgress()
            strokeCap = Paint.Cap.ROUND
            maskFilter = BlurMaskFilter(10F, BlurMaskFilter.Blur.INNER)
        }
        paintContainer.apply {
            style = Paint.Style.STROKE
            color = context.getColor(R.color.light_grey)
            isAntiAlias = true
            strokeWidth = getPaintStrokeWidth(false)
            strokeCap = Paint.Cap.ROUND
            maskFilter = BlurMaskFilter(getBlurRadius(w, h, PaintType.container), BlurMaskFilter.Blur.OUTER)
        }
        paintDarkShadow.apply {
            style = Paint.Style.STROKE
            color = context.getColor(R.color.dark_grey)
            isAntiAlias = true
            strokeWidth = getPaintStrokeWidth(false)
            strokeCap = Paint.Cap.ROUND
            maskFilter = BlurMaskFilter(getBlurRadius(w, h, PaintType.darkShadow), BlurMaskFilter.Blur.NORMAL)
        }
        paintNormal.apply {
            style = Paint.Style.FILL
            color = context.getColor(R.color.grey)
            isAntiAlias = true
            strokeWidth = getPaintStrokeWidth(true)
            strokeCap = Paint.Cap.ROUND
        }
        paintInnerShadow.apply {
            style = Paint.Style.STROKE
            color = context.getColor(R.color.grey)
            isAntiAlias = true
            strokeWidth = getPaintStrokeWidthForProgress()
            strokeCap = Paint.Cap.ROUND
            maskFilter = BlurMaskFilter(15F, BlurMaskFilter.Blur.INNER)
        }
    }

    private fun getPaintStrokeWidthForProgress(): Float {
        return (30F / 200) * (min(width / 3, height / 3))
    }

    private fun getPaintStrokeWidth(isForPaintNormal: Boolean): Float {
        if (isForPaintNormal) {
            return (23F / 200) * (min(width / 3, height / 3))
        } else
            return (20F / 200) * (min(width / 3, height / 3))
    }

    private fun getBlurRadius(w: Int, h: Int, type: PaintType): Float {
        if (type is PaintType.container) {
            return (50F / 200) * (min(w / (3), h / (3)).toFloat())
        } else
            return (60F / 200) * (min(w / (3), h / (3)).toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(ovalSpace, 130F, 200F, false, paintContainer)
        canvas?.drawArc(ovalSpace, -40F, 150F, false, paintDarkShadow)
        canvas?.drawArc(ovalSpace, 0F, 360F, false, paintNormal)
        canvas?.drawArc(ovalSpace, 0F, 360F, false, paintInnerShadow)
        canvas?.drawArc(ovalSpace, 270F, sweepAngle, false, paintProgress)
    }

    private fun setSpace(ovalSpace: RectF) {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()
        val ovalSize = min(width / (3), height / (3)).toFloat()
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    sealed class PaintType() {
        object container : PaintType()
        object darkShadow : PaintType()
    }
}
