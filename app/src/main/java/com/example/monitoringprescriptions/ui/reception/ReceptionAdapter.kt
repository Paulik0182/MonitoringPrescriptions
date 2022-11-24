package com.example.monitoringprescriptions.ui.reception

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity

class ReceptionAdapter(
    private var data: List<ReceptionEntity> = mutableListOf(),
    private var onDetailsReceptionListener: (ReceptionEntity) -> Unit = {}
): RecyclerView.Adapter<ReceptionViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(reception: List<ReceptionEntity>){
        data = reception
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceptionViewHolder {
        return ReceptionViewHolder(
            parent,
            onDetailsReceptionListener
        )
    }

    override fun onBindViewHolder(holder: ReceptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): ReceptionEntity = data[position]

    override fun getItemCount(): Int = data.size

}