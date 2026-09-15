package com.ute.studentprofile.utils

import android.content.Context
import android.widget.Toast

// Extension xếp loại học lực
fun Double.toAcademicRanking(): String = when {
    this >= 3.6 -> "Xuất sắc!!"
    this >= 3.2 -> "Giỏi"
    this >= 2.5 -> "Khá"
    else -> "Trung bình"
}

// Extension hiển thị Toast cho Context
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
