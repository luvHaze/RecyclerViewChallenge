package com.zoey.recyclerviewexample.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.Diary
import com.zoey.recyclerviewexample.model.EDIT_DIARY_CODE
import com.zoey.recyclerviewexample.ui.WriteDiaryActivity

class DiaryRecyclerViewAdapter(private var context: Context) : RecyclerView.Adapter<DiaryRecyclerViewHolder>(),
    SwipeButtonInterface, ItemTouchListener, ItemEditListener {

    private var userList = ArrayList<Diary>()
    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return DiaryRecyclerViewHolder(view, parent.context, this)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: DiaryRecyclerViewHolder, position: Int) {
        holder.onBind(userList[position])
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipeRevealLayout, "swipeLayout")
        viewBinderHelper.closeLayout("swipeLayout")
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

    override fun onClickEditButton(diaryData: Diary, position: Int) {
        Log.d("SwipeButton", "$position 수정 버튼 클릭됨")
        val intent = Intent(context, WriteDiaryActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("diary", diaryData)
        bundle.putInt("position", position)
        intent.putExtra("loadData", bundle)

        (context as Activity).startActivityForResult(intent, EDIT_DIARY_CODE)

    }

    override fun onClickRemoveButton(position: Int) {
        Log.d("SwipeButton", "$position 삭 버튼 클릭됨")
        userList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onEditFinish(diaryData: Diary, position: Int) {
        Log.d("[Edited Data is ]", "[$position], Data : $diaryData")
        userList[position] = diaryData
        notifyItemChanged(position)
    }


}