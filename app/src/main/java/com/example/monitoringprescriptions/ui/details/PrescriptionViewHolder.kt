package com.example.monitoringprescriptions.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemAssignedBinding
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.utils.bpDataFormatter
import com.example.monitoringprescriptions.utils.bpTimeFormatter

class PrescriptionViewHolder(
    parent: ViewGroup,
    val context: Context
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_assigned, parent, false)
) {

    private val binding: ItemAssignedBinding = ItemAssignedBinding.bind(itemView)
    private lateinit var appointmentEntity: AppointmentEntity

    fun bind(appointmentEntity: AppointmentEntity) {
        this.appointmentEntity = appointmentEntity

        binding.dateTextView.text = bpDataFormatter.format(appointmentEntity.time)
        binding.timeTextView.text = bpTimeFormatter.format(appointmentEntity.time)

        binding.nameMedicineTextView.text = appointmentEntity.status.toString()
        when (appointmentEntity.status) {
            AppointmentStatus.UNKNOWN -> binding.nameMedicineTextView.text = "Неизвестно"
            AppointmentStatus.YES -> binding.nameMedicineTextView.text = "Принято"
            AppointmentStatus.NO -> binding.nameMedicineTextView.text = "Не принято"
        }
    }
}
