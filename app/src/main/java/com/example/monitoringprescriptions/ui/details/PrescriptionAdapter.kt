package com.example.monitoringprescriptions.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity

class PrescriptionAdapter(
    private var data: List<AppointmentEntity?> = mutableListOf(),
    val context: Context,
) : RecyclerView.Adapter<PrescriptionViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(appointmentEntity: List<AppointmentEntity?>) {
        data = appointmentEntity
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        return PrescriptionViewHolder(
            parent,
            context
        )
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): AppointmentEntity? = data[position]

    override fun getItemCount(): Int = data.size
}