package com.zoey.recyclerviewexample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.adapter.ItemTouchHelperCallback
import com.zoey.recyclerviewexample.adapter.DiaryRecyclerViewAdapter
import com.zoey.recyclerviewexample.model.Diary
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var rvAdapter: DiaryRecyclerViewAdapter? = null
    private var dummyData: ArrayList<Diary> = ArrayList<Diary>()
    private var itemTouchHelper: ItemTouchHelper? = null
    private var itemTouchHelperCallback: ItemTouchHelperCallback? = null
    private var swipeState: Boolean = false
    private var dragState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActivity()
        initRecyclerview()

    }

    private fun initActivity() {
        recyclerview_sort_button.setOnClickListener(this)
        write_diary_fab.setOnClickListener(this)
    }

    private fun initRecyclerview() {
        rvAdapter = DiaryRecyclerViewAdapter()
        user_recyclerview.adapter = rvAdapter
        user_recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 리사이클러뷰업 ItemTouchHelper 붙이는 작업
        itemTouchHelperCallback = ItemTouchHelperCallback(rvAdapter!!, swipeState, dragState)
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback!!)
        itemTouchHelper!!.attachToRecyclerView(user_recyclerview);
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
                startActivity(intent)
            }
        }

    }
}