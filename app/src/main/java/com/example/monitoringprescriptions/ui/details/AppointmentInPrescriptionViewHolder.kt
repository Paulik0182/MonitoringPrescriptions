package com.example.monitoringprescriptions.ui.details

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemAssignedBinding
import com.example.monitoringprescriptions.domain.AppointmentStatus
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import com.example.monitoringprescriptions.utils.bpDataFormatter
import com.example.monitoringprescriptions.utils.bpTimeFormatter

class AppointmentInPrescriptionViewHolder(
    parent: ViewGroup,
    val context: Context,
    private val viewModel: DetailsPrescriptionViewModel
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_assigned, parent, false)
) {

    private val binding: ItemAssignedBinding = ItemAssignedBinding.bind(itemView)
    private lateinit var appointmentEntity: AppointmentEntity

    fun bind(appointmentEntity: AppointmentEntity) {
        this.appointmentEntity = appointmentEntity

        binding.dateTextView.text = bpDataFormatter.format(appointmentEntity.time)
        binding.timeTextView.text = bpTimeFormatter.format(appointmentEntity.time)

        binding.nameMedicineTextView.text = appointmentEntity.status.toString()
        when (appointmentEntity.status) {
            AppointmentStatus.UNKNOWN -> binding.nameMedicineTextView.text =
                context.getString(R.string.is_unknown)
            AppointmentStatus.YES -> {
                binding.nameMedicineTextView.text = context.getString(R.string.accepted_menu)
                binding.nameMedicineTextView.setTextColor(Color.BLUE)
                binding.dateTextView.setTextColor(Color.BLUE)
                binding.timeTextView.setTextColor(Color.BLUE)
            }
            AppointmentStatus.NO -> {
                binding.nameMedicineTextView.text = context.getString(R.string.skipped_menu)
                binding.nameMedicineTextView.setTextColor(Color.RED)
                binding.dateTextView.setTextColor(Color.RED)
                binding.timeTextView.setTextColor(Color.RED)
            }
            else -> Unit
        }
    }

    init {
        itemView.setOnLongClickListener {
            showPopupMenu(it)
            true
        }
    }

    private fun showPopupMenu(view: View) {
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
        viewModel.onDeleteAppointment(appointmentEntity)
    }
}
