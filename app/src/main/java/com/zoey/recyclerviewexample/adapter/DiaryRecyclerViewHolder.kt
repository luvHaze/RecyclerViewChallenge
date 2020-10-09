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
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Diary
import com.zoey.recyclerviewexample.model.Feeling
import com.zoey.recyclerviewexample.ui.ReadDiaryActivity
import com.zoey.recyclerviewexample.ui.WriteDiaryActivity
import com.zoey.recyclerviewexample.util.getDay
import com.zoey.recyclerviewexample.util.getMonth
import com.zoey.recyclerviewexample.util.getWeekday
import com.zoey.recyclerviewexample.util.setFeeling
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_diary.*
import kotlinx.android.synthetic.main.item_swipe_button_panel.*

class DiaryRecyclerViewHolder(
    itemView: View,
    private var context: Context,
    private var swipeButtonListener: SwipeButtonInterface
) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener, LayoutContainer {

    private lateinit var diaryData: Diary
    var swipeRevealLayout = itemView.findViewById<SwipeRevealLayout>(R.id.item_swipelayout)

    override val containerView: View?
        get() = itemView

    fun onBind(data: Diary) {
        diaryData = data

        val day = data.date.getDay()
        val month = data.date.getMonth()
        val weekDay = data.date.getWeekday()
        val imageUri = Uri.parse(data.cover)
        val feelingState = data.feeling_state

        item_day_textview.text = day
        item_month_textview.text = month
        item_weekday_textview.text = weekDay

        Glide.with(context)
            .load(imageUri)
            .override(500,500)
            .placeholder(R.drawable.image_placeholder)
            .into(item_cover_imageview)

        item_feeling_state_imageview.setFeeling(feelingState)

        item_edit_button.setOnClickListener(this)
        item_remove_button.setOnClickListener(this)
        item_cover_imageview.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.item_remove_button -> {
                Log.d("Clicked", "DeleteButton")
                swipeButtonListener.onClickRemoveButton(adapterPosition)
            }
            R.id.item_edit_button -> {
                Log.d("Clicked", "EditButton")
                swipeButtonListener.onClickEditButton(diaryData,adapterPosition)
            }

            R.id.item_cover_imageview -> {
                val bundle = Bundle()
                bundle.putSerializable("diaryData", diaryData)
                val intent = Intent(context, ReadDiaryActivity::class.java)
                intent.putExtra("data", bundle)

                context.startActivity(intent)
            }
        }
    }



}