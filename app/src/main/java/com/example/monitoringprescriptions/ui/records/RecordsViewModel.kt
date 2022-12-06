package com.example.monitoringprescriptions.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair

class RecordsViewModel(

) : ViewModel() {

    val selectedReceptionLiveData: LiveData<ReceptionRecordPair> = MutableLiveData()

    fun onReceptionClick(receptionRecordPair: ReceptionRecordPair) {
        (selectedReceptionLiveData as MutableLiveData).value = receptionRecordPair
    }
}