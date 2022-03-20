package com.siddharth.coolcustomviews.viewTreePlayer

import android.view.View
import android.view.ViewGroup

class ViewTreePlayer {

    private fun mirrorChildNodes(view: View) {
        if (view !is ViewGroup || view.childCount == 0) return    // base condition
        val viewList = ArrayList<View>()
        for (i in 0 until view.childCount) {
            viewList.add(view.getChildAt(i))
        }

        view.removeAllViews()
        viewList.reverse() // mirror

        for (i in 0 until viewList.size) {
            view.addView(viewList[i])
            mirrorChildNodes(viewList[i])
        }
    }

    fun mirrorViewTree(root: ViewGroup) {
        mirrorChildNodes(root)
    }
}