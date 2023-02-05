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
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.ui.details.CreationPrescriptionScreenErrors
import com.example.monitoringprescriptions.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class NewPrescriptionFragment :
    Fragment(R.layout.fragment_new_prescription),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentNewPrescriptionBinding? = null
    private val binding get() = _binding!!

    private val calendarFromView = Calendar.getInstance()
    private val calendarTimeTwo = Calendar.getInstance()
    private val calendarTimeThree = Calendar.getInstance()
    private val calendarTimeFour = Calendar.getInstance()
    private val calendarTimeFive = Calendar.getInstance()
    private lateinit var timeTwoSetListener: TimePickerDialog.OnTimeSetListener
    private lateinit var timeThreeSetListener: TimePickerDialog.OnTimeSetListener
    private lateinit var timeFourSetListener: TimePickerDialog.OnTimeSetListener
    private lateinit var timeFiveSetListener: TimePickerDialog.OnTimeSetListener

    private val unitMeasurementSpinnerLabels: Array<String> by lazy {
        UnitsMeasurement.values().map { it.toString(requireContext()) }
            .toTypedArray() // мапим значения из спинера чтобы получить норм. список
    }

    private val prescribedMedicineSpinnerLabels: Array<String> by lazy {
        TypeMedicine.values().map { it.toString(requireContext()) }
            .toTypedArray() // мапим значения из спинера чтобы получить норм. список
    }

    private val numberOfReceptionsPerDaySpinnerLabels: Array<Int> by lazy {
        resources.getIntArray(R.array.number_of_receptions_per_day).toTypedArray()
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
            numberOfReceptionsPerDaySpinnerLabels.map { it.toString() }
                .toTypedArray() // особенность с Int спинером
            // toTypedArray заганяет Array обратно в массив
        ) {
            viewModel.onNumberAdmissionsPerDaySelectSpinner(it)
        }

        viewModel.errorsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                // todo проблема с показом ошибки
                is CreationPrescriptionScreenErrors.DateStartError -> {
                    (binding.dateStartTextView.error as TextView).error = it.errorsMessage
                }
                is CreationPrescriptionScreenErrors.NumberDaysTakingMedicineError -> {
                    binding.numberDaysTakingMedicineEditText.error = it.errorsMessage
                }
                is CreationPrescriptionScreenErrors.NameMedicineError -> {
                    binding.nameMedicineEditText.error = it.errorsMessage
                }
                is CreationPrescriptionScreenErrors.PrescribedMedicineError -> {
                    (binding.prescribedMedicineSpinner.selectedView as TextView).error =
                        it.errorsMessage
                }
                is CreationPrescriptionScreenErrors.DosageError -> {
                    binding.dosageEditText.error = it.errorsMessage
                }
                // todo проблема с показом текста ошибки
                is CreationPrescriptionScreenErrors.UnitMeasurementError -> {
                    (binding.unitMeasurementSpinner.selectedView as TextView).error =
                        it.errorsMessage
                }
                // todo Дополнительная проверка на соответствие значений проблема с показом текста ошибки
                is CreationPrescriptionScreenErrors.UnitMeasurementMatchingValuesError -> {
                    (binding.unitMeasurementSpinner.selectedView as TextView).error =
                        it.errorsMessage
                }

                // todo проблема с показом текста ошибки
                is CreationPrescriptionScreenErrors.NumberAdmissionsPerDayError -> {
                    (binding.numberAdmissionsPerDaySpinner.selectedView as TextView).error =
                        it.errorsMessage
                }
                else -> {

                }
            }
        }

        viewModel.dialogLiveData.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle(it.massage)
                .setPositiveButton(
                    requireContext()
                        .getString(R.string.yes)
                ) { dialogInterface: DialogInterface, _ ->
                    dialogInterface.dismiss()
                }.show()
        }
        showTimeTakingMedications()

        getMedicationIntakeTime()
    }

    private fun changeTimeInDayVisibility(count: Int) {
        binding.textTwoTextView.visibility = View.GONE
        binding.timeReceptionTwoTextView.visibility = View.GONE
        binding.textThreeTextView.visibility = View.GONE
        binding.timeReceptionThreeTextView.visibility = View.GONE
        binding.textFourTextView.visibility = View.GONE
        binding.timeReceptionFourTextView.visibility = View.GONE
        binding.textFiveTextView.visibility = View.GONE
        binding.timeReceptionFiveTextView.visibility = View.GONE

        if (count >= 1) {
            binding.textTwoTextView.visibility = View.VISIBLE
            binding.timeReceptionTwoTextView.visibility = View.VISIBLE
        } else {
            binding.timeReceptionTwoTextView.text = null
        }
        if (count >= 2) {
            binding.textThreeTextView.visibility = View.VISIBLE
            binding.timeReceptionThreeTextView.visibility = View.VISIBLE
        } else {
            binding.timeReceptionThreeTextView.text = null
        }
        if (count >= 3) {
            binding.textFourTextView.visibility = View.VISIBLE
            binding.timeReceptionFourTextView.visibility = View.VISIBLE
        } else {
            binding.timeReceptionFourTextView.text = null
        }
        if (count >= 4) {
            binding.textFiveTextView.visibility = View.VISIBLE
            binding.timeReceptionFiveTextView.visibility = View.VISIBLE
        } else {
            binding.timeReceptionFiveTextView.text = null
        }
    }

    private fun showTimeTakingMedications() {
        // видимость поля приема лекарств в зависимости от выбранного значения приема в день.
        binding.numberAdmissionsPerDaySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    changeTimeInDayVisibility(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // todo
                }
            }
        changeTimeInDayVisibility(binding.numberAdmissionsPerDaySpinner.selectedItemPosition)
    }

    private fun saveNewReception() {
        // пример получения строки из Spinner (получаем позицию). эту строку и передаем
        val unitMeasurement =
            unitMeasurementSpinnerLabels[binding.unitMeasurementSpinner.selectedItemPosition]
        val typeMedicine =
            prescribedMedicineSpinnerLabels[binding.prescribedMedicineSpinner.selectedItemPosition]
        val numberAdmissionsPerDay =
            numberOfReceptionsPerDaySpinnerLabels[binding.numberAdmissionsPerDaySpinner.selectedItemPosition]

        viewModel.onSaveNewPrescription(
            // собираем все данные которые имеются
            nameMedicine = binding.nameMedicineEditText.text.toString(),
            dosage = binding.dosageEditText.text.toString().toFloatOrNull(),
            unitMeasurement = unitMeasurement.toUnitMeasurement(requireContext()),
            typeMedicine = typeMedicine.toTypeMedicine(requireContext()),
            comment = binding.commentEditText.text.toString(),
            dateStart = calendarFromView.timeInMillis,
            numberDaysTakingMedicine = binding.numberDaysTakingMedicineEditText.text.toString()
                .toIntOrNull(),
            numberAdmissionsPerDay = numberAdmissionsPerDay,
            medicationsCourse = binding.medicationsCourseEditText.text.toString().toFloatOrNull()
                ?: 0F,

            // Время приема
            timeReceptionTwo = calendarTimeTwo.timeInMillis,
            timeReceptionThree = calendarTimeThree.timeInMillis,
            timeReceptionFour = calendarTimeFour.timeInMillis,
            timeReceptionFive = calendarTimeFive.timeInMillis
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
                onSelect(labels[position])// здесь позиция котораю передали
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
            bpDataFormatter.format(
                calendarFromView.time
            )

        binding.timeReceptionOneTextView.text =
            bpTimeFormatter.format(
                calendarFromView.time
            )
    }

    private fun getMedicationIntakeTime() {

        timeTwoSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendarTimeTwo.set(Calendar.HOUR_OF_DAY, hour)
            calendarTimeTwo.set(Calendar.MINUTE, minute)
            binding.timeReceptionTwoTextView.text =
                bpTimeFormatter.format(
                    calendarTimeTwo.time
                )
        }

        timeThreeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendarTimeThree.set(Calendar.HOUR_OF_DAY, hour)
            calendarTimeThree.set(Calendar.MINUTE, minute)
            binding.timeReceptionThreeTextView.text =
                bpTimeFormatter.format(
                    calendarTimeThree.time
                )
        }

        timeFourSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendarTimeFour.set(Calendar.HOUR_OF_DAY, hour)
            calendarTimeFour.set(Calendar.MINUTE, minute)
            binding.timeReceptionFourTextView.text =
                bpTimeFormatter.format(
                    calendarTimeFour.time
                )
        }

        timeFiveSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendarTimeFive.set(Calendar.HOUR_OF_DAY, hour)
            calendarTimeFive.set(Calendar.MINUTE, minute)
            binding.timeReceptionFiveTextView.text =
                bpTimeFormatter.format(
                    calendarTimeFive.time
                )
        }
    }

    private fun initDateView() {
        val calendar = Calendar.getInstance()

        binding.dateStartTextView.setOnClickListener {
            val currentDay = calendar.get(Calendar.DAY_OF_YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)

            DatePickerDialog(
                requireContext(),
                this,
                currentYear,
                currentMonth,
                currentDay
            ).show()
        }

        binding.timeReceptionOneTextView.setOnClickListener {
            onTimePickerDialog(calendar, this)
        }
        binding.timeReceptionTwoTextView.setOnClickListener {
            onTimePickerDialog(calendar, timeTwoSetListener)
        }
        binding.timeReceptionThreeTextView.setOnClickListener {
            onTimePickerDialog(calendar, timeThreeSetListener)
        }
        binding.timeReceptionFourTextView.setOnClickListener {
            onTimePickerDialog(calendar, timeFourSetListener)
        }
        binding.timeReceptionFiveTextView.setOnClickListener {
            onTimePickerDialog(calendar, timeFiveSetListener)
        }
    }

    private fun onTimePickerDialog(
        calendar: Calendar,
        listener: TimePickerDialog.OnTimeSetListener
    ) {
        val currencyHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currencyMinute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            listener,
            currencyHour,
            currencyMinute,
            true
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}