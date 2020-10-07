package com.zoey.recyclerviewexample.model

import android.widget.ImageView

data class Diary(
    var title: String,
    var date: String,
    var feeling_state: Feeling,
    var body: String,
    var cover: String
) {
    enum class Feeling {
        GOOD, NOT_BAD, BAD
    }
}