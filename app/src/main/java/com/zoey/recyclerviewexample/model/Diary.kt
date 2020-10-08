package com.zoey.recyclerviewexample.model

import android.net.Uri
import android.widget.ImageView
import java.io.Serializable
import java.util.*

data class Diary(
    var title: String,
    var date: String,
    var feeling_state: Feeling,
    var body: String,
    var cover: String?
) : Serializable