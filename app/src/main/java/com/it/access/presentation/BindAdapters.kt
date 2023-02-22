package com.it.access.presentation

import android.graphics.drawable.Drawable
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("srcUrl", "circleCrop", "placeholder", requireAll = false)
fun ImageView.bindSrcUrl(
    uri: String,
    circleCrop: Boolean,
    holder: Drawable?
) {
    this.load(uri) {
        crossfade(true)
        if (circleCrop)
            transformations(CircleCropTransformation())
        holder?.let {
            placeholder(holder)
            error(holder)
        }
    }
}

@BindingAdapter("pattern", "values", requireAll = true)
fun TextView.bindSrcUrl(
    pattern: String,
    vararg values: Any?,
) {
    values.let {
        this.text = pattern.format(*values)
    }
}

@BindingAdapter("vm", "type", requireAll = true)
fun CheckBox.onClickIt(
    vm: MyViewModel?,
    type: Types
) {
    setOnClickListener {
        vm?.let {
            vm.onCheckBoxClicked(isChecked, text.toString(), type)
        }
    }
}