package com.siddharth.coolcustomviews.customRV

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.coolcustomviews.R
import kotlin.math.abs

import kotlin.math.*

import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

abstract class MyLayoutManager(private val resources: Resources, private val screenWidth: Int) :
    RecyclerView.LayoutManager() {

    private val TAG = "LayoutManager"
    private var horizontalOffset = 0
    private var viewWidth = resources.getDimensionPixelSize(R.dimen.item_width)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fillSafely(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        // viewWidth = recycler.getViewForPosition(0).measuredWidth

        detachAndScrapAttachedViews(recycler)

        var firstVisiblePosition = floor(horizontalOffset.toDouble() / viewWidth.toDouble()).toInt()
        firstVisiblePosition = max(0, firstVisiblePosition)

        var lastVisiblePosition =
            (((screenWidth.toDouble())) / viewWidth.toDouble()).toInt() + firstVisiblePosition

        lastVisiblePosition = min(itemCount, lastVisiblePosition + 1)

        for (i in firstVisiblePosition..lastVisiblePosition) {
            if (i >= itemCount) return
            val view = recycler.getViewForPosition(i)
            addView(view)
            layoutChildView(i, viewWidth, view)
        }
        recycler.scrapList.toList().forEach { recycler.recycleView(it.itemView) }
    }

    private fun layoutChildView(i: Int, viewWidth: Int, view: View) {
        val left = i * viewWidth - horizontalOffset
        val right = left + (viewWidth)
        val top = getTopOffsetForView(left + viewWidth / 2)
        val bottom = top + (viewWidth)
        measureChild(view, viewWidth, viewWidth)
        layoutDecorated(view, left, top, right, bottom)
    }

    abstract fun getTopOffsetForView(centerXCoordinate: Int): Int

    override fun canScrollHorizontally(): Boolean = true
    override fun canScrollVertically(): Boolean = false

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (screenWidth > itemCount * viewWidth) {
            return 0;
        }

        if ((horizontalOffset <= 0 && dx < 0)) {    // check if user trying to scroll through left end
            horizontalOffset = 0
            // call fill again to redraw views when horizontal offset value was set
            fillSafely(recycler, state)
            return 0
        }

        if ((itemCount * viewWidth <= horizontalOffset + screenWidth) && dx > 0) { // check if user trying to scroll through right end
            horizontalOffset = itemCount * viewWidth - screenWidth
            // call fill again to redraw views when horizontal offset  value was set
            fillSafely(recycler, state)
            return 0
        }

        horizontalOffset += dx
        fillSafely(recycler, state)
        return dx
    }

    private fun fillSafely(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler != null && state != null)
            fill(recycler, state)
    }
}