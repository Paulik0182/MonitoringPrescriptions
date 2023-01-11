package com.example.monitoringprescriptions.ui.details

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
    class NameMedicineError(errorsMassage: String) :
        CreationPrescriptionScreenErrors(errorsMassage)

    class PrescribedMedicineError(errorsMassage: String) :
        CreationPrescriptionScreenErrors(errorsMassage)

    class DosageError(errorsMassage: String) : CreationPrescriptionScreenErrors(errorsMassage)

    class UnitMeasurementError(errorsMassage: String) :
        CreationPrescriptionScreenErrors(errorsMassage)

    class UnitMeasurementMatchingValuesError(errorsMassage: String) :
        CreationPrescriptionScreenErrors(errorsMassage)

    class DateStartError(errorsMassage: String) :
        CreationPrescriptionScreenErrors(errorsMassage)

    class NumberDaysTakingMedicineError(errorsMassage: String) :
        CreationPrescriptionScreenErrors(errorsMassage)

    class NumberAdmissionsPerDayError(errorsMassage: String) :
        CreationPrescriptionScreenErrors(errorsMassage)
}
