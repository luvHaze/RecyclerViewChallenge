package com.zoey.recyclerviewexample.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.ColorFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Diary
import com.zoey.recyclerviewexample.model.Feeling
import com.zoey.recyclerviewexample.ui.WriteDiaryActivity
import de.hdodenhof.circleimageview.CircleImageView

class DiaryRecyclerViewHolder(
    itemView: View,
    private var context: Context,
    private var swipeButtonListener: SwipeButtonInterface
) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var diaryData: Diary

    var swipeRevealLayout = itemView.findViewById<SwipeRevealLayout>(R.id.item_swipelayout)
    private var tvDay = itemView.findViewById<TextView>(R.id.item_day_textview)
    private var tvMonth = itemView.findViewById<TextView>(R.id.item_month_textview)
    private var tvWeekDay = itemView.findViewById<TextView>(R.id.item_weekday_textview)
    private var imgCover = itemView.findViewById<ImageView>(R.id.item_cover_imageview)
    private var imgFeeling = itemView.findViewById<ImageView>(R.id.item_feeling_state_imageview)
    private var btnEdit = itemView.findViewById<ImageView>(R.id.item_edit_button)
    private var btnRemove = itemView.findViewById<ImageView>(R.id.item_remove_button)


    fun onBind(data: Diary) {
        diaryData = data

        val day = data.date.substring(8..9)
        val month = data.date.substring(4..6)
        val weekDay = data.date.substring(0..2)
        val imageUri = Uri.parse(data.cover)
        val feelingState = data.feeling_state

        tvDay.text = day
        tvMonth.text = month
        tvWeekDay.text = weekDay
        imgCover.setImageURI(imageUri)

        when (feelingState) {
            Feeling.HAPPY -> {
                imgFeeling.setColorFilter(context.getColor(R.color.feelHappy))
            }
            Feeling.SOSO -> {
                imgFeeling.setColorFilter(context.getColor(R.color.feelSoso))
            }
            Feeling.SAD -> {
                imgFeeling.setColorFilter(context.getColor(R.color.feelSad))
            }
        }
        imgCover.setOnClickListener(this)
        btnEdit.setOnClickListener(this)
        btnRemove.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.item_cover_imageview -> {
                Log.d("position: ", "$adapterPosition")
            }
            R.id.item_remove_button -> {
                swipeButtonListener.onClickRemoveButton(adapterPosition)
            }
            R.id.item_edit_button -> {
                Log.d("Clicked", "editBUtton")
                swipeButtonListener.onClickEditButton(diaryData,adapterPosition)
            }
        }
    }

}