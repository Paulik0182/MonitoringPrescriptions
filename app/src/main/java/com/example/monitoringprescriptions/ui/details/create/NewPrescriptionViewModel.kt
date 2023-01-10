package com.example.monitoringprescriptions.ui.details.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import com.example.monitoringprescriptions.utils.mutable
import java.util.*

class NewPrescriptionViewModel(
    private val prescriptionCreatorInteractor: PrescriptionCreatorInteractor,
) : ViewModel() {

    // для сообщения ошибки
    val errorsLiveData: LiveData<CreationPrescriptionScreenErrors> = MutableLiveData()

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
        unitMeasurement: String,
        comment: String,
        dateStart: Long,
        numberDaysTakingMedicine: Int,
        dateEnd: Long,
        numberAdmissionsPerDay: String,
        medicationsCourse: Float
    ) {
        when {
            nameMedicine.isEmpty() -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.NameMedicineError("Заполните поле Название лекарства")
                )
            }
            dosage == null -> {
                errorsLiveData.mutable().postValue(
                    CreationPrescriptionScreenErrors.DosageError("Укажите дозировку")
                )
            }
            // todo продолжить проверки
            else -> {
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
                        .apply {
                            timeInMillis = dateEnd
                        }, // todo вырезать (возможно не стоит это хранить)
                    numberAdmissionsPerDay = numberAdmissionsPerDay,
                    medicationsCourse = medicationsCourse //todo требуется проверка на валидность
                )
                // todo сюда вставить диалог (данные сохранены) одноразовая LiveDate (singleLiveDate)
            }
        }
    }

    /**
     * Валидация полей. Этапы
     *
     * 1. Сбор gathering - метод который собирает с view все данные
     *
     * 2. Валидация validate - метод который проверяет данные на корректность
     *
     * 3. Сохранение данных (отправка во ViewModel)
     *
     * Первый и второй этап зачастую соеденены
     *
     */

    // для примера завели sealed класс для сообщения ошибки
    sealed class CreationPrescriptionScreenErrors(val errorsMassage: String) {
        // перечисляются все потомки класса
        class DosageError(errorsMassage: String) : CreationPrescriptionScreenErrors(errorsMassage)
        class NameMedicineError(errorsMassage: String) :
            CreationPrescriptionScreenErrors(errorsMassage)

    }
}
