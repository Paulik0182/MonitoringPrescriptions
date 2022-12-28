package com.example.monitoringprescriptions.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemAssignedBinding
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity

class PrescriptionViewHolder(
    parent: ViewGroup,
    val context: Context
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_assigned, parent, false)
) {

    private val binding: ItemAssignedBinding = ItemAssignedBinding.bind(itemView)
    private lateinit var prescriptionEntity: PrescriptionEntity

    fun bind(prescriptionEntity: PrescriptionEntity) {
        this.prescriptionEntity = prescriptionEntity

        binding.nameMedicineTextView.text = prescriptionEntity.nameMedicine

    }
}
