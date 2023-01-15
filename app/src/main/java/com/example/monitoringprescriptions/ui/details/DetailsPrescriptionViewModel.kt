package com.example.monitoringprescriptions.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.ui.CloseDialog
import com.example.monitoringprescriptions.utils.mutable
import com.example.monitoringprescriptions.utils.toastMake

class DetailsPrescriptionViewModel(
    private val prescriptionId: String,
    private val prescriptionRepo: PrescriptionRepo,
    private val appointmentsRepo: AppointmentsRepo,
    private val appointmentsInteractor: AppointmentsInteractor,
    private val context: Context
) : ViewModel() {

    // для сообщения ошибки
    val errorsLiveData: LiveData<CreationPrescriptionScreenErrors> = MutableLiveData()

    // для дополнительного уведомления
    val dialogLiveData: LiveData<CloseDialog> = MutableLiveData()
    val closeDialogLiveData: LiveData<CloseDialog> = MutableLiveData()

    fun onDeleteAppointments() {
        prescriptionLiveData.value?.let {
//            prescriptionRepo.delete(it)
            appointmentsInteractor.delete(it)
        }
    }

    fun onDeletePrescription() {
        prescriptionLiveData.value?.let {
            prescriptionRepo.delete(it)
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
                prescriptionLiveData.value?.copy(
                    nameMedicine = nameMedicine,
                    dosage = dosage,
                    unitMeasurement = unitMeasurement,
                    typeMedicine = typeMedicine,
                    comment = comment,
                    dateStart = dateStart,
                    numberDaysTakingMedicine = numberDaysTakingMedicine,
                    numberAdmissionsPerDay = numberAdmissionsPerDay,
                    medicationsCourse = medicationsCourse
                )?.let {
                    prescriptionRepo.updatePrescription(it)

                    // todo сюда вставить диалог (данные сохранены) одноразовая LiveDate (singleLiveDate)
                    dialogLiveData.mutable().postValue(
                        CloseDialog.ShowDialog("Запись создана")
                    )
                    closeDialogLiveData.mutable().postValue(
                        CloseDialog.ShowCloseDialog("Выйти из рецепта?")
                    )
                    null
                }
            }
        }?.let {
            errorsLiveData.mutable().postValue(it)
            return
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