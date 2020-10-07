package com.zoey.recyclerviewexample.adapter

import android.graphics.Canvas
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class ItemTouchHelperCallback(
    private var listener: ItemTouchListener,
    var swipeState: Boolean,
    var dragState: Boolean
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        val drag_flags = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)
        val swipe_flags = ItemTouchHelper.START

        return makeMovementFlags(drag_flags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return listener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemSwipe(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return dragState
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return swipeState
    }

}