package com.zoey.recyclerviewexample.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Diary
import com.zoey.recyclerviewexample.util.setFeeling
import kotlinx.android.synthetic.main.activity_read_diary.*

class ReadDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_diary)

        if(intent.extras != null) {
            val extra = intent.extras!!.get("data") as Bundle
            val date = extra.getSerializable("diaryData") as Diary

            initActivity(date)
        }
    }

    private fun initActivity(diaryData: Diary) {
        read_date_textview.text = diaryData.date
        read_title_textview.text = diaryData.title
        read_body_textview.text = diaryData.body

        val imageURI = Uri.parse(diaryData.cover)
        Glide.with(this)
            .load(imageURI)
            .override(500,500)
            .placeholder(R.drawable.image_placeholder)
            .into(read_cover_imageview)

        read_feeling_imageview.setFeeling(diaryData.feeling_state)

    }


}