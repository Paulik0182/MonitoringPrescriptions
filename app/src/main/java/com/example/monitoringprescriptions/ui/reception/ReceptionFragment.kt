package com.example.monitoringprescriptions.ui.reception

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentReceptionBinding
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.domain.repos.ReceptionRepo
import org.koin.android.ext.android.inject

class ReceptionFragment : Fragment(R.layout.fragment_reception) {

    private var _binding: FragmentReceptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ReceptionAdapter

    private val receptionRepo: ReceptionRepo by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReceptionBinding.bind(view)

        initView()

        adapter.setData(receptionRepo.getReceptionList())

    }

    private fun initView() {
        binding.cardioRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ReceptionAdapter(
            data = emptyList(),
            onDetailsReceptionListener = {
                //todo
            }
        )
        binding.cardioRecyclerView.adapter = adapter
    }

    interface Controller {
        fun openDetailsReception(receptionEntity: ReceptionEntity)
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ReceptionFragment().apply {
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