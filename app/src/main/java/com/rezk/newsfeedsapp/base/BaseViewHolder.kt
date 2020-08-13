package com.rezk.newsfeedsapp.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<I>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindItem(item: I?, position: Int)

}