package com.example.monitoringprescriptions.utils

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