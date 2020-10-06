package com.zoey.recyclerviewexample.adapter

interface ItemTouchListener {
    fun onItemMove(from_position: Int, to_position: Int) : Boolean
    fun onItemSwipe(position: Int)
}