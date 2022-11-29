package com.example.monitoringprescriptions.di

import com.example.monitoringprescriptions.data.ReceptionRepoImpl
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo
import org.koin.dsl.module

val appModules = module {

    single<ReceptionRepo> { ReceptionRepoImpl() }
}