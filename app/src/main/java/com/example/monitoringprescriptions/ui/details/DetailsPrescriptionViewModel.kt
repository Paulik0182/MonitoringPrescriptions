package com.example.monitoringprescriptions.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        nameMedicine: String,
        prescribedMedicine: String,
        dosage: Float?,
        unitMeasurement: String?,
        comment: String,
        dateStart: Long?,
        numberDaysTakingMedicine: Int?,
        numberAdmissionsPerDay: String?,
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

            prescribedMedicine.isEmpty() -> {
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
            unitMeasurement == null || unitMeasurement == listOf("Еди. изм.")[0] -> {
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
            prescribedMedicine == "Таблетка" && unitMeasurement != "шт." -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Укол" &&
                    unitMeasurement != "мл." -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Порошок" && (
                    unitMeasurement != "ложка" &&
                            unitMeasurement != "гр.") -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Суспензия" && (
                    unitMeasurement != "пакет" &&
                            unitMeasurement != "шт." &&
                            unitMeasurement != "мл."
                    ) -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Мазь" && (
                    unitMeasurement != "гр." && unitMeasurement != "тюбик"
                    ) -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Настойка" && unitMeasurement != "мл." -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Капли" && unitMeasurement != "капя" -> {
                context.toastMake("Не верно указана единица измерения")
                CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                    "Не верно указана единица измерения"
                )
            }

            // todo после проверки
            else -> {
                prescriptionLiveData.value?.copy(
                    nameMedicine = nameMedicine,
                    prescribedMedicine = prescribedMedicine,
                    dosage = dosage,
                    unitMeasurement = unitMeasurement,
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