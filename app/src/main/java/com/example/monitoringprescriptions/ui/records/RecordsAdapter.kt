package com.example.monitoringprescriptions.ui.records

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.domain.entities.RecordEntity

class RecordsAdapter(
    private var data: List<RecordEntity> = mutableListOf(),
) : RecyclerView.Adapter<RecordsViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(records: List<RecordEntity>) {
        data = records
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder {
        return RecordsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): RecordEntity = data[position]

    override fun getItemCount(): Int = data.size

}