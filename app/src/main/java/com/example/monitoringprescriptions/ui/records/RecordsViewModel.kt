package com.example.monitoringprescriptions.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair
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
    val appointmentsLiveData: LiveData<List<AppointmentFullEntity>> = MutableLiveData()

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

        // фильтруем две сущьности и делаем одну сущьность которая нужна для экрана
        // обычный список appointments превращаем в список fullAppointments
        val fullAppointments = appointments.map {
            // конкретный рецепт вытащили из репозитория
            val prescription = prescriptionRepo.getId(it.prescriptionId)
            val appointment = it
            // проверка на null
            requireNotNull(prescription)
            // объединяем prescription и appointment
            return@map AppointmentFullEntity(
                appointmentId = appointment.id,
                time = appointment.time,
                status = appointment.status,
                prescriptionId = prescription.id,
                nameMedicine = prescription.nameMedicine,
                prescribedMedicine = prescription.prescribedMedicine,
                typeMedicine = prescription.typeMedicine,
                dosage = prescription.dosage,
                unitMeasurement = prescription.unitMeasurement
            )
        }
        loaderVisibilityLiveData.mutable().postValue(false)
        appointmentsLiveData.mutable().postValue(fullAppointments)

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