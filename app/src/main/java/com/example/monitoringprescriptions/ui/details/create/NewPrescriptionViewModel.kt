package com.example.monitoringprescriptions.ui.details.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import com.example.monitoringprescriptions.ui.CloseDialog
import com.example.monitoringprescriptions.ui.details.CreationPrescriptionScreenErrors
import com.example.monitoringprescriptions.utils.mutable
import com.example.monitoringprescriptions.utils.toastMake

class NewPrescriptionViewModel(
    private val prescriptionCreatorInteractor: PrescriptionCreatorInteractor,
    private val context: Context
) : ViewModel() {

    // для сообщения ошибки
    val errorsLiveData: LiveData<CreationPrescriptionScreenErrors> = MutableLiveData()

    // для дополнительного уведомления
    val dialogLiveData: LiveData<CloseDialog> = MutableLiveData()

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
        dosage: Float?,
        unitMeasurement: UnitsMeasurement?,
        typeMedicine: TypeMedicine,
        comment: String,
        dateStart: Long?,
        numberDaysTakingMedicine: Int?,
        numberAdmissionsPerDay: Int?,
        medicationsCourse: Float
    ) {
        when {
            numberDaysTakingMedicine == null || numberDaysTakingMedicine == 0 -> {
                CreationPrescriptionScreenErrors.NumberDaysTakingMedicineError(
                    "Укажите количество дней приема лекарства"
                )
            }

            nameMedicine.isEmpty() -> {
                CreationPrescriptionScreenErrors.NameMedicineError(
                    "Укажите название лекарства"
                )
            }

            typeMedicine == TypeMedicine.TYPE_MED -> {
                CreationPrescriptionScreenErrors.PrescribedMedicineError(
                    "Укажите вид лекарства"
                )
            }

            dosage == null -> {
                CreationPrescriptionScreenErrors.DosageError(
                    "Укажите дозировку"
                )
            }

            // todo доработать (единица измерения должна соответствовать виду лекарства)
            unitMeasurement == null || unitMeasurement == UnitsMeasurement.UNITS_MEAS -> {
                CreationPrescriptionScreenErrors.UnitMeasurementError(
                    "Укажите единицу измерения"
                )
            }

            // todo не выполняется проверка
            dateStart == null || dateStart == 0L -> {
                CreationPrescriptionScreenErrors.DateStartError(
                    "Укажите дату приема лекарства"
                )
            }

            numberAdmissionsPerDay == null -> {
                CreationPrescriptionScreenErrors.NumberAdmissionsPerDayError(
                    "Укажите количество приемов день"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.PILL && unitMeasurement != UnitsMeasurement.PIECES -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.SYRINGE &&
                    unitMeasurement != UnitsMeasurement.MILLILITER -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.POWDER && (
                    unitMeasurement != UnitsMeasurement.SPOON &&
                            unitMeasurement != UnitsMeasurement.GRAM) -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.SUSPENSION && (
                    unitMeasurement != UnitsMeasurement.PACKAGE &&
                            unitMeasurement != UnitsMeasurement.PIECES &&
                            unitMeasurement != UnitsMeasurement.MILLILITER
                    ) -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.OINTMENT && (
                    unitMeasurement != UnitsMeasurement.GRAM && unitMeasurement != UnitsMeasurement.TUBE
                    ) -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.TINCTURE && unitMeasurement != UnitsMeasurement.MILLILITER -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.DROPS && unitMeasurement != UnitsMeasurement.DROP -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.CANDLES && unitMeasurement != UnitsMeasurement.PIECES -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo после проверки
            else -> {
                prescriptionCreatorInteractor.create(
                    nameMedicine = nameMedicine,
                    dosage = dosage,
                    unitMeasurement = unitMeasurement,
                    typeMedicine = typeMedicine,
                    comment = comment,
                    dateStart = java.util.Calendar.getInstance().apply { timeInMillis = dateStart },
                    numberDaysTakingMedicine = numberDaysTakingMedicine,
                    numberAdmissionsPerDay = numberAdmissionsPerDay,
                    medicationsCourse = medicationsCourse
                )
                // todo сюда вставить диалог (данные сохранены) одноразовая LiveDate (singleLiveDate).
                //  написать свою одноразовую LiveDate
                dialogLiveData.mutable().postValue(
                    CloseDialog.ShowCloseDialog("Запись создана")
                )
                null
            }
        }?.let {
            errorsLiveData.mutable().postValue(it)
            return
        }
    }
}
