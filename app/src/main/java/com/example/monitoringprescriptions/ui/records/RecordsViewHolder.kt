package com.example.monitoringprescriptions.ui.records

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemRecordReceptionBinding
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair

class RecordsViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(

    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_record_reception, parent, false)
) {

    private val binding: ItemRecordReceptionBinding = ItemRecordReceptionBinding.bind(itemView)
    private lateinit var receptionRecordPair: ReceptionRecordPair

    fun bind(receptionRecordPair: ReceptionRecordPair) {
        this.receptionRecordPair = receptionRecordPair

        binding.nameMedicineTextView.text = receptionRecordPair.receptionEntity.nameMedicine
        binding.typeMedicineTextView.text =
            receptionRecordPair.receptionEntity.typeMedicine.toString()
//        binding.timeTextView.text = recordReceptionEntity.time
        binding.dosageTextView.text = receptionRecordPair.receptionEntity.dosage.toString()
    }

}