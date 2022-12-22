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

    private val listeners: MutableSet<() -> Unit> = HashSet()

    override fun changeStatus(appointmentId: String, status: AppointmentStatus) {
        appointmentsRepo.getById(appointmentId)?.let {
            appointmentsRepo.updateAppointments(it.copy(status = status))
        }
        // Для подписки (статус)
        notifyListener()
    }

    private fun notifyListener() {
        listeners.forEach {
            it.invoke()
        }
    }

    override fun subscribe(callback: () -> Unit) {
        listeners.add(callback)
    }

    override fun unsubscribe(callback: () -> Unit) {
        listeners.remove(callback)
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
                prescribedMedicine = prescription.prescribedMedicine,
                typeMedicine = prescription.typeMedicine,
                dosage = prescription.dosage,
                unitMeasurement = prescription.unitMeasurement
            )
        }
        callback.invoke(fullAppointments)
    }

    override fun generateNewPrescription() {
        // заглушечный код (генерируем рецепты)
        val names = listOf("Ребедол", "Баготир", "Инвалин", "Куратин", "Херотин")

        val prescription = PrescriptionEntity(
            nameMedicine = names.random()
        )
        val todayAppointment = AppointmentEntity(
            time = Calendar.getInstance().timeInMillis,
            prescriptionId = prescription.id
        )

        val tomorrowAppointment = AppointmentEntity(
            time = Calendar.getInstance().timeInMillis + 24 * 60 * 60 * 1_000,
            prescriptionId = prescription.id
        )

        // сохранили данные
        prescriptionRepo.addPrescription(prescription)
        appointmentsRepo.addAppointment(todayAppointment)
        appointmentsRepo.addAppointment(tomorrowAppointment)

        // уведомляем все слушатели, что что-то изменилось

        notifyListener()
    }
}