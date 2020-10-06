package com.zoey.recyclerviewexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.adapter.ItemTouchHelperCallback
import com.zoey.recyclerviewexample.adapter.ItemTouchListener
import com.zoey.recyclerviewexample.adapter.UserRecyclerViewAdapter
import com.zoey.recyclerviewexample.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var rvAdapter: UserRecyclerViewAdapter? = null
    private var dummyData: ArrayList<User> = ArrayList<User>()
    private var itemTouchHelper: ItemTouchHelper? = null
    private var itemTouchHelperCallback: ItemTouchHelperCallback? = null
    private var swipeState: Boolean = false
    private var dragState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 1..20) {
            dummyData.add(
                User(
                    "철수$i",
                    "좆같다$i",
                    "씨"
                )
            )
        }
        initRecyclerview()
        rvAdapter?.submitUserList(dummyData)

        swipe_switch.setOnClickListener {
            itemTouchHelperCallback?.swipeState = !itemTouchHelperCallback?.swipeState!!
        }
        drag_switch.setOnClickListener {
            itemTouchHelperCallback?.dragState = !itemTouchHelperCallback?.dragState!!
        }

    }

    fun initRecyclerview() {
        rvAdapter = UserRecyclerViewAdapter()
        user_recyclerview.adapter = rvAdapter
        user_recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 리사이클러뷰업 ItemTouchHelper 붙이는 작업
        itemTouchHelperCallback = ItemTouchHelperCallback(rvAdapter!!, swipeState, dragState)
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback!!)
        itemTouchHelper!!.attachToRecyclerView(user_recyclerview);

    }
}