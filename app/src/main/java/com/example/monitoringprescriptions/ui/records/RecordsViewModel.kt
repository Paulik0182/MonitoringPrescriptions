package com.example.monitoringprescriptions.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.v2.repos.AppointmentsRepo
import com.example.monitoringprescriptions.domain.v2.repos.PrescriptionRepo
import com.example.monitoringprescriptions.utils.mutable
import java.util.*

class RecordsViewModel(
    private val appointmentsRepo: AppointmentsRepo,
    private val prescriptionRepo: PrescriptionRepo,
    private val currentCalendar: Calendar
) : ViewModel() {

    // подписались на listener
    private val onAppointmentListener = { loadData() }

    val selectedReceptionLiveData: LiveData<ReceptionRecordPair> = MutableLiveData()
    val loaderVisibilityLiveData: LiveData<Boolean> = MutableLiveData()

    // сообщаем что данные изменились
    val appointmentsLiveData: LiveData<List<AppointmentEntity>> = MutableLiveData()

    fun onReceptionClick(receptionRecordPair: ReceptionRecordPair) {
        (selectedReceptionLiveData as MutableLiveData).value = receptionRecordPair
    }

    //получаем при нажатии статус исполнения приема лекарств
    fun onAppointmentSelected(
        appointmentId: String,
        appointmentStatus: AppointmentStatus
    ) {
        // перегрузка данных
        appointmentsRepo.getById(appointmentId)?.let {
            appointmentsRepo.updateAppointments(it.copy(status = appointmentStatus))
        }
    }

    private fun loadData() {
        loaderVisibilityLiveData.mutable().postValue(true)

        // Загрузка данных
        // календарь превращаем в дни
        val year = currentCalendar.get(Calendar.YEAR)
        val month = currentCalendar.get(Calendar.MONTH)
        val day = currentCalendar.get(Calendar.DAY_OF_MONTH)

        val appointments = appointmentsRepo.getByDate(year, month, day)
        loaderVisibilityLiveData.mutable().postValue(false)
        appointmentsLiveData.mutable().postValue(appointments)

    }

    init {
        loadData()
        // подписываемся на статус приема лекарств
//        recordAppointmentInteractor.subscribe(onAppointmentListener)
    }

    override fun onCleared() {
        super.onCleared()
        // отписываемся от приема лекарств
//        recordAppointmentInteractor.unsubscribe(onAppointmentListener)
    }

}