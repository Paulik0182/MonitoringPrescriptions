package com.example.monitoringprescriptions.ui.records

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.domain.AppointmentStatus

class AppointmentsAdapter(
    private var data: List<AppointmentFullEntity> = mutableListOf(),
    private var showPopupMenu: () -> Unit,
    val context: Context,
    private var listener: (
        appointmentId: String,
        appointmentStatus: AppointmentStatus
    ) -> Unit = { _, _ -> },
//    private var recordListener: (ReceptionRecordPair) -> Unit = { _ -> }
) : RecyclerView.Adapter<RecordsViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(records: List<AppointmentFullEntity>) {
        data = records.sortedBy { it.time }// сортируем по времени
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder {
        return RecordsViewHolder(
            parent,
            showPopupMenu,
            context,
            listener,
//            recordListener
        )
    }

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): AppointmentFullEntity = data[position]

    override fun getItemCount(): Int = data.size

}