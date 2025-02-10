package com.example.onlineshopapplication.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar


fun View.hideKeyboard(){
    val inputMethod = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethod.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnackBar(message:String,duration:Int){
    Snackbar.make(this,message,duration).show()
}