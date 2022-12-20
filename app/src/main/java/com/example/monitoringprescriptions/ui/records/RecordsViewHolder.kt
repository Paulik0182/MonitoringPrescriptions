package com.example.monitoringprescriptions.ui.records

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemRecordReceptionBinding
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.v2.entities.AppointmentEntity
import com.example.monitoringprescriptions.utils.bpTimeFormatter

class RecordsViewHolder(
    parent: ViewGroup,
    showPopupMenu: () -> Unit,
    val context: Context,
    private val listener: (
        appointmentId: String,
        appointmentStatus: AppointmentStatus
    ) -> Unit,
//    recordListener: (ReceptionRecordPair) -> Unit
) : RecyclerView.ViewHolder(

    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_record_reception, parent, false)
) {

    private val binding: ItemRecordReceptionBinding = ItemRecordReceptionBinding.bind(itemView)
    private lateinit var appointmentEntity: AppointmentEntity

    fun bind(appointmentEntity: AppointmentEntity) {
        this.appointmentEntity = appointmentEntity

//        binding.nameMedicineTextView.text = appointmentEntity.nameMedicine
//        binding.typeMedicineTextView.text = appointmentEntity.prescribedMedicine

        binding.timeTextView.text = bpTimeFormatter.format(appointmentEntity.time)

//        binding.dosageTextView.text = appointmentEntity.dosage.toString()

        binding.resultReceptionTextView.text = appointmentEntity.status.toString()
        when (appointmentEntity.status) {
            AppointmentStatus.UNKNOWN -> binding.resultReceptionTextView.setText(R.string.emoji_unknown)
            AppointmentStatus.YES -> binding.resultReceptionTextView.setText(R.string.emoji_yes)
            AppointmentStatus.NO -> binding.resultReceptionTextView.setText(R.string.emoji_no)
        }
//        binding.iconMedicineTextView.text = appointmentEntity.typeMedicine.toString()
//        when (appointmentEntity.typeMedicine) {
//            TypeMedicine.PILL -> binding.iconMedicineTextView.setText(R.string.emoji_pill)
//            TypeMedicine.SYRINGE -> binding.iconMedicineTextView.setText(R.string.emoji_syringe)
//        }
    }

    init {
        itemView.setOnClickListener {
//            recordListener.invoke(receptionRecordPair)
        }

        binding.resultReceptionTextView.setOnClickListener {
//            showPopupMenu
            showPopupMenu(it)
        }
    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.result_popup_menu)
        popupMenu
            .setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.accepted_item -> {
                        listener.invoke(
                            appointmentEntity.id,
                            AppointmentStatus.YES
                        )
//                        binding.resultReceptionTextView.setText(R.string.emoji_yes)
                        Toast.makeText(
                            context,
                            "Принято",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    R.id.skipped_item -> {
                        listener.invoke(
                            appointmentEntity.id,
                            AppointmentStatus.NO
                        )
//                        binding.resultReceptionTextView.setText(R.string.emoji_no)
                        Toast.makeText(
                            context,
                            "Пропущено",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    else -> false
                }
            }
        popupMenu.setOnDismissListener {
            Toast.makeText(
                context, "onDismiss",
                Toast.LENGTH_SHORT
            ).show()
        }
        popupMenu.show()
    }

}