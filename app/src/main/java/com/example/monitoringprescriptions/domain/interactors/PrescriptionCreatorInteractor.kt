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

        days: Int, // сколько дней длится прием лекарств

        timesAtDay: Int // сколько раз в день принемать лекарства

    ): PrescriptionEntity
}