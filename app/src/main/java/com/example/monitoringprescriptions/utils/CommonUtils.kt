package com.example.monitoringprescriptions.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.App
import java.util.*

fun Calendar.toUserString(): String {
    return "${
        get(Calendar.DAY_OF_MONTH)
    } ${
        get(Calendar.MONDAY)
    } ${
        get(Calendar.YEAR)
    }"
}

// экстеншен, get() постоянный
val Context.app get() = applicationContext as App


val Fragment.app get() = requireContext().app

