package com.example.monitoringprescriptions.ui.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.monitoringprescriptions.App
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentDerailsReceptionBinding

class DerailsReceptionFragment : Fragment(R.layout.fragment_derails_reception) {

    private val app: App get() = requireActivity().application as App

    private var _binding: FragmentDerailsReceptionBinding? = null
    private var binding = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDerailsReceptionBinding.bind(view)
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
            DerailsReceptionFragment().apply {
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