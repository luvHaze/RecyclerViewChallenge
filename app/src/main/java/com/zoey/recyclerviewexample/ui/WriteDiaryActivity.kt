package com.zoey.recyclerviewexample.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.adapter.ItemEditListener
import com.zoey.recyclerviewexample.model.Diary
import com.zoey.recyclerviewexample.model.Feeling
import com.zoey.recyclerviewexample.util.PICK_IMAGE_GALLERY_CODE
import kotlinx.android.synthetic.main.activity_write_diary.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class WriteDiaryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editListener: ItemEditListener

    private var imageUri: Uri? = null
    private var EDIT_MODE: Boolean = false
    private var EDIT_POSITION: Int = 0
    private val PERMISSION_LIST =
        arrayListOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    private val READ_EXTERNAL_PEMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_diary)

        initActivity()

        // 수정부분인지 파악한다.
        // loadData가 존재하면 수정기능을 실행해야 하기 때문
        val bundle: Bundle? = intent.extras?.getBundle("loadData")
        if (bundle != null) {

            Log.d("* load *", "[불러오기 데이터 있음]")
            val loadedData = bundle.getSerializable("diary") as Diary
            val position = bundle.getInt("position")

            loadData(loadedData)
            EDIT_MODE = true
            EDIT_POSITION = position

        } else {
            EDIT_MODE = false
            EDIT_POSITION = -1
        }

    }

    private fun initActivity() {
        add_cover_imageview.setOnClickListener(this)
        backbutton_imageview.setOnClickListener(this)
        confirm_diary_textview.setOnClickListener(this)
    }

    private fun loadData(data: Diary) {

        val uri = Uri.parse(data.cover)
        write_body_edittext.setText(data.body)
        Glide.with(this).load(uri).into(cover_circleimageview)
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
                var isPermissionGranted =
                    checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                if (isPermissionGranted == PackageManager.PERMISSION_GRANTED) {
                    loadPicture()
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        PERMISSION_LIST.toTypedArray(),
                        READ_EXTERNAL_PEMISSION_CODE
                    )
                }
            }

            R.id.backbutton_imageview -> {
                onBackPressed()
            }

            R.id.confirm_diary_textview -> {
                when (EDIT_MODE) {
                    true -> {
                        val diary = writeDiary()
                        val position = EDIT_POSITION
                        val bundle = Bundle()

                        bundle.putSerializable("diary", diary)
                        bundle.putInt("position", position)

                        intent.putExtra("bundleData", bundle)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                    false -> {
                        // 작성모드일때는 그냥 추가해줌
                        val diary = writeDiary()
                        val bundle = Bundle()

                        bundle.putSerializable("diary", diary)

                        intent.putExtra("bundleData", bundle)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }

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
            imageUri.toString()
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_IMAGE_GALLERY_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        val dataUri = data.data
                        Glide.with(this).load(dataUri).into(cover_circleimageview)
                        imageUri = dataUri
                    } catch (e: Exception) {
                        Toast.makeText(this, "사진 불러오기 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            READ_EXTERNAL_PEMISSION_CODE -> {
                var isPermissionAllGranted = true
                grantResults.forEach {
                    if (it != PackageManager.PERMISSION_GRANTED) isPermissionAllGranted = false
                }
                if (isPermissionAllGranted) {
                    loadPicture()
                }
            }
        }
    }

    private fun loadPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        startActivityForResult(intent, PICK_IMAGE_GALLERY_CODE)
    }


}