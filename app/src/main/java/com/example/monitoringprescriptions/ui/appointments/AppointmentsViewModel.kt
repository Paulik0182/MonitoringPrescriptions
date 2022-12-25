package com.example.monitoringprescriptions.ui.appointments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.utils.mutable
import java.util.*

class AppointmentsViewModel(
    private val appointmentsInteractor: AppointmentsInteractor,
    private val currentCalendar: Calendar
) : ViewModel() {

    // подписались на listener (в случае изменения, перегружаем все данные)
    private val onAppointmentListener = {
        loadData()
    }

    val selectedReceptionLiveData: LiveData<AppointmentFullEntity> = MutableLiveData()
    val loaderVisibilityLiveData: LiveData<Boolean> = MutableLiveData()

    // сообщаем что данные изменились
    val appointmentsLiveData: LiveData<List<AppointmentFullEntity>> = MutableLiveData()

    fun onPrescriptionClick(appointmentFullEntity: AppointmentFullEntity) {
        (selectedReceptionLiveData as MutableLiveData).value = appointmentFullEntity
    }

    //получаем при нажатии статус исполнения приема лекарств
    fun onAppointmentSelected(
        appointmentId: String,
        appointmentStatus: AppointmentStatus
    ) {
        appointmentsInteractor.changeStatus(appointmentId, appointmentStatus)
    }

    private fun loadData() {
        loaderVisibilityLiveData.mutable().postValue(true)

        appointmentsInteractor.getByDate(currentCalendar) {
            loaderVisibilityLiveData.mutable().postValue(false)
            appointmentsLiveData.mutable().postValue(it)
        }
    }

    init {
        loadData()
        // подписываемся на статус приема лекарств
        appointmentsInteractor.subscribe(onAppointmentListener)
    }

    override fun onCleared() {
        super.onCleared()
        // отписываемся от приема лекарств
        appointmentsInteractor.unsubscribe(onAppointmentListener)
    }

    fun onTempCreateClick() {
        appointmentsInteractor.generateNewPrescription()
    }
}