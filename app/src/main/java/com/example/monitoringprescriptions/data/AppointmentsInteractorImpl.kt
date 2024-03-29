package com.example.monitoringprescriptions.data

import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import java.util.*

class AppointmentsInteractorImpl(
    private val appointmentsRepo: AppointmentsRepo,
    private val prescriptionRepo: PrescriptionRepo
) : AppointmentsInteractor {

    private val subscribers: MutableSet<() -> Unit> = HashSet()

    override fun changeStatus(appointmentId: String, status: AppointmentStatus) {
        appointmentsRepo.getById(appointmentId)?.let {
            appointmentsRepo.updateAppointments(it.copy(status = status))
        }
        // Для подписки (статус)
        notifyListener()
    }

    private fun notifyListener() {
        subscribers.forEach {
            it.invoke()
        }
    }

    override fun subscribe(callback: () -> Unit) {
        subscribers.add(callback)
    }

    override fun unsubscribe(callback: () -> Unit) {
        subscribers.remove(callback)
    }

    override fun getByDate(calendar: Calendar, callback: (List<AppointmentFullEntity>) -> Unit) {

        // Загрузка данных
        // календарь превращаем в дни
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val appointments = appointmentsRepo.getByDate(year, month, day)

        // перегрузка данных
        // фильтруем две сущьности и делаем одну сущьность которая нужна для экрана
        // обычный список appointments превращаем в список fullAppointments
        val fullAppointments = appointments.map {
            // конкретный рецепт вытащили из репозитория
            val prescription = prescriptionRepo.getById(it.prescriptionId)
            val appointment = it
            // проверка на null
            requireNotNull(prescription)
            // объединяем prescription и appointment
            return@map AppointmentFullEntity(
                appointmentId = appointment.id,
                time = appointment.time,
                status = appointment.status,
                prescriptionId = prescription.id,
                nameMedicine = prescription.nameMedicine,
                typeMedicine = prescription.typeMedicine,
                dosage = prescription.dosage,
                unitMeasurement = prescription.unitMeasurement
            )
        }
        callback.invoke(fullAppointments)
    }

    override fun delete(prescriptionEntity: PrescriptionEntity) {
        // удаляем все appointment
        appointmentsRepo.deletePrescriptionAppointments(prescriptionEntity.id)
        // потом удаляем prescription
        prescriptionRepo.delete(prescriptionEntity)
        notifyListener()
    }

    // удаление одной записи в рецепте
    override fun delete(appointmentEntity: AppointmentEntity) {
        // удаляем все appointment
        appointmentsRepo.delete(appointmentEntity.prescriptionId, appointmentEntity.id)
        notifyListener()
    }

    // удаление одной записи в рецепте
    override fun delete(appointmentFullEntity: AppointmentFullEntity) {
        // удаляем все appointment
        appointmentsRepo.delete(
            appointmentFullEntity.prescriptionId,
            appointmentFullEntity.appointmentId
        )
        notifyListener()
    }
}