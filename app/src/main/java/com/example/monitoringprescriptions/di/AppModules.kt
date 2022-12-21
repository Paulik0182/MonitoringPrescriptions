package com.example.monitoringprescriptions.di

import com.example.monitoringprescriptions.App.Companion.getHistoryAppointmentFullDao
import com.example.monitoringprescriptions.data.AppointmentsInteractorImpl
import com.example.monitoringprescriptions.data.AppointmentsRepoImpl
import com.example.monitoringprescriptions.data.PrescriptionRepoImpl
import com.example.monitoringprescriptions.data.room.HistoryAppointmentFullLocalRepo
import com.example.monitoringprescriptions.data.room.HistoryAppointmentFullLocalRepoImpl
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import com.example.monitoringprescriptions.ui.appointments.AppointmentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<PrescriptionRepo> { PrescriptionRepoImpl() }
    single<AppointmentsRepo> { AppointmentsRepoImpl() }
    single<AppointmentsInteractor> { AppointmentsInteractorImpl(get(), get()) }

    single<HistoryAppointmentFullLocalRepo> {
        HistoryAppointmentFullLocalRepoImpl(
            getHistoryAppointmentFullDao(),
            get(),
            get()
        )
    }

    viewModel { parameters -> AppointmentsViewModel(get(), parameters.get()) }
}