package com.example.monitoringprescriptions.domain.interactors

import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import java.util.*

interface PrescriptionCreatorInteractor {

    // создаем новый Prescription (сущьность)
    fun create(
        nameMedicine: String,
        prescribedMedicine: String,
        typeMedicine: TypeMedicine,
        dosage: Float,
        unitMeasurement: String,
        comment: String,
        dateStart: Calendar, // когда начинать прием
        numberDaysTakingMedicine: Int, // сколько дней длится прием лекарств (количество дней приема лекарства)

        // добавлено
        dateEnd: Calendar, // дата окончания приема лекарства  todo вырезать (возможно не стоит это хранить)
        numberAdmissionsPerDay: String, // количество приемов в день
        medicationsCourse: Float // количество лекарства на весь курс лечения

    ): PrescriptionEntity
}


