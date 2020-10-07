package com.zoey.recyclerviewexample.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Diary

class DiaryRecyclerViewAdapter : RecyclerView.Adapter<DiaryRecyclerViewHolder>(),
    SwipeButtonInterface, ItemTouchListener {

    private var userList = ArrayList<Diary>()
    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return DiaryRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: DiaryRecyclerViewHolder, position: Int) {
        holder.onBind(userList[position])
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipeRevealLayout, "삭제")
    }

    fun submitUserList(list: ArrayList<Diary>) {
        this.userList = list
    }

    override fun onItemMove(from_position: Int, to_position: Int): Boolean {
        val user = userList[from_position]
        userList.removeAt(from_position)
        userList.add(to_position, user)
        notifyItemMoved(from_position, to_position)

        return true
    }

    override fun onItemSwipe(position: Int) {
        Log.d("TAG", "Swipe")
        userList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onClickEditButton(position: Int) {
        Log.d("SwipeButton", "$position 수정 버튼 클릭됨")
    }

    override fun onClickRemoveButton(position: Int) {
        Log.d("SwipeButton", "$position 삭 버튼 클릭됨")
    }


}