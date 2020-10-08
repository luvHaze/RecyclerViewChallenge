package com.zoey.recyclerviewexample.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Diary
import com.zoey.recyclerviewexample.model.Feeling
import kotlinx.android.synthetic.main.activity_write_diary.*
import java.text.SimpleDateFormat
import java.util.*


const val PICK_IMAGE_GALLERY_CODE = 200

class WriteDiaryActivity : AppCompatActivity(), View.OnClickListener {

    private var imageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_diary)


        initActivity()
        if (intent.data != null) {
            val data = intent.getSerializableExtra("diary") as Diary
            loadData(data)
        }

    }

    private fun initActivity() {
        add_cover_imageview.setOnClickListener(this)
        backbutton_imageview.setOnClickListener(this)
        confirm_diary_textview.setOnClickListener(this)
    }

    private fun loadData(data: Diary) {
        write_body_edittext.setText(data.body)
        cover_circleimageview.setImageURI(data.cover?.toUri())
        write_title_edittext.setText(data.title)

        when (data.feeling_state) {
            Feeling.HAPPY -> {
                feel_happy_radiobutton.isChecked = true
            }
            Feeling.SOSO -> {
                feel_soso_radiobutton.isChecked = true
            }
            Feeling.SAD -> {
                feel_sad_radiobutton.isChecked = true
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.add_cover_imageview -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                startActivityForResult(intent, PICK_IMAGE_GALLERY_CODE)
            }

            R.id.backbutton_imageview -> {
                onBackPressed()
            }

            R.id.confirm_diary_textview -> {
                val diary = writeDiary()
                //val intent = Intent(this, MainActivity::class.java)
                val bundle = Bundle()

                bundle.putSerializable("hi", diary)

                intent.putExtra("ff",bundle)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    fun writeDiary(): Diary {

        val date = Date()
        val dateformat = SimpleDateFormat("EEE MMM dd").format(date).toString()

        val checkedState = radioGroup.checkedRadioButtonId
        lateinit var feelingState: Feeling

        when (checkedState) {
            R.id.feel_soso_radiobutton -> {
                feelingState = Feeling.SOSO
            }
            R.id.feel_happy_radiobutton -> {
                feelingState = Feeling.HAPPY
            }
            R.id.feel_sad_radiobutton -> {
                feelingState = Feeling.SAD
            }
        }

        return Diary(
            write_title_edittext.text.toString(),
            dateformat,
            feelingState,
            write_body_edittext.text.toString(),
            imageURI.toString()
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_GALLERY_CODE &&
            resultCode == Activity.RESULT_OK &&
            data != null &&
            data.data != null
        ) {
            imageURI = data.data!!
            cover_circleimageview.setImageURI(imageURI)
        }
    }


}