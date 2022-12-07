package com.example.monitoringprescriptions.ui.records

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair

class RecordsAdapter(
    private var data: List<ReceptionRecordPair> = mutableListOf(),
    private var showPopupMenu: () -> Unit,
    val context: Context,
    private var listener: (
        receptionEntity: ReceptionEntity,
        recordId: String,
        appointmentStatus: AppointmentStatus
    ) -> Unit = { _, _, _ -> },
//    private var recordListener: (ReceptionRecordPair) -> Unit = { _ -> }
) : RecyclerView.Adapter<RecordsViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(records: List<ReceptionRecordPair>) {
        data = records.sortedBy { it.recordEntity.id }// сортируем по времени
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

    private fun getItem(position: Int): ReceptionRecordPair = data[position]

    override fun getItemCount(): Int = data.size

}