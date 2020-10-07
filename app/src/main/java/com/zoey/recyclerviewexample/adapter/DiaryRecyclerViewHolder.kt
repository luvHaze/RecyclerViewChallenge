package com.zoey.recyclerviewexample.adapter

import android.graphics.BlendMode
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Diary
import de.hdodenhof.circleimageview.CircleImageView

class DiaryRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var swipeRevealLayout = itemView.findViewById<SwipeRevealLayout>(R.id.item_swipelayout)
    private var tvDay = itemView.findViewById<TextView>(R.id.item_day_textview)
    private var tvMonth = itemView.findViewById<TextView>(R.id.item_month_textview)
    private var tvWeekDay = itemView.findViewById<TextView>(R.id.item_weekday_textview)
    private var imgCover = itemView.findViewById<ImageView>(R.id.item_cover_imageview)
    private var imgFeeling = itemView.findViewById<ImageView>(R.id.item_feeling_state_imageview)

    fun onBind(data: Diary) {


    }
}