package com.example.monitoringprescriptions.ui.details.create

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentNewPrescriptionBinding
import com.example.monitoringprescriptions.ui.details.CreationPrescriptionScreenErrors
import com.example.monitoringprescriptions.utils.bpDataFormatter
import com.example.monitoringprescriptions.utils.bpTimeFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class NewPrescriptionFragment :
    Fragment(R.layout.fragment_new_prescription),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentNewPrescriptionBinding? = null
    private val binding get() = _binding!!

    private val calendarFromView = Calendar.getInstance()

    private val unitMeasurementSpinnerLabels: Array<String> by lazy {
        resources.getStringArray(R.array.unit_measurement)
    }

    private val prescribedMedicineSpinnerLabels: Array<String> by lazy {
        resources.getStringArray(R.array.prescribed_medicine)
    }

    private val numberOfReceptionsPerDaySpinnerLabels: Array<String> by lazy {
        resources.getStringArray(R.array.number_of_receptions_per_day)
    }

    private val viewModel: NewPrescriptionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewPrescriptionBinding.bind(view)

        binding.saveButton.setOnClickListener {
            saveNewReception()
        }

        initDateView()

        initStringSpinner(binding.unitMeasurementSpinner, unitMeasurementSpinnerLabels) {
            viewModel.onUnitMeasurementSelectSpinner(it)
        }

        initStringSpinner(binding.prescribedMedicineSpinner, prescribedMedicineSpinnerLabels) {
            viewModel.onMedicineSelectSpinner(it)
        }

        initStringSpinner(
            binding.numberAdmissionsPerDaySpinner,
            numberOfReceptionsPerDaySpinnerLabels
        ) {
            viewModel.onNumberAdmissionsPerDaySelectSpinner(it)
        }

        viewModel.errorsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                // todo ???????????????? ?? ?????????????? ????????????
                is CreationPrescriptionScreenErrors.DateStartError -> {
                    (binding.dateStartTextView.error as TextView).error = it.errorsMassage
                }
                is CreationPrescriptionScreenErrors.NumberDaysTakingMedicineError -> {
                    binding.numberDaysTakingMedicineEditText.error = it.errorsMassage
                }
                is CreationPrescriptionScreenErrors.NameMedicineError -> {
                    binding.nameMedicineEditText.error = it.errorsMassage
                }
                is CreationPrescriptionScreenErrors.PrescribedMedicineError -> {
                    (binding.prescribedMedicineSpinner.selectedView as TextView).error =
                        it.errorsMassage
                }
                is CreationPrescriptionScreenErrors.DosageError -> {
                    binding.dosageEditText.error = it.errorsMassage
                }
                // todo ???????????????? ?? ?????????????? ???????????? ????????????
                is CreationPrescriptionScreenErrors.UnitMeasurementError -> {
                    (binding.unitMeasurementSpinner.selectedView as TextView).error =
                        it.errorsMassage
                }
                // todo ???????????????????????????? ???????????????? ???? ???????????????????????? ???????????????? ???????????????? ?? ?????????????? ???????????? ????????????
                is CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError -> {
                    (binding.unitMeasurementSpinner.selectedView as TextView).error =
                        it.errorsMassage
                }

                // todo ???????????????? ?? ?????????????? ???????????? ????????????
                is CreationPrescriptionScreenErrors.NumberAdmissionsPerDayError -> {
                    (binding.numberAdmissionsPerDaySpinner.selectedView as TextView).error =
                        it.errorsMassage
                }
                else -> {

                }
            }
        }

        viewModel.dialogLiveData.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle(it.massage)
                .setPositiveButton("????") { dialogInterface: DialogInterface, _ ->
                    dialogInterface.dismiss()
                }.show()
        }
    }

    private fun saveNewReception() {
        // ???????????? ?????????????????? ???????????? ???? Spinner (???????????????? ??????????????). ?????? ???????????? ?? ????????????????
        val unitMeasurement =
            unitMeasurementSpinnerLabels[binding.unitMeasurementSpinner.selectedItemPosition]
        val prescribedMedicine =
            prescribedMedicineSpinnerLabels[binding.prescribedMedicineSpinner.selectedItemPosition]
        val numberAdmissionsPerDay =
            numberOfReceptionsPerDaySpinnerLabels[binding.numberAdmissionsPerDaySpinner.selectedItemPosition]

        viewModel.onSaveNewPrescription(
            // ???????????????? ?????? ???????????? ?????????????? ??????????????
            nameMedicine = binding.nameMedicineEditText.text.toString(),
            prescribedMedicine = prescribedMedicine,
            dosage = binding.dosageEditText.text.toString().toFloatOrNull(),
            unitMeasurement = unitMeasurement,
            comment = binding.commentEditText.text.toString(),
            dateStart = calendarFromView.timeInMillis,
            numberDaysTakingMedicine = binding.numberDaysTakingMedicineEditText.text.toString()
                .toIntOrNull(), //todo ?????????????????? ???????????????? ???? ????????????????????,
            numberAdmissionsPerDay = numberAdmissionsPerDay,
            medicationsCourse = binding.medicationsCourseEditText.text.toString().toFloatOrNull()
                ?: 0F //todo ?????????????????? ???????????????? ???? ????????????????????
        )
    }

    private fun initStringSpinner(
        spinner: Spinner,
        labels: Array<String>,
        onSelect: (String) -> Unit
    ) {
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            labels
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onSelect(labels[position])// ?????????? ?????????????? ?????????????? ????????????????
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // todo
            }
        }
    }

    interface Controller {
        fun onDataChanged()
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewPrescriptionFragment()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendarFromView.set(Calendar.YEAR, year)
        calendarFromView.set(Calendar.MONTH, month)
        calendarFromView.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val calendar = Calendar.getInstance()

        TimePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendarFromView.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendarFromView.set(Calendar.MINUTE, minute)

        binding.dateStartTextView.text =
            "${
                bpDataFormatter.format(
                    calendarFromView.time
                )
            }\n " +
                    "??????????: ${
                        bpTimeFormatter.format(
                            calendarFromView.time
                        )
                    }"
    }

    private fun initDateView() {
        binding.dateStartTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentDay = calendar.get(Calendar.DAY_OF_YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)

            DatePickerDialog(requireContext(), this, currentYear, currentMonth, currentDay).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}