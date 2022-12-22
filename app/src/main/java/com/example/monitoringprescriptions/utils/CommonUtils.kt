package com.example.monitoringprescriptions.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

/**
 * экстеншен (расширение обычной чужой функции). Можно указать mutable расширение и оно вернет
 * версию MutableLiveData это сделано чтобы во фрагменте случайно не изменить список (в этом
 * рельной безописности нет)
 */
fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
    return this as MutableLiveData
}

// второй вариант
//val <T> LiveData<T>.mutable: MutableLiveData<T> get() = this as MutableLiveData<T>


/**
 * Два метода позволяющий сравнивать совпадающий день
 */
fun Calendar.dayIsEqual(otherCalendar: Calendar): Boolean {
    return get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR)
            && get(Calendar.DAY_OF_YEAR) == otherCalendar.get(Calendar.DAY_OF_YEAR)
}

fun Long.dayIsEqual(otherTimeMs: Long): Boolean {
    val firstCalendar = Calendar.getInstance()
    firstCalendar.timeInMillis = otherTimeMs
    val secondCalendar = Calendar.getInstance()
    secondCalendar.timeInMillis = this

    return firstCalendar.dayIsEqual(secondCalendar)
}