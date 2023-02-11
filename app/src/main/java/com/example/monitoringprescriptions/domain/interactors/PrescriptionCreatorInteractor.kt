package com.example.monitoringprescriptions.domain.interactors

import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import java.util.*

interface PrescriptionCreatorInteractor {

    // создаем новый Prescription (сущьность)
    fun create(
        nameMedicine: String,
        typeMedicine: TypeMedicine,
        dosage: Float,
        unitMeasurement: UnitsMeasurement,
        comment: String,
        dateStart: Calendar, // когда начинать прием
        numberDaysTakingMedicine: Int, // сколько дней длится прием лекарств (количество дней приема лекарства)

        // добавлено
        numberAdmissionsPerDay: Int, // количество приемов в день
        medicationsCourse: Float, // количество лекарства на весь курс лечения

        // Время приема
        timeReceptionTwo: Long?,
        timeReceptionThree: Long?,
        timeReceptionFour: Long?,
        timeReceptionFive: Long?
    ): PrescriptionEntity
}


