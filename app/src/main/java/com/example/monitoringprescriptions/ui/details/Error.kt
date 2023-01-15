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
sealed class CreationPrescriptionScreenErrors(val errorsMessage: String) {
    // перечисляются все потомки класса
    class NameMedicineError(errorsMessage: String) :
        CreationPrescriptionScreenErrors(errorsMessage)

    class PrescribedMedicineError(errorsMessage: String) :
        CreationPrescriptionScreenErrors(errorsMessage)

    class DosageError(errorsMassage: String) : CreationPrescriptionScreenErrors(errorsMassage)

    class UnitMeasurementError(errorsMessage: String) :
        CreationPrescriptionScreenErrors(errorsMessage)

    class UnitMeasurementMatchingValuesError(errorsMessage: String) :
        CreationPrescriptionScreenErrors(errorsMessage)

    class DateStartError(errorsMessage: String) :
        CreationPrescriptionScreenErrors(errorsMessage)

    class NumberDaysTakingMedicineError(errorsMessage: String) :
        CreationPrescriptionScreenErrors(errorsMessage)

    class NumberAdmissionsPerDayError(errorsMessage: String) :
        CreationPrescriptionScreenErrors(errorsMessage)
}
