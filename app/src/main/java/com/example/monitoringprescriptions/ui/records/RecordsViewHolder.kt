package com.example.monitoringprescriptions.ui.records

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemRecordReceptionBinding
import com.example.monitoringprescriptions.domain.entities.RecordEntity

class RecordsViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(

    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_record_reception, parent, false)
) {

    private val binding: ItemRecordReceptionBinding = ItemRecordReceptionBinding.bind(itemView)
    private lateinit var recordEntity: RecordEntity

    fun bind(recordEntity: RecordEntity) {
        this.recordEntity = recordEntity

        binding.nameMedicineTextView.text = recordEntity.id
        binding.typeMedicineTextView.text = recordEntity.status.toString()
//        binding.timeTextView.text = recordReceptionEntity.time
        binding.dosageTextView.text = recordEntity.time.toString()
    }

}