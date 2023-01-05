package com.example.monitoringprescriptions.ui.details.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo

class NewPrescriptionViewModel(
    private val prescriptionRepo: PrescriptionRepo,
    private val appointmentsRepo: AppointmentsRepo
) : ViewModel() {

    private lateinit var prescriptionEntity: PrescriptionEntity
    private lateinit var appointmentEntity: AppointmentEntity

    fun onUnitMeasurementSelectSpinner(unitMeasurement: String) {
        // todo
    }

    fun onMedicineSelectSpinner(medicine: String) {
        // todo
    }

    fun onNumberAdmissionsPerDaySelectSpinner(perDay: String) {
        // todo
    }

    fun onSaveNewPrescription(
        dateStart: Long,
        dateEnd: Long,
        nameMedicine: String,
        dosage: String,
        comment: String,
        prescribedMedicine: String,
        unitMeasurement: String,
        numberAdmissionsPerDay: String,
        numberOfDays: String,
        medicationsCourse: String
    ) {
        prescriptionLiveData.value?.copy(
            dateStart = dateStart,
            dateEnd = dateEnd,
            prescribedMedicine = prescribedMedicine,
            unitMeasurement = unitMeasurement,
            numberAdmissionsPerDay = numberAdmissionsPerDay,
            nameMedicine = nameMedicine,
            dosage = dosage.toFloat(),
            comment = comment,
            numberDaysTakingMedicine = numberOfDays.toInt(),
            medicationsCourse = medicationsCourse.toFloat()
        )?.let {
            prescriptionRepo.addPrescription(it)
        }
    }

    // сообщаем ViewModel
    val prescriptionLiveData: LiveData<PrescriptionEntity> = MutableLiveData()
    val appointmentsLiveData: LiveData<AppointmentEntity> = MutableLiveData()

//    init {
//        val prescription = prescriptionRepo.addPrescription(prescriptionEntity)
//        prescriptionLiveData.mutable().postValue(prescription)
//
//        val appointment = appointmentsRepo.addAppointment(appointmentEntity)
//        appointmentsLiveData.mutable().postValue(appointment)
//    }
}
