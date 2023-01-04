package com.example.monitoringprescriptions.ui.details

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentDetailsPrescriptionBinding
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.utils.bpDataFormatter
import com.example.monitoringprescriptions.utils.bpTimeFormatter
import com.example.monitoringprescriptions.utils.decimalForm
import com.example.monitoringprescriptions.utils.numberForm
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

private const val PRESCRIPTION_ID_ARG_KEY = "PRESCRIPTION_ID_ARG_KEY"
private const val DAY_IN_MS = 24 * 60 * 60 * 1000L

class DetailsPrescriptionFragment :
    Fragment(R.layout.fragment_details_prescription),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentDetailsPrescriptionBinding? = null
    private val binding get() = _binding!!

    private val calendarFromView = Calendar.getInstance()

    private lateinit var appointmentInPrescriptionAdapter: AppointmentInPrescriptionAdapter

    private val viewModel: DetailsPrescriptionViewModel by viewModel {
        parametersOf(requireArguments().getString(PRESCRIPTION_ID_ARG_KEY))
        // второй, боллее очевидный вариант
//        val prescriptionId = requireArguments().getString(PRESCRIPTION_ID_ARG_KEY)
//        return@viewModel parametersOf(prescriptionId)
    }

    private val unitMeasurementSpinnerLabels: Array<String> by lazy {
        resources.getStringArray(R.array.unit_measurement)
    }

    private val prescribedMedicineSpinnerLabels: Array<String> by lazy {
        resources.getStringArray(R.array.prescribed_medicine)
    }

    private val numberOfReceptionsPerDaySpinnerLabels: Array<String> by lazy {
        resources.getStringArray(R.array.number_of_receptions_per_day)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsPrescriptionBinding.bind(view)

        setHasOptionsMenu(true)

        saveDetailsReception()

        initPrescription()

        initDateView()

        viewModel.prescriptionListLiveDate.observe(viewLifecycleOwner) {
            appointmentInPrescriptionAdapter.setData(it)
        }

        viewModel.prescriptionLiveData.observe(viewLifecycleOwner) {
            fillPrescription(it)
        }

        binding.saveButton.setOnClickListener {
            saveDetailsReception()
            showCloseDialog("Выйти из рецепта?")
        }

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
    }

    private fun fillPrescription(prescription: PrescriptionEntity) {
        val dataStart = prescription.dateStart
        val numberOfDays = prescription.numberDaysTakingMedicine
        val resultData = (DAY_IN_MS * numberOfDays) - DAY_IN_MS

        val dosage = prescription.dosage
        val numberAdmissionsPerDay = prescription.numberAdmissionsPerDay
        val medicationsCourse = (numberAdmissionsPerDay.toInt() * dosage) * numberOfDays

        binding.dateStartTextView.setText(bpDataFormatter.format(dataStart))
        binding.dateEndTextView.setText(bpDataFormatter.format(prescription.dateEnd + resultData))
        binding.nameMedicineEditText.setText(prescription.nameMedicine)
        binding.dosageEditText.setText(decimalForm.format(dosage))
        binding.commentEditText.setText(prescription.comment)
        binding.numberOfDaysEditText.setText(numberForm.format(numberOfDays))
        binding.medicationsCourseEditText.text = medicationsCourse.toString()

        // выясняем какому элементу массива соответствует выставленное значение
        prescribedMedicineSpinnerLabels.forEachIndexed { i, medicine ->
            if (medicine == prescription.prescribedMedicine) {
                binding.prescribedMedicineSpinner.setSelection(i)
            }
        }

        unitMeasurementSpinnerLabels.forEachIndexed { i, unit ->
            if (unit == prescription.unitMeasurement) {
                binding.unitMeasurementSpinner.setSelection(i)
            }
        }

        numberOfReceptionsPerDaySpinnerLabels.forEachIndexed { i, perDay ->
            if (perDay == numberAdmissionsPerDay) {
                binding.numberAdmissionsPerDaySpinner.setSelection(i)
            }
        }
    }

    private fun saveDetailsReception() {

        // пример получения строки из Spinner (получаем позицию). эту строку и передаем
        val unitMeasurement =
            unitMeasurementSpinnerLabels[binding.unitMeasurementSpinner.selectedItemPosition]
        val prescribedMedicine =
            prescribedMedicineSpinnerLabels[binding.prescribedMedicineSpinner.selectedItemPosition]
        val numberOfReceptionsPerDay =
            numberOfReceptionsPerDaySpinnerLabels[binding.numberAdmissionsPerDaySpinner.selectedItemPosition]

        val dateStartMs = calendarFromView.timeInMillis
        val dateEndMs = calendarFromView.timeInMillis

        viewModel.onSaveDetails(
            // собираем все данные которые имеются
            dateStartMs,
            dateEndMs,
            binding.nameMedicineEditText.text.toString(),
            binding.dosageEditText.text.toString(),
            binding.commentEditText.text.toString(),
            prescribedMedicine,
            unitMeasurement,
            numberOfReceptionsPerDay,
            binding.numberOfDaysEditText.text.toString(),
            binding.medicationsCourseEditText.text.toString()
        )
    }

    // всплывающее окно (уточнее действия)!!!
    private fun showCloseDialog(message: String, runnable: Runnable? = null) {
        AlertDialog.Builder(requireContext())
            .setTitle(message)//сообщение на всплыв. окне
            .setPositiveButton("ДА") { dialogInterface: DialogInterface, i: Int ->
                runnable?.run()
                activity?.onBackPressed()//выход (кнопка назад)
                dialogInterface.dismiss()//закрываем окно. Обязательно!!
            }
            .setNegativeButton("НЕТ") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()//закрываем окно
            }
            .show()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_for_detailed_fragment, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val runnable = {
            viewModel.onDeleteClick()
        }

        when (item.itemId) {
            R.id.delete_icon_menu_items -> {
                showCloseDialog("Вы уверены что хотите удалить запись?", runnable)
            }
        }
        return super.onOptionsItemSelected(item)
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
                onSelect(labels[position])// здесь позиция котораю передали
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // todo
            }
        }
    }

    private fun initIntSpinner(spinner: Spinner, labels: Array<Int>, onSelect: (Int) -> Unit) {
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
                onSelect(labels[position])// здесь позиция котораю передали
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // todo
            }
        }
    }

    private fun initPrescription() {
        binding.recordsRecyclerView.layoutManager = LinearLayoutManager(context)
        appointmentInPrescriptionAdapter = AppointmentInPrescriptionAdapter(
            data = emptyList(),
            context = requireContext()
        )
        binding.recordsRecyclerView.adapter = appointmentInPrescriptionAdapter
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
        fun newInstance(prescriptionId: String) =
            DetailsPrescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(PRESCRIPTION_ID_ARG_KEY, prescriptionId)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                    "Время: ${
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
}