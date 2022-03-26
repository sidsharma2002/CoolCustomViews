package com.siddharth.coolcustomviews.viewTreePlayer

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup

class ViewTreePlayer {

    private fun mirrorChildNodes(view: View) {
        if (view !is ViewGroup || view.childCount == 0) return    // base condition
        val viewList = ArrayList<View>()
        for (i in 0 until view.childCount) {
            viewList.add(view.getChildAt(i))
        }

        view.animate().alpha(0f).setDuration(600).start()
        Handler(Looper.getMainLooper()).postDelayed({
            view.removeAllViews()
            viewList.reverse() // mirror
            view.animate().alpha(1f).setDuration(0).start()

            viewList.forEach {
                loopAndAddViews(view, it)
                mirrorChildNodes(it)
            }
        }, 600L)
    }

    private fun loopAndAddViews(view: ViewGroup, it: View) {
        Handler(Looper.getMainLooper()).postDelayed({
            view.addView(it)
            it.alpha = 0f
            it.animate().alpha(1f).setDuration(600).start()
        }, 600L)
    }

    fun mirrorViewTree(root: ViewGroup) {
        mirrorChildNodes(root)
    }
}