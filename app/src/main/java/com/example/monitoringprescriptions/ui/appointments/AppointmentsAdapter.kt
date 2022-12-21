package com.example.monitoringprescriptions.ui.appointments

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.data.room.HistoryAppointmentFullEntity
import com.example.monitoringprescriptions.domain.AppointmentStatus

class AppointmentsAdapter(
    private var data: List<HistoryAppointmentFullEntity> = mutableListOf(),
    private var showPopupMenu: () -> Unit,
    val context: Context,
    private var listener: (
        appointmentId: String,
        appointmentStatus: AppointmentStatus
    ) -> Unit = { _, _ -> },
) : RecyclerView.Adapter<AppointmentsViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(records: List<HistoryAppointmentFullEntity>) {
        data = records.sortedBy { it.time }// сортируем по времени
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentsViewHolder {
        return AppointmentsViewHolder(
            parent,
            showPopupMenu,
            context,
            listener,
        )
    }

    override fun onBindViewHolder(holder: AppointmentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): HistoryAppointmentFullEntity = data[position]

    override fun getItemCount(): Int = data.size

}