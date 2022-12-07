package com.example.monitoringprescriptions.ui.schedule

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TabDateAdapter(
    val calendar: Calendar = Calendar.getInstance(),
    private val onDayChanged: (Calendar) -> Unit // Для пробрасывания информации наружу
) : RecyclerView.Adapter<TabViewHolder>() {

    private val todayPosition = itemCount / 2 // тоесть сегодня должнобыть посередине
    private var selectionPosition = todayPosition // поумолчанию позиция на старте

    init {
// позиция по календарю (выбронная позиция)
        selectionPosition = getPosition(calendar)

    }

    // Для подчеркивания дня недели (получаем RecyclerView)
    // метод вызывается при старте
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.scrollToPosition(selectionPosition)

        //когда view пересоздается, отправляется эти данные (календарь и позиция)
        onItemClick(calendar, selectionPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        return TabViewHolder(
            parent,
            this::onItemClick // Обработка нажатия клика
        )
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.bind(getItem(position), selectionPosition == position)
    }

    private fun getItem(position: Int): Calendar {
        val dayShift = position - todayPosition
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, dayShift)
        return calendar
    }

    private fun getPosition(calendar: Calendar): Int {
        // взяли сегодняшний календарь
        val todayCalendar = Calendar.getInstance()

        // сбрасываем время на 12 часов так как между нажатием проходит некоторое время
        // и результат будет не верный за счет округления
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 12)
        calendar.set(Calendar.SECOND, 12)
        calendar.set(Calendar.MILLISECOND, 12)

        todayCalendar.set(Calendar.HOUR_OF_DAY, 12)
        todayCalendar.set(Calendar.MINUTE, 12)
        todayCalendar.set(Calendar.SECOND, 12)
        todayCalendar.set(Calendar.MILLISECOND, 12)

        // вычлили миллисекунды
        val diffMs = calendar.timeInMillis - todayCalendar.timeInMillis
        // превращаем миллисекунды в дни (берем разницу от сегодняшнего дня, всего день опережает гна столько)
        val diffDays = (diffMs / 1000 / 60 / 60 / 24).toInt()
        // на сколько календарь больше чем сегодня
        return todayPosition + diffDays
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // Для бесконечного скроллинга

    // Обработка нажатия клика
    private fun onItemClick(calendar: Calendar, position: Int) {

        val oldPosition = selectionPosition

        selectionPosition = position

        // Обнавляем новую позицию
        notifyItemChanged(oldPosition)
        // Обновляем старую позицию
        notifyItemChanged(position)

        // Для пробрасывания информации наружу
        // Сообщаем наружу, что изменился выбраный день.
        onDayChanged(calendar)
    }
}