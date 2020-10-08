package com.zoey.recyclerviewexample.adapter

import com.zoey.recyclerviewexample.model.Diary
import java.io.Serializable

interface ItemEditListener {
    fun onEditFinish(diaryData: Diary, position: Int)
}