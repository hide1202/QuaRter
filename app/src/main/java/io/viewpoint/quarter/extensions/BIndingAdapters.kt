package io.viewpoint.quarter.extensions

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("gone")
fun View.setGone(gone: Boolean?) {
    if (gone != null) {
        isGone = gone
    }
}

@BindingAdapter("layout_constraintGuide_begin")
fun Guideline.setGuideBegin(begin: Int?) {
    if (begin != null) {
        setGuidelineBegin(begin)
    }
}

@BindingAdapter("layout_constraintGuide_end")
fun Guideline.setGuideEnd(end: Int?) {
    if (end != null) {
        setGuidelineEnd(end)
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: CharSequence?) {
    if (url == null) {
        Glide.with(this)
            .clear(this)
    } else {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}