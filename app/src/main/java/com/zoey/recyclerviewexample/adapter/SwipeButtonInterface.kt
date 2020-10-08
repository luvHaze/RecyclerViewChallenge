package com.zoey.recyclerviewexample.adapter

import com.zoey.recyclerviewexample.model.Diary

interface SwipeButtonInterface {
    fun onClickEditButton(diaryData: Diary, position: Int)
    fun onClickRemoveButton(position: Int)
}