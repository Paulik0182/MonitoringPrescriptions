package com.example.monitoringprescriptions.domain.entities

data class ReceptionRecordPair(
    val receptionEntity: ReceptionEntity,
    val recordEntity: RecordEntity
)