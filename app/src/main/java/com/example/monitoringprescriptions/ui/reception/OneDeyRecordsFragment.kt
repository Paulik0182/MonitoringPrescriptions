package com.example.monitoringprescriptions.ui.reception

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentDayChangedBinding
import com.example.monitoringprescriptions.utils.toUserString
import java.util.*

internal const val DEFAULT_DAY_KEY = -1
private const val DATE_KEY = "DAY_KEY"

class OneDeyRecordsFragment : Fragment(R.layout.fragment_day_changed) {

    private var _binding: FragmentDayChangedBinding? = null
    private val binding get() = _binding!!

    // достаем пришедший календарь и преобразуем его (делаем один раз и пользуемся в файле)
    private val currentCalendar: Calendar by lazy {
        extractTimeFromBundle(requireArguments())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDayChangedBinding.bind(view)


        binding.deyTimeTextView.text = currentCalendar.toUserString()
    }

    // достаем календарь и модифицируем его
    private fun extractTimeFromBundle(arguments: Bundle): Calendar {
        val timeInMs = arguments.getLong(DATE_KEY)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMs

        return calendar
    }

    interface Controller {
        //todo
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    /**
     * Calendar положить в Bundle нельзя, Calendar это сложный объект. время можно передать
     * в секундах, поэтому передаем Long и из Calendar достаем милисекунды.
     * Это универсальный способ передачи времени.
     */
    companion object {
        @JvmStatic
        fun newInstance(calendar: Calendar) =
            OneDeyRecordsFragment().apply {
                arguments = Bundle().apply {
                    putLong(DATE_KEY, calendar.timeInMillis)

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}