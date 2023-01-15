package com.example.monitoringprescriptions.ui.details

import android.content.Context
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.domain.ErrorMessage
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.utils.toString
import com.example.monitoringprescriptions.utils.toastMake

class CheckingForErrors(
    private val context: Context
) {
    fun checkingForErrors(
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
                    ErrorMessage.RECEPTION_DAYS.toString(context)
                )
            }

            nameMedicine.isEmpty() -> {
                CreationPrescriptionScreenErrors.NameMedicineError(
                    ErrorMessage.NAME_MEDICINE.toString(context)
                )
            }

            typeMedicine == TypeMedicine.TYPE_MED -> {
                CreationPrescriptionScreenErrors.PrescribedMedicineError(
                    ErrorMessage.TYPE_MEDICINE.toString(context)
                )
            }

            dosage == null -> {
                CreationPrescriptionScreenErrors.DosageError(
                    ErrorMessage.DOSAGE.toString(context)
                )
            }

            // todo доработать (единица измерения должна соответствовать виду лекарства)
            unitMeasurement == null || unitMeasurement == UnitsMeasurement.UNITS_MEAS -> {
                CreationPrescriptionScreenErrors.UnitMeasurementError(
                    ErrorMessage.UNIT_MEASUREMENT.toString(context)
                )
            }

            // todo не выполняется проверка
            dateStart == null || dateStart == 0L -> {
                CreationPrescriptionScreenErrors.DateStartError(
                    ErrorMessage.DATE_ADMISSION.toString(context)
                )
            }

            numberAdmissionsPerDay == null -> {
                CreationPrescriptionScreenErrors.NumberAdmissionsPerDayError(
                    ErrorMessage.NUMBER_RECEPTION.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.PILL && unitMeasurement != UnitsMeasurement.PIECES -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.SYRINGE &&
                    unitMeasurement != UnitsMeasurement.MILLILITER -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.POWDER && (
                    unitMeasurement != UnitsMeasurement.SPOON &&
                            unitMeasurement != UnitsMeasurement.GRAM) -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.SUSPENSION && (
                    unitMeasurement != UnitsMeasurement.PACKAGE &&
                            unitMeasurement != UnitsMeasurement.PIECES &&
                            unitMeasurement != UnitsMeasurement.MILLILITER
                    ) -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.OINTMENT && (
                    unitMeasurement != UnitsMeasurement.GRAM && unitMeasurement != UnitsMeasurement.TUBE
                    ) -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.TINCTURE && unitMeasurement != UnitsMeasurement.MILLILITER -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.DROPS && unitMeasurement != UnitsMeasurement.DROP -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }

            // todo дополнительная проверка на соответствие значений
            typeMedicine == TypeMedicine.CANDLES && unitMeasurement != UnitsMeasurement.PIECES -> {
                context.toastMake(R.string.unit_error)
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    ErrorMessage.UNIT_ERROR.toString(context)
                )
            }
            else ->
                Unit
        }
    }
}