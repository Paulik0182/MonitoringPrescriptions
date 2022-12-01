package com.example.monitoringprescriptions.ui.reception

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentDayChangedBinding
import java.util.*

internal const val DEFAULT_DAY_KEY = -1
private const val DAY_KEY = "DAY_KEY"

class DayChangedFragment : Fragment(R.layout.fragment_day_changed) {

    private var _binding: FragmentDayChangedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDayChangedBinding.bind(view)

//        var calendar =  arguments?.getSerializable(DAY_KEY) ?: DEFAULT_DAY_KEY

        binding.deyTimeTextView.text =
            (arguments?.getString(DAY_KEY) ?: DEFAULT_DAY_KEY).toString()

//        binding.deyTimeTextView.text = calendarToString( calendar.to(Calendar.DAY_OF_MONTH))
    }

    private fun calendarToString(calendar: Calendar): String {
        return "${
            calendar.get(
                Calendar.DAY_OF_MONTH
            )
        } ${
            calendar.get(
                Calendar.MONDAY
            )
        } ${
            calendar.get(
                Calendar.YEAR
            )
        }"
    }

    interface Controller {
        //todo
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        @JvmStatic
        fun newInstance(calendar: String) =
            DayChangedFragment().apply {
                arguments = Bundle().apply {
                    putString(DAY_KEY, calendar)

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}