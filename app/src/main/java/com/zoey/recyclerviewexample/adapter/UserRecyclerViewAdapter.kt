package com.zoey.recyclerviewexample.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.User

class UserRecyclerViewAdapter: RecyclerView.Adapter<UserRecyclerViewHolder>(), ItemTouchListener {

    private var userList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_userlist, parent, false)
        return UserRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserRecyclerViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    fun submitUserList(list: ArrayList<User>) {
        this.userList = list
    }

    override fun onItemMove(from_position: Int, to_position: Int): Boolean {
        var user = userList[from_position]
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

}