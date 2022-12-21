package com.example.monitoringprescriptions.ui.appointments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.data.room.HistoryAppointmentFullEntity
import com.example.monitoringprescriptions.data.room.HistoryAppointmentFullLocalRepo
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.utils.mutable
import java.util.*

class AppointmentsViewModel(
    private val historyAppointmentFullLocalRepo: HistoryAppointmentFullLocalRepo,
    private val currentCalendar: Calendar
) : ViewModel() {

    // подписались на listener (в случае изменения, перегружаем все данные)
    private val onAppointmentListener = {
        loadData()
    }

    //    val selectedReceptionLiveData: LiveData<ReceptionRecordPair> = MutableLiveData()
    val loaderVisibilityLiveData: LiveData<Boolean> = MutableLiveData()

    // сообщаем что данные изменились
    val appointmentsLiveData: LiveData<List<HistoryAppointmentFullEntity>> = MutableLiveData()

//    fun onReceptionClick(receptionRecordPair: ReceptionRecordPair) {
//        (selectedReceptionLiveData as MutableLiveData).value = receptionRecordPair
//    }

    //получаем при нажатии статус исполнения приема лекарств
    fun onAppointmentSelected(
        appointmentId: String,
        appointmentStatus: AppointmentStatus
    ) {
        historyAppointmentFullLocalRepo.changeStatus(appointmentId, appointmentStatus)
    }

    private fun loadData() {
        loaderVisibilityLiveData.mutable().postValue(true)

        historyAppointmentFullLocalRepo.getAllHistory(currentCalendar) {
            loaderVisibilityLiveData.mutable().postValue(false)
            appointmentsLiveData.mutable().postValue(it)
        }
    }

    init {
        loadData()
        // подписываемся на статус приема лекарств
        historyAppointmentFullLocalRepo.subscribe(onAppointmentListener)
    }

    override fun onCleared() {
        super.onCleared()
        // отписываемся от приема лекарств
        historyAppointmentFullLocalRepo.unsubscribe(onAppointmentListener)
    }

}