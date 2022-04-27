package com.siddharth.coolcustomviews

import android.graphics.Point
import android.graphics.RectF
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.coolcustomviews.customRV.MyLayoutManager
import com.siddharth.coolcustomviews.viewTreePlayer.ViewTreePlayer


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
    }

    private fun setupUi() {
        val thingsList = List(8) {
            10
        }

        val recyclerView: RecyclerView = findViewById(R.id.rv)
        recyclerView.adapter = ThingAdapter(thingsList)

        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        val screenWidth = size.x
        recyclerView.layoutManager = MyLayoutManager(resources, screenWidth)
    }

    inner class ThingAdapter(private val thingsList: List<Int>) : RecyclerView.Adapter<ThingHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingHolder {
            val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
            return ThingHolder(itemView)
        }

        override fun onBindViewHolder(holder: ThingHolder, position: Int) {}

        override fun getItemCount(): Int {
            return thingsList.size
        }
    }

    class ThingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}