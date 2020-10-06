package com.zoey.recyclerviewexample.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zoey.recyclerviewexample.R
import com.zoey.recyclerviewexample.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tvName = itemView.findViewById<TextView>(R.id.item_name_textview)
    private var tvStatus = itemView.findViewById<TextView>(R.id.item_status_textview)
    private var imgProfile = itemView.findViewById<CircleImageView>(R.id.item_profile_imageview)

    fun onBind(data: User) {
        tvName.text = data.name
        tvStatus.text = data.status
    }

}