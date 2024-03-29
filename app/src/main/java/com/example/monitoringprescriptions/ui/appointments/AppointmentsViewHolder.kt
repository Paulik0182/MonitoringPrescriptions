package com.example.monitoringprescriptions.ui.appointments

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
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import com.example.monitoringprescriptions.utils.bpTimeFormatter
import com.example.monitoringprescriptions.utils.toString

class AppointmentsViewHolder(
    parent: ViewGroup,
    showPopupMenu: () -> Unit,
    val context: Context,
    private val viewModel: AppointmentsViewModel,
    private val listener: (
        appointmentId: String,
        appointmentStatus: AppointmentStatus
    ) -> Unit,
    onPrescriptionClickListener: (AppointmentFullEntity) -> Unit
) : RecyclerView.ViewHolder(

    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_record_reception, parent, false)
) {

    private val binding: ItemRecordReceptionBinding = ItemRecordReceptionBinding.bind(itemView)
    private lateinit var appointmentFullEntity: AppointmentFullEntity

    fun bind(appointmentFullEntity: AppointmentFullEntity) {
        this.appointmentFullEntity = appointmentFullEntity

        binding.nameMedicineTextView.text = appointmentFullEntity.nameMedicine
        binding.typeMedicineTextView.text = appointmentFullEntity.typeMedicine.toString(context)

        binding.timeTextView.text = bpTimeFormatter.format(appointmentFullEntity.time)

        binding.dosageTextView.text = appointmentFullEntity.dosage.toString()

        binding.resultReceptionTextView.text = appointmentFullEntity.status.toString()
        when (appointmentFullEntity.status) {
            AppointmentStatus.UNKNOWN -> binding.resultReceptionTextView.setText(R.string.emoji_unknown)
            AppointmentStatus.YES -> binding.resultReceptionTextView.setText(R.string.emoji_yes)
            AppointmentStatus.NO -> binding.resultReceptionTextView.setText(R.string.emoji_no)
        }
        binding.iconMedicineTextView.text = appointmentFullEntity.typeMedicine.toString()
        when (appointmentFullEntity.typeMedicine) {
            TypeMedicine.TYPE_MED -> null
            TypeMedicine.PILL -> binding.iconMedicineTextView.setText(R.string.emoji_pill)
            TypeMedicine.SYRINGE -> binding.iconMedicineTextView.setText(R.string.emoji_syringe)
            TypeMedicine.POWDER -> binding.iconMedicineTextView.setText(R.string.emoji_powder)
            TypeMedicine.SUSPENSION -> binding.iconMedicineTextView.setText(R.string.emoji_suspension)
            TypeMedicine.OINTMENT -> binding.iconMedicineTextView.setText(R.string.emoji_ointment)
            TypeMedicine.TINCTURE -> binding.iconMedicineTextView.setText(R.string.emoji_tincture)
            TypeMedicine.DROPS -> binding.iconMedicineTextView.setText(R.string.emoji_drops)
            TypeMedicine.CANDLES -> binding.iconMedicineTextView.setText(R.string.emoji_candles)
        }
    }

    init {
        itemView.setOnClickListener {
            onPrescriptionClickListener.invoke(appointmentFullEntity)
        }

        itemView.setOnLongClickListener {
            showLongPopupMenu(it)
            true
        }

        binding.resultReceptionTextView.setOnClickListener {
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
                            appointmentFullEntity.appointmentId,
                            AppointmentStatus.YES
                        )
                        Toast.makeText(
                            context,
                            "Принято",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    R.id.skipped_item -> {
                        listener.invoke(
                            appointmentFullEntity.appointmentId,
                            AppointmentStatus.NO
                        )
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

    private fun showLongPopupMenu(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.action_long_press_popup_menu)
        popupMenu
            .setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.delete_item -> {
                        onDeleteClick()

                        true
                    }
                    else -> false
                }
            }
        popupMenu.show()
    }

    private fun onDeleteClick() {
        Toast.makeText(context, "Запись удалена", Toast.LENGTH_SHORT).show()
        viewModel.onDeleteAppointment(appointmentFullEntity)
    }

}