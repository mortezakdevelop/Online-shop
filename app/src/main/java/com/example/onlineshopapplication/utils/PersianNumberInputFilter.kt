package com.example.onlineshopapplication.utils

import android.text.InputFilter
import android.text.Spanned

class PersianNumberInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val persianNumbers = arrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
        val output = StringBuilder()

        var hasChanged = false  // بررسی اینکه آیا تغییری در متن ایجاد شده یا نه

        source?.forEach { char ->
            if (char in '0'..'9') {
                output.append(persianNumbers[char - '0'])  // تبدیل عدد انگلیسی به فارسی
                hasChanged = true
            } else {
                output.append(char)  // اگر غیر از عدد بود، بدون تغییر اضافه شود
            }
        }

        return if (hasChanged) output.toString() else null  // مقدار `null` یعنی تغییری ایجاد نشده
    }
}