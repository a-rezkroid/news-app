package com.rezk.newsfeedsapp.utils.databinding

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rezk.newsfeedsapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter(value = ["dateText"])
fun setDateText(textView: TextView, dateText: String?) {

    dateText?.let { date ->
        try {
            val df1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH)
            val df2 = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)
            val calender = Calendar.getInstance()
            calender.time = df1.parse(date)
            textView.text = df2.format(calender.time)
        } catch (e: ParseException) {
            e.printStackTrace()
            textView.text = date

        }
    }


}

@BindingAdapter(value = ["author"])
fun setByBeforeAuthor(textView: TextView, author: String?) {
    author?.let {
        textView.text = "By ${author}"
    }

}