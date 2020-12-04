package io.viewpoint.quarter.extensions

import android.view.View
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter

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