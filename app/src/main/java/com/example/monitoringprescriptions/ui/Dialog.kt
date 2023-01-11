package com.example.monitoringprescriptions.ui

sealed class CloseDialog(val massage: String, val runnable: Runnable? = null) {

    // перечисляются все потомки класса
    class ShowDialog(massage: String) : CloseDialog(massage)
    class ShowCloseDialog(massage: String, runnable: Runnable? = null) :
        CloseDialog(massage, runnable)
}