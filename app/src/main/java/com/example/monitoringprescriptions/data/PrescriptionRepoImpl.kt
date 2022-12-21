package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import java.util.*

class PrescriptionRepoImpl : PrescriptionRepo {

    private val date: MutableList<PrescriptionEntity> = mutableListOf()

    override fun addPrescription(prescriptionEntity: PrescriptionEntity) {
        date.add(prescriptionEntity)
    }

    override fun getListPrescription(): List<PrescriptionEntity> {
        return ArrayList(date)
    }

    override fun getId(id: String): PrescriptionEntity? {
        return date.find {
            id == it.id
        }
    }

    init {
        date.add(
            PrescriptionEntity(
                id = "123",

                nameMedicine = "Люфастон",

                prescribedMedicine = "Таблетка",

                typeMedicine = TypeMedicine.PILL,

                dateStart = Calendar.getInstance().timeInMillis, // todo пока не используется

                dateEnd = Calendar.getInstance().timeInMillis, // todo пока не используется

                receptionFrequency = 1, // todo как описать. пока не используется

                dosage = 1.5f,

                unitMeasurement = "шт.",

                photo = "path/to/photo", // todo пока не используется

                comment = "нет комментария"

            )
        )
    }
}