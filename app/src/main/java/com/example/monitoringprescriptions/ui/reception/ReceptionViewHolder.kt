package com.example.monitoringprescriptions.ui.reception

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemRecordReceptionBinding
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity

class ReceptionViewHolder(
    parent: ViewGroup,
    onDetailsReceptionListener: (ReceptionEntity) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_record_reception, parent, false)
) {

    private val binding: ItemRecordReceptionBinding = ItemRecordReceptionBinding.bind(itemView)

    private lateinit var receptionEntity: ReceptionEntity

    fun bind(recordReceptionEntity: ReceptionEntity) {
        this.receptionEntity = recordReceptionEntity

        binding.nameMedicineTextView.text = recordReceptionEntity.nameMedicine
        binding.typeMedicineTextView.text = recordReceptionEntity.prescribedMedicine
//        binding.timeTextView.text = recordReceptionEntity.time
        binding.dosageTextView.text = recordReceptionEntity.dosage.toString()

//        binding.resultReceptionTextView.text = recordReceptionEntity.resultReception.toString()
//        when (recordReceptionEntity.resultReception) {
//            AppointmentStatus.UNKNOWN -> binding.resultReceptionTextView.setText(R.string.emoji_unknown)
//            AppointmentStatus.YES -> binding.resultReceptionTextView.setText(R.string.emoji_yes)
//            AppointmentStatus.NO -> binding.resultReceptionTextView.setText(R.string.emoji_no)
//        }
//        binding.iconMedicineTextView.text = recordReceptionEntity.typeMedicine.toString()
//        when (recordReceptionEntity.typeMedicine) {
//            TypeMedicine.PILL -> binding.iconMedicineTextView.setText(R.string.emoji_pill)
//            TypeMedicine.SYRINGE -> binding.iconMedicineTextView.setText(R.string.emoji_syringe)
//        }
    }

    init {
        itemView.setOnClickListener {
            onDetailsReceptionListener.invoke(receptionEntity)
        }
    }
}