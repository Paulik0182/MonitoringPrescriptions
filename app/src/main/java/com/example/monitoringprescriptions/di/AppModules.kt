package com.example.monitoringprescriptions.di

import androidx.room.Room
import com.example.monitoringprescriptions.data.*
import com.example.monitoringprescriptions.data.room.AppDataBase
import com.example.monitoringprescriptions.data.room.AppointmentDao
import com.example.monitoringprescriptions.data.room.PrescriptionDao
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.interactors.PrescriptionCreatorInteractor
import com.example.monitoringprescriptions.domain.interactors.ReceiverReminderInteraction
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.ui.appointments.AppointmentsViewModel
import com.example.monitoringprescriptions.ui.details.DetailsPrescriptionViewModel
import com.example.monitoringprescriptions.ui.details.create.NewPrescriptionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    // создаем БД. сингл в коде коин
    single<AppDataBase> {
        Room.databaseBuilder(
            get(),
            AppDataBase::class.java, "database"
        ).allowMainThreadQueries().build()//переделать, должно быть не в основном потоке
    }

    // создали конкретный Dao
    single<PrescriptionDao> { get<AppDataBase>().prescriptionDao() }
    // В качестве get() попадет PrescriptionDao, см выше
    single<PrescriptionRepo> { RoomPrescriptionRepoImpl(get()) }

    // создали конкретный Dao
    single<AppointmentDao> { get<AppDataBase>().appointmentDao() }
    // В качестве get() попадет AppointmentDao, см выше
    single<AppointmentsRepo> { RoomAppointmentRepoImpl(get()) }

    single<AppointmentsInteractor> { AppointmentsInteractorImpl(get(), get()) }

    single<PrescriptionCreatorInteractor> {
        PrescriptionCreatorInteractorImpl(
            get(),
            get(),
            get(),
            get()
        )
    }

    single<ReceiverReminderInteraction> { ReceiverReminderInteractionImpl(get()) }

    viewModel { parameters -> AppointmentsViewModel(get(), parameters.get()) }
    viewModel { parameters ->
        DetailsPrescriptionViewModel(
            parameters.get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { NewPrescriptionViewModel(get(), get()) }
}