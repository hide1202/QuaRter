@file:JvmName("CharSequences")

package io.viewpoint.quarter.extensions

import android.text.SpannableStringBuilder
import android.text.style.URLSpan
import android.webkit.URLUtil
import androidx.core.text.inSpans

fun CharSequence.clickableIfWebUrl(): CharSequence {
    val text = this.toString()
    if (!isWebUrl) {
        return text
    }

    val builder = SpannableStringBuilder()
    builder.inSpans(URLSpan(text)) {
        append(text)
    }
    return builder
}

val CharSequence?.isWebUrl: Boolean
    get() = this?.let {
        URLUtil.isHttpUrl(toString()) || URLUtil.isHttpsUrl(toString())
    } ?: false