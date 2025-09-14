package com.dani.ftprcar.ui

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.dani.ftprcar.R

fun ImageView.loadUrl(imageUrl: String) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.ic_downloading)
        .error(R.drawable.ic_error)
        .transform(CircleTransform())
        .into(this)
}