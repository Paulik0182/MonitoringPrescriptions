package com.example.monitoringprescriptions.ui.appointments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentOneDeyAppointmentsBinding
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

private const val DATE_KEY = "DAY_KEY"

class AppointmentsFragment : Fragment(R.layout.fragment_one_dey_appointments) {

    private var _binding: FragmentOneDeyAppointmentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppointmentsViewModel by viewModel {
        parametersOf(extractTimeFromBundle(requireArguments()))
    }

    private lateinit var adapter: AppointmentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOneDeyAppointmentsBinding.bind(view)

        initViews()

        updateData()

        viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
            getController().openDetailsReception(it)
        }

//        binding.fab.setOnClickListener {
//            getController().openCreateDetailsReception()
//        }

        viewModel.loaderVisibilityLiveData.observe(viewLifecycleOwner) {
            // todo показать - скрыть лоадер
        }
    }

    private fun updateData() {
        viewModel.appointmentsLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun initViews() {
        binding.recordsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AppointmentsAdapter(
            data = emptyList(),
            showPopupMenu = {
            },
            onPrescriptionClickListener = {
                viewModel.onPrescriptionClick(it)
            },
            context = requireContext()
        ) { appointmentId, appointmentStatus ->
            viewModel.onAppointmentSelected(
                appointmentId,
                appointmentStatus
            )
        }

        binding.recordsRecyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            viewModel.onTempCreateClick()
        }
    }

    // достаем календарь и модифицируем его
    private fun extractTimeFromBundle(arguments: Bundle): Calendar {
        val timeInMs = arguments.getLong(DATE_KEY)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMs

        return calendar
    }

    interface Controller {
        fun openDetailsReception(appointmentFullEntity: AppointmentFullEntity)
//        fun openCreateDetailsReception()
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
            AppointmentsFragment().apply {
                arguments = bundleOf(DATE_KEY to calendar.timeInMillis)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}