package com.example.monitoringprescriptions.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair
import com.example.monitoringprescriptions.domain.interactors.RecordsInteractor
import com.example.monitoringprescriptions.utils.mutable
import java.util.*

class RecordsViewModel(
    private val recordsInteractor: RecordsInteractor,
    private val currentCalendar: Calendar
) : ViewModel() {

    val selectedReceptionLiveData: LiveData<ReceptionRecordPair> = MutableLiveData()
    val loaderVisibilityLiveData: LiveData<Boolean> = MutableLiveData()

    // сообщаем что данные изменились
    val receptionRecordPair: LiveData<List<ReceptionRecordPair>> = MutableLiveData()

    fun onReceptionClick(receptionRecordPair: ReceptionRecordPair) {
        (selectedReceptionLiveData as MutableLiveData).value = receptionRecordPair
    }

    private fun loadData() {
        loaderVisibilityLiveData.mutable().postValue(true)
        recordsInteractor.getRecordsForDay(currentCalendar) {
            loaderVisibilityLiveData.mutable().postValue(false)
            receptionRecordPair.mutable().postValue(it)
        }
    }

    init {
        loadData()
    }

}