package com.example.monitoringprescriptions.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity

class AppointmentInPrescriptionAdapter(
    private var data: List<AppointmentEntity> = mutableListOf(),
    val context: Context,
) : RecyclerView.Adapter<AppointmentInPrescriptionViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(appointmentEntity: List<AppointmentEntity>) {
        data = appointmentEntity
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentInPrescriptionViewHolder {
        return AppointmentInPrescriptionViewHolder(
            parent,
            context
        )
    }

    override fun onBindViewHolder(holder: AppointmentInPrescriptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): AppointmentEntity = data[position]

    override fun getItemCount(): Int = data.size
}