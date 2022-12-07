package com.example.monitoringprescriptions.ui.schedule

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ItemDayTabBinding
import java.util.*

class TabViewHolder(
    parent: ViewGroup,
    val onItemClick: (Calendar, Int) -> Unit // Обработка клика
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_day_tab, parent, false)
) {
    private val binding: ItemDayTabBinding = ItemDayTabBinding.bind(itemView)

    fun bind(calendar: Calendar, isSelected: Boolean) {
        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]

        binding.headerTextView.text = when (dayOfWeek) {
            Calendar.SUNDAY -> "Вс"
            Calendar.MONDAY -> "Пн"
            Calendar.TUESDAY -> "Вт"
            Calendar.WEDNESDAY -> "Ср"
            Calendar.THURSDAY -> "Чт"
            Calendar.FRIDAY -> "Пт"
            Calendar.SATURDAY -> "Сб"
            else -> throw RuntimeException()
        }

        // Дни (дни недели с конкретными днями)
        binding.dateTextView.text = calendar.get(Calendar.DAY_OF_MONTH).toString()

        binding.selectionView.isInvisible = !isSelected

        // При нажатии на день недели - выделяем конкретный день
        val textStyle = if (isSelected) Typeface.BOLD else Typeface.NORMAL
        binding.headerTextView.setTypeface(null, textStyle)
        binding.dateTextView.setTypeface(null, textStyle)

        // Для обработки надатия на день недели (передали календарь и позицию)
        itemView.setOnClickListener {
            onItemClick(calendar, bindingAdapterPosition)
        }
    }
}