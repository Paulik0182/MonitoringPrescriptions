package com.example.monitoringprescriptions.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.utils.mutable

class DetailsPrescriptionViewModel(
    private val prescriptionId: String,
    private val prescriptionRepo: PrescriptionRepo,
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

    fun onSaveDetails(nameMedicine: String, dosage: String, comment: String) {
        prescriptionLiveData.value?.copy(
//            dateStart = dateStart.toLong(), // todo проблема с передачей данных
            nameMedicine = nameMedicine,
            dosage = dosage.toFloat(),
            comment = comment
        )?.let {
            prescriptionRepo.updatePrescription(it)
        }
    }


    val prescriptionListLiveDate: LiveData<List<AppointmentEntity?>> = MutableLiveData()

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