package com.siddharth.coolcustomviews

import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.siddharth.coolcustomviews.viewTreePlayer.ViewTreePlayer


class MainActivity : AppCompatActivity() {

    private val viewList = ArrayList<RectF>()
    private var yOffset = 50f
    private val viewTreePlayer = ViewTreePlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rootViewGroup = findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup

        viewTreePlayer.mirrorViewTree(rootViewGroup)
    }
}