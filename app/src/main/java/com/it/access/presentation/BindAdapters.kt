package com.it.access.presentation

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.textfield.TextInputLayout
import com.it.access.domain.Params
import com.it.access.util.collectLatestLifecycleFlow
import kotlinx.coroutines.flow.collectLatest

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

@BindingAdapter("vm", "type")
fun CheckBox.onClickIt(
    vm: MyViewModel?,
    type: Types,
) {
    if (vm == null)
        return

    setOnClickListener {
        vm.onCheckBoxClicked(
            isChecked = isChecked,
            value = text.toString(),
            type = type
        )
    }
}

@BindingAdapter("vm", "type")
fun TextInputLayout.onTextChanged(
    vm: MyViewModel?,
    type: Types
) {
    if (vm == null)
        return

    editText?.doOnTextChanged click@ { mText, _, _, _ ->
        if (!vm.isCleaning.value) {
            val text = mText.toString()
            vm.onTextChanged(
                param = text,
                type = type
            )
        }
    }
}