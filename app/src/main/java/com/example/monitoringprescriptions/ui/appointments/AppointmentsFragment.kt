package com.example.monitoringprescriptions.ui.appointments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentOneDeyAppointmentsBinding
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

private const val DATE_KEY = "DAY_KEY"

class AppointmentsFragment : Fragment(R.layout.fragment_one_dey_appointments) {

    private var _binding: FragmentOneDeyAppointmentsBinding? = null
    private val binding get() = _binding!!

    var isFlag = false
    var duration = 1000L

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
            getController().openDetailsPrescription(it)
        }

        processingClicksOnFab()

        viewModel.loaderVisibilityLiveData.observe(viewLifecycleOwner) {
            // todo показать - скрыть лоадер
        }

        openNewNamedPrescription()
    }

    private fun openNewNamedPrescription() {
        binding.optionCandlesContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.CANDLES, UnitsMeasurement.PIECES)
        }
        binding.optionDropsContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.DROPS, UnitsMeasurement.DROP)
        }
        binding.optionPillContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.PILL, UnitsMeasurement.PIECES)

        }
        binding.optionOintmentContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.OINTMENT, UnitsMeasurement.GRAM)
//            viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
//
//            }
        }
        binding.optionPowderContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.POWDER, UnitsMeasurement.GRAM)
//            viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
//
//            }
        }
        binding.optionSuspensionContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.SUSPENSION, UnitsMeasurement.PACKAGE)
//            viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
//
//            }
        }
        binding.optionSyringeContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.SYRINGE, UnitsMeasurement.MILLILITER)
//            viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
//
//            }
        }
        binding.optionTinctureContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.TINCTURE, UnitsMeasurement.MILLILITER)
//            viewModel.selectedReceptionLiveData.observe(viewLifecycleOwner) {
//
//            }
        }
    }

    private fun processingClicksOnFab() {
        binding.fab.setOnClickListener {
            isFlag = !isFlag
            if (isFlag) {
                ObjectAnimator.ofFloat(binding.plusImageView, View.ROTATION, 0f, 675f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionCandlesContainer, View.TRANSLATION_Y, -180f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionDropsContainer, View.TRANSLATION_Y, -280f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionPillContainer, View.TRANSLATION_Y, -380f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOintmentContainer, View.TRANSLATION_Y, -480f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionPowderContainer, View.TRANSLATION_Y, -580f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionSuspensionContainer, View.TRANSLATION_Y, -680f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionSyringeContainer, View.TRANSLATION_Y, -780f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTinctureContainer, View.TRANSLATION_Y, -880f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0.8f)
                    .setDuration(duration).start()

                binding.optionCandlesContainer.animate().alpha(1f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionCandlesContainer.isClickable = true
                            }
                        }
                    )
                binding.optionDropsContainer.animate().alpha(1f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionDropsContainer.isClickable = true
                        }
                    }
                )

                binding.optionPillContainer.animate().alpha(1f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionPillContainer.isClickable = true
                        }
                    }
                )

                binding.optionOintmentContainer.animate().alpha(1f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionOintmentContainer.isClickable = true
                            }
                        }
                    )

                binding.optionPowderContainer.animate().alpha(1f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionPowderContainer.isClickable = true
                        }
                    }
                )

                binding.optionSuspensionContainer.animate().alpha(1f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionSuspensionContainer.isClickable = true
                            }
                        }
                    )

                binding.optionSyringeContainer.animate().alpha(1f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionSyringeContainer.isClickable = true
                            }
                        }
                    )

                binding.optionTinctureContainer.animate().alpha(1f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionTinctureContainer.isClickable = true
                            }
                        }
                    )
            } else {
                ObjectAnimator.ofFloat(binding.plusImageView, View.ROTATION, 675f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionCandlesContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionDropsContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionPillContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOintmentContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionPowderContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionSuspensionContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionSyringeContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTinctureContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0f)
                    .setDuration(duration).start()

                binding.optionCandlesContainer.animate().alpha(0f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionCandlesContainer.isClickable = false
                            }
                        }
                    )

                binding.optionDropsContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionDropsContainer.isClickable = false
                        }
                    }
                )

                binding.optionPillContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionPillContainer.isClickable = false
                        }
                    }
                )

                binding.optionOintmentContainer.animate().alpha(0f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionOintmentContainer.isClickable = false
                            }
                        }
                    )

                binding.optionPowderContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionPowderContainer.isClickable = false
                        }
                    }
                )

                binding.optionSuspensionContainer.animate().alpha(0f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionSuspensionContainer.isClickable = false
                            }
                        }
                    )

                binding.optionSyringeContainer.animate().alpha(0f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionSyringeContainer.isClickable = false
                            }
                        }
                    )

                binding.optionTinctureContainer.animate().alpha(0f).setDuration(duration)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.optionTinctureContainer.isClickable = false
                            }
                        }
                    )
            }
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
            context = requireContext(),
            viewModel = viewModel
        ) { appointmentId, appointmentStatus ->
            viewModel.onAppointmentSelected(
                appointmentId,
                appointmentStatus
            )
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
        fun openDetailsPrescription(appointmentFullEntity: AppointmentFullEntity)
        fun openNewPrescription(typeMedicine: TypeMedicine, unitMeasurement: UnitsMeasurement)
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