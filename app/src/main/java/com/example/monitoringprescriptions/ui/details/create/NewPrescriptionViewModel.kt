package com.example.monitoringprescriptions.ui.details.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import com.example.monitoringprescriptions.ui.CloseDialog
import com.example.monitoringprescriptions.ui.details.CreationPrescriptionScreenErrors
import com.example.monitoringprescriptions.utils.mutable
import com.example.monitoringprescriptions.utils.toastMake
import java.util.*

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
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.NumberDaysTakingMedicineError(
                        "Укажите количество дней приема лекарства"
                    )
                )
            }

            nameMedicine.isEmpty() -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.NameMedicineError(
                        "Укажите название лекарства"
                    )
                )
            }

            prescribedMedicine.isEmpty() -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.PrescribedMedicineError(
                        "Укажите вид лекарства"
                    )
                )
            }

            dosage == null -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.DosageError(
                        "Укажите дозировку"
                    )
                )
            }

            // todo доработать (единица измерения должна соответствовать виду лекарства)
            unitMeasurement == null || unitMeasurement == listOf("Еди. изм.")[0] -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementError(
                        "Укажите единицу измерения"
                    )
                )
            }

            // todo не выполняется проверка
            dateStart == null || dateStart == 0L -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.DateStartError(
                        "Укажите дату приема лекарства"
                    )
                )
            }

            numberAdmissionsPerDay == null -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.NumberAdmissionsPerDayError(
                        "Укажите количество приемов день"
                    )
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Таблетка" && unitMeasurement != "шт." -> {
                context.toastMake("Не верно указана единица измерения")
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                        "Не верно указана единица измерения"
                    )
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Укол" &&
                    unitMeasurement != "мл." -> {
                context.toastMake("Не верно указана единица измерения")
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                        "Не верно указана единица измерения"
                    )
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Порошок" && (
                    unitMeasurement != "ложка" &&
                            unitMeasurement != "гр.") -> {
                context.toastMake("Не верно указана единица измерения")
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                        "Не верно указана единица измерения"
                    )
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Суспензия" && (
                    unitMeasurement != "пакет" &&
                            unitMeasurement != "шт." &&
                            unitMeasurement != "мл."
                    ) -> {
                context.toastMake("Не верно указана единица измерения")
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                        "Не верно указана единица измерения"
                    )
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Мазь" && (
                    unitMeasurement != "гр." && unitMeasurement != "тюбик"
                    ) -> {
                context.toastMake("Не верно указана единица измерения")
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                        "Не верно указана единица измерения"
                    )
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Настойка" && unitMeasurement != "мл." -> {
                context.toastMake("Не верно указана единица измерения")
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                        "Не верно указана единица измерения"
                    )
                )
            }

            // todo дополнительная проверка на соответствие значений
            prescribedMedicine == "Капли" && unitMeasurement != "капя" -> {
                context.toastMake("Не верно указана единица измерения")
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError(
                        "Не верно указана единица измерения"
                    )
                )
            }

            // todo после проверки
            else -> {
                prescriptionCreatorInteractor.create(
                    nameMedicine = nameMedicine,
                    prescribedMedicine = prescribedMedicine,
                    typeMedicine = TypeMedicine.PILL, // todo подумать как убрать это поле (совместить с другим полем)
                    dosage = dosage,
                    unitMeasurement = unitMeasurement,
                    comment = comment,
                    dateStart = Calendar.getInstance().apply { timeInMillis = dateStart },
                    numberDaysTakingMedicine = numberDaysTakingMedicine,
                    numberAdmissionsPerDay = numberAdmissionsPerDay,
                    medicationsCourse = medicationsCourse
                )
                // todo сюда вставить диалог (данные сохранены) одноразовая LiveDate (singleLiveDate)
                dialogLiveData.mutable().postValue(
                    CloseDialog.ShowCloseDialog("Запись создана")
                )
            }
        }
    }
}
