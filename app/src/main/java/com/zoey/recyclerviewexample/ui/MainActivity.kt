package com.zoey.recyclerviewexample.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.adapter.ItemTouchHelperCallback
import com.zoey.recyclerviewexample.adapter.DiaryRecyclerViewAdapter
import com.zoey.recyclerviewexample.adapter.ItemEditListener
import com.zoey.recyclerviewexample.model.Diary
import com.zoey.recyclerviewexample.util.EDIT_DIARY_CODE
import com.zoey.recyclerviewexample.util.PERMISSION_LIST
import com.zoey.recyclerviewexample.util.WRITE_DIARY_CODE
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var rvAdapter: DiaryRecyclerViewAdapter? = null
    private var diaryData: ArrayList<Diary> = ArrayList<Diary>()
    private var itemTouchHelper: ItemTouchHelper? = null
    private var itemTouchHelperCallback: ItemTouchHelperCallback? = null
    private var swipeState: Boolean = false
    private var dragState: Boolean = false

    private lateinit var test: ItemEditListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionCheck()
        initActivity()
        initRecyclerview()

        rvAdapter?.submitUserList(diaryData)

    }

    private fun initActivity() {
        val today = SimpleDateFormat("yyyy. MM. dd", Locale.US).format(Date())

        main_date_textview.text = today
        recyclerview_sort_button.setOnClickListener(this)
        write_diary_fab.setOnClickListener(this)
    }

    private fun initRecyclerview() {
        // 리사이클러뷰 작업
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.isItemPrefetchEnabled = true
        rvAdapter = DiaryRecyclerViewAdapter(this)
        user_recyclerview.adapter = rvAdapter
        user_recyclerview.layoutManager = layoutManager
        user_recyclerview.setHasFixedSize(true)
        user_recyclerview.setItemViewCacheSize(20)

        // 리사이클러뷰업 ItemTouchHelper 붙이는 작업 (드래그 작업 위함)
        itemTouchHelperCallback = ItemTouchHelperCallback(rvAdapter!!, swipeState, dragState)
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback!!)
        itemTouchHelper!!.attachToRecyclerView(user_recyclerview);
    }

    fun permissionCheck() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
             Toast.makeText(this, "이미 권한 있음", Toast.LENGTH_LONG).show()
        } else {
            requestPermissions(PERMISSION_LIST.toTypedArray(), 1001)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.recyclerview_sort_button -> {
                if (recyclerview_sort_button.text == "정렬") {
                    recyclerview_sort_button.text = "완료"
                    itemTouchHelperCallback?.dragState = true
                } else {
                    recyclerview_sort_button.text = "정렬"
                    itemTouchHelperCallback?.dragState = false
                }
            }

            R.id.write_diary_fab -> {
                val intent = Intent(this@MainActivity, WriteDiaryActivity::class.java)
                startActivityForResult(intent, WRITE_DIARY_CODE)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult", "$requestCode, $resultCode")
        when (requestCode) {

            // 작성하고 나서 결과값이 들어올 때
            WRITE_DIARY_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val bundle = data?.getBundleExtra("bundleData")
                    val diary = bundle?.getSerializable("diary") as Diary
                    Log.d("Data : ", "$diary")
                    diaryData.add(diary)
                    rvAdapter?.notifyDataSetChanged()
                }
            }
            // 수정하고 나서 결과값이 들어올 때
            EDIT_DIARY_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val bundle = data?.getBundleExtra("bundleData")
                    val diary = bundle?.getSerializable("diary") as Diary
                    val position = bundle.getInt("position")

                    Log.d("Edit Data : ", "$diary")
                    rvAdapter?.onEditFinish(diary, position)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1001) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "퍼미션 허가됨", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
