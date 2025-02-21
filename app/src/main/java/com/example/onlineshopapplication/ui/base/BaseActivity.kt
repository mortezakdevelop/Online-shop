package com.example.onlineshopapplication.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper

open class BaseActivity:AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })

    }
}