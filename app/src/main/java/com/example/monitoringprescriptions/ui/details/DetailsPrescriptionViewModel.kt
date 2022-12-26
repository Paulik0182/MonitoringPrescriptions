package com.example.monitoringprescriptions.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.utils.mutable

class DetailsPrescriptionViewModel(
    private val prescriptionId: String,
    private val prescriptionRepo: PrescriptionRepo
) : ViewModel() {
    fun onDeleteClick() {
//        prescriptionRepo.delete()
        // todo
    }

    fun onUnitMeasurementSelectSpinner(unitMeasurement: String) {
        // todo
    }

    fun onMedicineSelectSpinner(medicine: String) {
        // todo
    }

    fun onDosageSelectSpinner(dosage: String) {
        // todo
    }

    // сообщаем ViewModel отрисовать данные
    val prescriptionLiveData: LiveData<PrescriptionEntity> = MutableLiveData()

    init {
        // получаем данные
        val prescription = prescriptionRepo.getById(prescriptionId)
        prescriptionLiveData.mutable().postValue(prescription)
    }

}