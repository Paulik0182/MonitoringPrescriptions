package com.example.monitoringprescriptions.ui.schedule

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.monitoringprescriptions.App
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentScheduleBinding
import com.example.monitoringprescriptions.ui.reception.DayChangedFragment
import java.util.*

private const val TAG_FRAGMENT_CONTAINER_KEY = "TAG_FRAGMENT_CONTAINER_KEY"
private const val TEG_DAY_CHANGED_KEY = "TEG_DAY_CHANGED_KEY"

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val app: App get() = requireActivity().application as App

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentScheduleBinding.bind(view)

        initTabRecycler()
    }

    private fun initTabRecycler() {
        binding.tabRecyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.tabRecyclerView.adapter = TabDateAdapter {
            // пробросили данные наружу
            Toast.makeText(requireContext(), calendarToString(it), Toast.LENGTH_SHORT).show()

            // todo Вызываем обновление вложенного фрагмента
            onDayChanged(calendarToString(it))
        }

        // Для красивого скроллинга (элемент останавливается по центру)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.tabRecyclerView)
    }

    private fun onDayChanged(calendar: String) {
        // todo swapFragment(calendar)
        val fragment: Fragment = DayChangedFragment.newInstance(calendar)
        childFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragment, TEG_DAY_CHANGED_KEY)
            .commit()
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
//        fun onClickDayChanged(calendar: Calendar)
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
//                    putString(KEY, "key")

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}