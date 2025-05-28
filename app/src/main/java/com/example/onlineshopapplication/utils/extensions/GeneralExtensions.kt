package com.example.onlineshopapplication.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.view.isVisible
import coil.load
import coil.request.CachePolicy
import com.example.onlineshopapplication.R
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat


fun View.hideKeyboard() {
    val inputMethod = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethod.hideSoftInputFromWindow(windowToken, 0)
}

//-1 for short show message
// 0 for long show message
fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun ImageView.loadImage(url: String) {
    this.load(url) {
        crossfade(true)
        crossfade(500)
        diskCachePolicy(CachePolicy.ENABLED)
        error(R.drawable.placeholder)
    }
}

fun View.isVisible(isShowLoading: Boolean, container: View) {
    if (isShowLoading) {
        this.isVisible = true
        container.isVisible = false
    } else {
        this.isVisible = false
        container.isVisible = true
    }
}

fun Int.moneySeparating():String{
    return "${DecimalFormat("#,###.##").format(this)}تومان"
}