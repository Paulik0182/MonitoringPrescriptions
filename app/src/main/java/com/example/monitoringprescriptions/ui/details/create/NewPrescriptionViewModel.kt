package com.example.monitoringprescriptions.ui.details.create

import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import java.util.*

class NewPrescriptionViewModel(
    private val prescriptionCreatorInteractor: PrescriptionCreatorInteractor,
) : ViewModel() {

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
        nameMedicine: String,
        prescribedMedicine: String,

        dosage: Float,
        unitMeasurement: String,
        comment: String,
        dateStart: Long,
        numberDaysTakingMedicine: Int,
        dateEnd: Long,
        numberAdmissionsPerDay: String,
        medicationsCourse: Float
    ) {
        prescriptionCreatorInteractor.create(
            nameMedicine = nameMedicine,
            prescribedMedicine = prescribedMedicine,
            typeMedicine = TypeMedicine.PILL, // todo подумать как убрать это поле (совместить с другим полем)
            dosage = dosage, //todo требуется проверка на валидность
            unitMeasurement = unitMeasurement,
            comment = comment,
            dateStart = Calendar.getInstance().apply { timeInMillis = dateStart },
            numberDaysTakingMedicine = numberDaysTakingMedicine, //todo требуется проверка на валидность,
            dateEnd = Calendar.getInstance()
                .apply { timeInMillis = dateEnd }, // todo вырезать (возможно не стоит это хранить)
            numberAdmissionsPerDay = numberAdmissionsPerDay,
            medicationsCourse = medicationsCourse //todo требуется проверка на валидность
        )
    }
}
