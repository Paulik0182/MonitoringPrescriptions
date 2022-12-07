package com.example.monitoringprescriptions.ui.records

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentOneDeyRecordsBinding
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

private const val DATE_KEY = "DAY_KEY"

class OneDeyRecordsFragment : Fragment(R.layout.fragment_one_dey_records) {

    private var _binding: FragmentOneDeyRecordsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecordsViewModel by viewModel {
        parametersOf(extractTimeFromBundle(requireArguments()))
    }

    private lateinit var adapter: RecordsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOneDeyRecordsBinding.bind(view)

        initViews()

        viewModel.receptionRecordPair.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
            getController().openDetailsReception(it)
        }

        viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
            // todo показать - скрыть лоадер
        }
    }

    private fun initViews() {
        binding.recordsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecordsAdapter(
            data = emptyList(),
            showPopupMenu = {
            },
            context = requireContext()
        ) { reception ->
            viewModel.onReceptionClick(reception)
        }
        binding.recordsRecyclerView.adapter = adapter
    }

    // достаем календарь и модифицируем его
    private fun extractTimeFromBundle(arguments: Bundle): Calendar {
        val timeInMs = arguments.getLong(DATE_KEY)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMs

        return calendar
    }

    interface Controller {
        fun openDetailsReception(receptionRecordPair: ReceptionRecordPair)
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
                arguments = bundleOf(DATE_KEY to calendar.timeInMillis)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}