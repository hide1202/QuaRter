package io.viewpoint.quarter.extensions

import android.view.View
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter

@BindingAdapter("gone")
fun View.setGone(gone: Boolean?) {
    if (gone != null) {
        isGone = gone
    }
}