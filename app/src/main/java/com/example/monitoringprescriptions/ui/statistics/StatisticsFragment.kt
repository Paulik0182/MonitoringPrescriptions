package com.example.monitoringprescriptions.ui.statistics

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.monitoringprescriptions.App
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val app: App get() = requireActivity().application as App

    private var _binding: FragmentStatisticsBinding? = null
    private var binding = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentStatisticsBinding.bind(view)
    }

    interface Controller {
        //TODO
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            StatisticsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}