package com.example.monitoringprescriptions.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.utils.mutable

class DetailsPrescriptionViewModel(
    private val prescriptionId: String,
    private val prescriptionRepo: PrescriptionRepo,
    private val appointmentsRepo: AppointmentsRepo,
    private val appointmentsInteractor: AppointmentsInteractor
) : ViewModel() {

    fun onDeleteClick() {
        prescriptionLiveData.value?.let {
//            prescriptionRepo.delete(it)
            appointmentsInteractor.delete(it)
        }
    }

    fun onUnitMeasurementSelectSpinner(unitMeasurement: String) {
        // todo
    }

    fun onMedicineSelectSpinner(medicine: String) {
        // todo
    }

    fun onNumberAdmissionsPerDaySelectSpinner(perDay: String) {
        // todo
    }

    fun onSaveDetails(
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
            medicationsCourse = numberOfDays.toFloat()
        )?.let {
            prescriptionRepo.updatePrescription(it)
        }
    }


    val prescriptionListLiveDate: LiveData<List<AppointmentEntity>> = MutableLiveData()

    // сообщаем ViewModel отрисовать данные
    val prescriptionLiveData: LiveData<PrescriptionEntity> = MutableLiveData()

    init {
        // получаем данные
        val prescription = prescriptionRepo.getById(prescriptionId)
        prescriptionLiveData.mutable().postValue(prescription)

        val appointmentList = appointmentsRepo.getPrescriptionAppointments(prescriptionId)
        prescriptionListLiveDate.mutable().postValue(appointmentList)
    }

}