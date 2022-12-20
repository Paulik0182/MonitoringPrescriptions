package com.example.monitoringprescriptions.di

import com.example.monitoringprescriptions.data.ReceptionRepoImpl
import com.example.monitoringprescriptions.data.RecordAppointmentInteractorIpl
import com.example.monitoringprescriptions.data.RecordsInteractorImpl
import com.example.monitoringprescriptions.data2.AppointmentsInteractorImpl
import com.example.monitoringprescriptions.data2.AppointmentsRepoImpl
import com.example.monitoringprescriptions.data2.PrescriptionRepoImpl
import com.example.monitoringprescriptions.domain.interactors.RecordAppointmentInteractor
import com.example.monitoringprescriptions.domain.interactors.RecordsInteractor
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo
import com.example.monitoringprescriptions.domain.v2.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.v2.repos.AppointmentsRepo
import com.example.monitoringprescriptions.domain.v2.repos.PrescriptionRepo
import com.example.monitoringprescriptions.ui.appointments.AppointmentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<ReceptionRepo> { ReceptionRepoImpl() }
    single<RecordsInteractor> { RecordsInteractorImpl(get()) }
    single<RecordAppointmentInteractor> { RecordAppointmentInteractorIpl(get()) }

    single<PrescriptionRepo> { PrescriptionRepoImpl() }
    single<AppointmentsRepo> { AppointmentsRepoImpl() }
    single<AppointmentsInteractor> { AppointmentsInteractorImpl(get(), get()) }

    viewModel { parameters -> AppointmentsViewModel(get(), parameters.get()) }
}