package com.zoey.recyclerviewexample.util

import android.widget.ImageView
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Feeling
import kotlinx.android.synthetic.main.item_diary.*

fun String.getDay() : String {
    return this.substring(8..9)
}

fun String.getMonth() : String {
    return this.substring(4..6)
}

fun String.getWeekday() : String {
    return this.substring(0..2)
}

fun ImageView.setFeeling(feeling: Feeling) {
    when (feeling) {
        Feeling.HAPPY -> {
            this.setColorFilter(context.getColor(R.color.feelHappy))
        }
        Feeling.SOSO -> {
            this.setColorFilter(context.getColor(R.color.feelSoso))
        }
        Feeling.SAD -> {
            this.setColorFilter(context.getColor(R.color.feelSad))
        }
    }
}