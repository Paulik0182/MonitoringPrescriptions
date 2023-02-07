package com.example.monitoringprescriptions.ui.details.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.domain.ErrorMessage
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.ui.CloseDialog
import com.example.monitoringprescriptions.ui.details.CreationPrescriptionScreenErrors
import com.example.monitoringprescriptions.utils.SingleLiveEvent
import com.example.monitoringprescriptions.utils.mutable
import com.example.monitoringprescriptions.utils.toString
import com.example.monitoringprescriptions.utils.toastMake

class NewPrescriptionViewModel(
    private val prescriptionCreatorInteractor: PrescriptionCreatorInteractor,
    private val context: Context,
    private val prescriptionRepo: PrescriptionRepo,
    private val appointmentsRepo: AppointmentsRepo
) : ViewModel() {

    // для сообщения ошибки
    val errorsLiveData: LiveData<CreationPrescriptionScreenErrors> = MutableLiveData()

    // для дополнительного уведомления
    val dialogLiveData: LiveData<CloseDialog> = SingleLiveEvent()

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
        medicationsCourse: Float,
        // Время приема
        timeReceptionTwo: Long?,
        timeReceptionThree: Long?,
        timeReceptionFour: Long?,
        timeReceptionFive: Long?
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
                    medicationsCourse = medicationsCourse,

                    // Время приема
                    timeReceptionTwo = timeReceptionTwo,
                    timeReceptionThree = timeReceptionThree,
                    timeReceptionFour = timeReceptionFour,
                    timeReceptionFive = timeReceptionFive
                )

                dialogLiveData.mutable().postValue(
                    CloseDialog.ShowCloseDialog(context.getString(R.string.record_created))
                )
                null
            }
        }?.let {
            errorsLiveData.mutable().postValue(it)
            return
        }
    }

    val prescriptionListLiveDate: LiveData<List<AppointmentEntity>> = MutableLiveData()

    // сообщаем ViewModel отрисовать данные
    val prescriptionLiveData: LiveData<PrescriptionEntity> = MutableLiveData()

    // подписка на обновление списка
//    private fun updateAppointments() {
//        val appointmentList = appointmentsRepo.getPrescriptionAppointments(prescriptionId)
//        prescriptionListLiveDate.mutable().postValue(appointmentList)
//    }

    init {
        // получаем данные
        val prescription = prescriptionRepo.getPrescription()
        prescriptionLiveData.mutable().postValue(prescription)
//        updateAppointments()
    }
}
