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

        errorMassage()

        viewModel.prescriptionListLiveDate.observe(viewLifecycleOwner) {
            appointmentInPrescriptionAdapter.setData(it)
        }

        viewModel.prescriptionLiveData.observe(viewLifecycleOwner) {
            fillPrescription(it)
        }

        binding.saveButton.setOnClickListener {
            saveDetailsReception()
//            showCloseDialog("Выйти из рецепта?") // todo времмено (замннить на LiveDate)
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

        // todo Дублирующий код
        viewModel.dialogLiveData.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle(it.massage)
                .setPositiveButton("ОК") { dialogInterface: DialogInterface, _ ->
                    dialogInterface.dismiss()
                }.show()
        }

        // todo Дублирующий код (функция -> showCloseDialog в данном классе)
        viewModel.closeDialogLiveData.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle(it.massage)//сообщение на всплыв. окне
                .setPositiveButton("ДА") { dialogInterface: DialogInterface, i: Int ->
                    it.runnable?.run()
                    activity?.onBackPressed()//выход (кнопка назад)
                    dialogInterface.dismiss()//закрываем окно. Обязательно!!
                }
                .setNegativeButton("НЕТ") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()//закрываем окно
                }
                .show()
        }
    }

    private fun fillPrescription(prescription: PrescriptionEntity) {
        val dateStart = prescription.dateStart
        val numberDaysTakingMedicine = prescription.numberDaysTakingMedicine
        val dateEnd = dateStart + (DAY_IN_MS * numberDaysTakingMedicine) - DAY_IN_MS

        val dosage = prescription.dosage
        val numberAdmissionsPerDay = prescription.numberAdmissionsPerDay
        val medicationsCourse = (numberAdmissionsPerDay.toInt() * dosage) * numberDaysTakingMedicine

        binding.dateStartTextView.text = bpDataFormatter.format(dateStart)
        binding.dateEndTextView.text = bpDataFormatter.format(dateEnd)
        binding.nameMedicineEditText.setText(prescription.nameMedicine)
        binding.dosageEditText.setText(decimalForm.format(dosage))
        binding.commentEditText.setText(prescription.comment)
        binding.numberDaysTakingMedicineEditText.setText(numberForm.format(numberDaysTakingMedicine))
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
        val numberAdmissionsPerDay =
            numberOfReceptionsPerDaySpinnerLabels[binding.numberAdmissionsPerDaySpinner.selectedItemPosition]

        val dateStartMs = calendarFromView.timeInMillis

        viewModel.onSaveDetails(
            // собираем все данные которые имеются
            nameMedicine = binding.nameMedicineEditText.text.toString(),
            prescribedMedicine = prescribedMedicine,
            dosage = binding.dosageEditText.text.toString().toFloatOrNull(),
            unitMeasurement = unitMeasurement,
            comment = binding.commentEditText.text.toString(),
            dateStart = dateStartMs,
            numberDaysTakingMedicine = binding.numberDaysTakingMedicineEditText.text.toString()
                .toIntOrNull(),
            numberAdmissionsPerDay = numberAdmissionsPerDay,
            medicationsCourse = binding.medicationsCourseEditText.text.toString().toFloatOrNull()
                ?: 0F
        )
    }

    // todo Дублирующий код (в NewPrescriptionFragment и данном классе)
    private fun errorMassage() {
        viewModel.errorsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                // todo проблема с показом ошибки
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
                // todo проблема с показом текста ошибки
                is CreationPrescriptionScreenErrors.UnitMeasurementError -> {
                    (binding.unitMeasurementSpinner.selectedView as TextView).error =
                        it.errorsMassage
                }
                // todo Дополнительная проверка на соответствие значений проблема с показом текста ошибки
                is CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError -> {
                    (binding.unitMeasurementSpinner.selectedView as TextView).error =
                        it.errorsMassage
                }

                // todo проблема с показом текста ошибки
                is CreationPrescriptionScreenErrors.NumberAdmissionsPerDayError -> {
                    (binding.numberAdmissionsPerDaySpinner.selectedView as TextView).error =
                        it.errorsMassage
                }
                else -> {

                }
            }
        }
    }

    // всплывающее окно (уточнее действия)!!! // todo дублирующий код!!!!
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

    // todo почему не получается Spinner с Array<Int> ?
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