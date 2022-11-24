package com.example.monitoringprescriptions.ui.reception

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemRecordReceptionBinding
import com.example.monitoringprescriptions.domain.Emoji
import com.example.monitoringprescriptions.domain.EmojiIconMedicine
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
        binding.typeMedicineTextView.text = recordReceptionEntity.typeMedicine
        binding.timeTextView.text = recordReceptionEntity.time
        binding.dosageTextView.text = recordReceptionEntity.dosage.toString()

        binding.resultReceptionTextView.text = recordReceptionEntity.resultReception.toString()
        when (recordReceptionEntity.resultReception) {
            Emoji.UNKNOWN -> binding.resultReceptionTextView.setText(R.string.emoji_unknown)
            Emoji.YES -> binding.resultReceptionTextView.setText(R.string.emoji_yes)
            Emoji.NO -> binding.resultReceptionTextView.setText(R.string.emoji_no)
        }
        binding.iconMedicineTextView.text = recordReceptionEntity.iconMedicine.toString()
        when (recordReceptionEntity.iconMedicine) {
            EmojiIconMedicine.PILL -> binding.iconMedicineTextView.setText(R.string.emoji_pill)
            EmojiIconMedicine.SYRINGE -> binding.iconMedicineTextView.setText(R.string.emoji_syringe)
        }
    }

    init {
        itemView.setOnClickListener {
            onDetailsReceptionListener.invoke(receptionEntity)
        }
    }
}