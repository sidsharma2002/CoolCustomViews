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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.coolcustomviews.customRV.MyLayoutManager
import com.siddharth.coolcustomviews.graphAnimator.GraphView
import com.siddharth.coolcustomviews.viewTreePlayer.ViewTreePlayer
import org.w3c.dom.Text
import kotlin.math.sin
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRvUi()
        setupGraphAnimUi()
    }

    private fun setupRvUi() {
        val thingsList = List(10) {
            0
        }

        val recyclerView: RecyclerView = findViewById(R.id.rv)
        recyclerView.adapter = ThingAdapter(thingsList)

        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        val screenWidth = size.x
        recyclerView.layoutManager = object : MyLayoutManager(resources, screenWidth) {
            override fun getTopOffsetForView(centerXCoordinate: Int): Int {
                val sinValue = (60 * (sin(centerXCoordinate.toDouble() / 60))).toInt()
                return sinValue + screenWidth / 2
            }
        }
    }

    private fun setupGraphAnimUi() {
        val graphView = object : GraphView(this, null) {
            override fun getY(x: Float): Float {
                return (sin((x / 50)) * 200 + 700)
            }
        }
        val parentView = findViewById<LinearLayout>(R.id.parent_layout)
        parentView.addView(
            graphView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
    }

    inner class ThingAdapter(private val thingsList: List<Int>) :
        RecyclerView.Adapter<ThingHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingHolder {
            val itemView: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
            return ThingHolder(itemView)
        }

        override fun onBindViewHolder(holder: ThingHolder, position: Int) {
            holder.setData((holder.adapterPosition + 1).toString())
        }

        override fun getItemCount(): Int {
            return thingsList.size
        }
    }

    class ThingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.tv_circle)
        fun setData(text: String) {
            textView.text = text
        }
    }
}