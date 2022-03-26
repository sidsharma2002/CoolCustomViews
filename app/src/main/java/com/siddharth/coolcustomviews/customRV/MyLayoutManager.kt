package com.siddharth.coolcustomviews.customRV

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.coolcustomviews.R
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class MyLayoutManager(resources: Resources, private val screenWidth: Int) : RecyclerView.LayoutManager() {

    private val TAG = "LayoutManager"
    private var horizontalOffset = 0
    private val viewWidth = resources.getDimensionPixelSize(R.dimen.item_width)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler != null && state != null)
            fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val firstVisiblePosition = floor(horizontalOffset.toDouble() / viewWidth.toDouble()).toInt()
        val lastVisiblePosition = ((horizontalOffset + screenWidth) / viewWidth).toInt()

        for (i in firstVisiblePosition..lastVisiblePosition) {
            Log.d(TAG, "33 $firstVisiblePosition $lastVisiblePosition")
            var recyclerIndex = i % itemCount
            if (recyclerIndex < 0) {
                recyclerIndex += itemCount
            }
            val view = recycler.getViewForPosition(recyclerIndex)
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

        Log.d(TAG, "top offset ${getTopOffsetForView(left + viewWidth / 2)}")
        Log.d(TAG, "$left $right $top $bottom")

        measureChild(view, viewWidth, viewWidth)
        layoutDecorated(view, left, top, right, bottom)
    }

    private fun getTopOffsetForView(centerXCoordinate: Int): Int {
        val sinValue = (60 * (tan(centerXCoordinate.toDouble() / 60))).toInt()
        return sinValue + screenWidth / 2
    }

    override fun canScrollHorizontally(): Boolean = true
    override fun canScrollVertically(): Boolean = false

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        horizontalOffset += dx
        if (recycler != null && state != null)
            fill(recycler, state)
        return dx
    }
}