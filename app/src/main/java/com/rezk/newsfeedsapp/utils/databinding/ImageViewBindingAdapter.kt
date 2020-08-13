package com.rezk.newsfeedsapp.utils.databinding

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rezk.newsfeedsapp.R

@BindingAdapter(value = ["imageToUrl"], requireAll = false)
fun setImageSource(imageView: ImageView, imageToUrl: String?) {
    imageToUrl?.let { url ->
        Glide.with(imageView.context)
            .load(url)
            .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.placeholder))
            .error(R.drawable.placeholder)
            .into(imageView)
    }
}