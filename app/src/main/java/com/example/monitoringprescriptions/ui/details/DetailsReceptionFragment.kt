package com.example.monitoringprescriptions.ui.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentDerailsReceptionBinding
import com.example.monitoringprescriptions.domain.entities.ReceptionRecordPair
import com.example.monitoringprescriptions.domain.interactors.RecordsInteractor
import org.koin.android.ext.android.inject

private const val DETAILS_RECEPTION_KEY = "DETAILS_RECEPTION_KEY"

class DetailsReceptionFragment : Fragment(R.layout.fragment_derails_reception) {

    private var _binding: FragmentDerailsReceptionBinding? = null
    private val binding get() = _binding!!

    private val recordsInteractor: RecordsInteractor by inject()

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
        fun newInstance(receptionRecordPair: ReceptionRecordPair) =
            DetailsReceptionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DETAILS_RECEPTION_KEY, receptionRecordPair)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}