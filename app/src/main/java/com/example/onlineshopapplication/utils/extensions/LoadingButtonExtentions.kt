package com.example.onlineshopapplication.utils.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import com.example.onlineshopapplication.R
import com.google.android.material.button.MaterialButton

private fun drawableProgress(context: Context): Drawable? {

    val value = TypedValue()
    context.theme.resolveAttribute(android.R.attr.progressBarStyleSmall, value, false)
    val progressStyle = value.data
    val attrs = intArrayOf(android.R.attr.indeterminateDrawable)
    val typeArray: TypedArray = context.obtainStyledAttributes(progressStyle, attrs)
    val drawable = typeArray.getDrawable(0)
    typeArray.recycle()
    return drawable
}

fun MaterialButton.enableLoading(isLoading:Boolean){
    maxLines = 1
    iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
    val originalText = getTag(R.id.btnInputWithMobilePhone) as? String ?: text.toString()

    if (isLoading){

        var drawable = icon
        if (drawable !is Animatable){
            drawable = drawableProgress(context)
            icon = drawable
        }
        (drawable as Animatable).start()
        setTag(R.id.btnInputWithMobilePhone, originalText)
        text = ""
        alpha = 0.5f
    }else{
        text = originalText
        icon = null
        alpha = 1.0f
    }
}