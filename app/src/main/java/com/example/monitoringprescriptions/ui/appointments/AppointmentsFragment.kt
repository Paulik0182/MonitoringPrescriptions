package com.example.monitoringprescriptions.ui.appointments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
private const val ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION = 0f
private const val TRANSPARENCY_VALUE_FOR_ANIMATION = 1f
private const val FAB_ROTATION_ANGLE_DEGREE = 180f
private const val ROTATION_CANDLERS_ANGLE_DEGREE_MINUS = -230f
private const val DROPS_CANDLERS_ANGLE_DEGREE_MINUS = -350f
private const val PILL_CANDLERS_ANGLE_DEGREE_MINUS = -470f
private const val OINTMENT_CANDLERS_ANGLE_DEGREE_MINUS = -590f
private const val POWDER_CANDLERS_ANGLE_DEGREE_MINUS = -710f
private const val SUSPENSION_CANDLERS_ANGLE_DEGREE_MINUS = -830f
private const val SYRINGE_CANDLERS_ANGLE_DEGREE_MINUS = -950f
private const val TINCTURE_CANDLERS_ANGLE_DEGREE_MINUS = -1070f
private const val TRANSPARENCY_OF_THE_BACKGROUND = 0.8f
private const val FAB_ANIMATION_DURATION = 800L

class AppointmentsFragment : Fragment(R.layout.fragment_one_dey_appointments) {

    private var _binding: FragmentOneDeyAppointmentsBinding? = null
    private val binding get() = _binding!!

    private var isFabOpen = false

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

        viewModel.loaderVisibilityLiveData.observe(viewLifecycleOwner) {
            // todo показать - скрыть лоадер
        }
    }

    private fun initFab() {

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
        }
        binding.optionPowderContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.POWDER, UnitsMeasurement.GRAM)
        }
        binding.optionSuspensionContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.SUSPENSION, UnitsMeasurement.PACKAGE)
        }
        binding.optionSyringeContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.SYRINGE, UnitsMeasurement.MILLILITER)
        }
        binding.optionTinctureContainer.setOnClickListener {
            getController().openNewPrescription(TypeMedicine.TINCTURE, UnitsMeasurement.MILLILITER)
        }
    }

    private fun processingClicksOnFab() {
        binding.fab.setOnClickListener {
            isFabOpen = !isFabOpen
            if (isFabOpen) {

                animatedOpeningListOnClickingFab()

                configuringContainerParameters(
                    binding.optionCandlesContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

                configuringContainerParameters(
                    binding.optionDropsContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

                configuringContainerParameters(
                    binding.optionPillContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

                configuringContainerParameters(
                    binding.optionOintmentContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

                configuringContainerParameters(
                    binding.optionPowderContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

                configuringContainerParameters(
                    binding.optionSuspensionContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

                configuringContainerParameters(
                    binding.optionSyringeContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

                configuringContainerParameters(
                    binding.optionTinctureContainer,
                    TRANSPARENCY_VALUE_FOR_ANIMATION,
                    true
                )

            } else {
                animatedСlosingListOnClickingFab()

                configuringContainerParameters(
                    binding.optionCandlesContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )

                configuringContainerParameters(
                    binding.optionDropsContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )

                configuringContainerParameters(
                    binding.optionPillContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )

                configuringContainerParameters(
                    binding.optionOintmentContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )

                configuringContainerParameters(
                    binding.optionPowderContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )

                configuringContainerParameters(
                    binding.optionSuspensionContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )

                configuringContainerParameters(
                    binding.optionSyringeContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )

                configuringContainerParameters(
                    binding.optionTinctureContainer,
                    ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
                    false
                )
            }
        }
    }

    private fun configuringContainerParameters(
        tinctureContainer: LinearLayout,
        alpha: Float,
        actionClick: Boolean
    ) {
        tinctureContainer
            .animate()
            .alpha(alpha)
            .setDuration(FAB_ANIMATION_DURATION)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        tinctureContainer.isClickable = actionClick
                    }
                }
            )
    }

    private fun animatedOpeningListOnClickingFab() {

        ObjectAnimator.ofFloat(
            binding.fab,
            View.ROTATION,
            ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION,
            FAB_ROTATION_ANGLE_DEGREE
        ).setDuration(FAB_ANIMATION_DURATION).start()

        processingAnimatedObject(
            binding.optionCandlesContainer,
            ROTATION_CANDLERS_ANGLE_DEGREE_MINUS
        )

        processingAnimatedObject(
            binding.optionDropsContainer,
            DROPS_CANDLERS_ANGLE_DEGREE_MINUS
        )

        processingAnimatedObject(
            binding.optionPillContainer,
            PILL_CANDLERS_ANGLE_DEGREE_MINUS
        )

        processingAnimatedObject(
            binding.optionOintmentContainer,
            OINTMENT_CANDLERS_ANGLE_DEGREE_MINUS
        )

        processingAnimatedObject(
            binding.optionPowderContainer,
            POWDER_CANDLERS_ANGLE_DEGREE_MINUS
        )

        processingAnimatedObject(
            binding.optionSuspensionContainer,
            SUSPENSION_CANDLERS_ANGLE_DEGREE_MINUS
        )

        processingAnimatedObject(
            binding.optionSyringeContainer,
            SYRINGE_CANDLERS_ANGLE_DEGREE_MINUS
        )

        processingAnimatedObject(
            binding.optionTinctureContainer,
            TINCTURE_CANDLERS_ANGLE_DEGREE_MINUS
        )

        ObjectAnimator.ofFloat(
            binding.transparentBackground,
            View.ALPHA,
            TRANSPARENCY_OF_THE_BACKGROUND
        ).setDuration(FAB_ANIMATION_DURATION).start()
    }

    private fun processingAnimatedObject(
        container: LinearLayout,
        animationEffectValue: Float = ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION
    ) {

        ObjectAnimator.ofFloat(
            container,
            View.TRANSLATION_Y,
            animationEffectValue
        ).setDuration(FAB_ANIMATION_DURATION).start()
    }

    private fun animatedСlosingListOnClickingFab() {
        ObjectAnimator.ofFloat(
            binding.fab,
            View.ROTATION,
            FAB_ROTATION_ANGLE_DEGREE,
            ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION
        ).setDuration(FAB_ANIMATION_DURATION).start()

        processingAnimatedObject(binding.optionCandlesContainer)

        processingAnimatedObject(binding.optionDropsContainer)

        processingAnimatedObject(binding.optionPillContainer)

        processingAnimatedObject(binding.optionOintmentContainer)

        processingAnimatedObject(binding.optionPowderContainer)

        processingAnimatedObject(binding.optionSuspensionContainer)

        processingAnimatedObject(binding.optionSyringeContainer)

        processingAnimatedObject(binding.optionTinctureContainer)

        ObjectAnimator.ofFloat(
            binding.transparentBackground,
            View.ALPHA,
            ZERO_TRANSPARENCY_VALUE_FOR_ANIMATION
        ).setDuration(FAB_ANIMATION_DURATION).start()
    }

    private fun updateData() {
        viewModel.appointmentsLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun initViews() {
        processingClicksOnFab()
        initFab()

        binding.recordsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AppointmentsAdapter(
            data = emptyList(),
            showPopupMenu = {},
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