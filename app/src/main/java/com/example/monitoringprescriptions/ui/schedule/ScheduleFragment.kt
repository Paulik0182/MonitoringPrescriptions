package com.example.monitoringprescriptions.ui.schedule

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentScheduleBinding
import com.example.monitoringprescriptions.ui.appointments.AppointmentsFragment
import com.example.monitoringprescriptions.utils.toUserString
import java.util.*

private const val TAG_ONE_DAY_CHANGED_KEY = "TAG_DAY_CHANGED_KEY"

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private var currentCalendar: Calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentScheduleBinding.bind(view)

        initTabRecycler()
    }

    private fun initTabRecycler() {
        binding.tabRecyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        binding.tabRecyclerView.adapter = TabDateAdapter(currentCalendar) {
            currentCalendar = it
            // пробросили данные наружу
            Toast.makeText(requireContext(), it.toUserString(), Toast.LENGTH_SHORT).show()

            // todo Вызываем обновление вложенного фрагмента
            onDayChanged(it)
        }

        // Для красивого скроллинга (элемент останавливается по центру)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.tabRecyclerView)
    }

    private fun onDayChanged(calendar: Calendar) {
        val fragment: Fragment = AppointmentsFragment.newInstance(calendar)
        childFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragment, TAG_ONE_DAY_CHANGED_KEY)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}