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
import com.example.monitoringprescriptions.databinding.FragmentDerailsPrescriptionBinding
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.utils.bpDataFormatter
import com.example.monitoringprescriptions.utils.decimalForm
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

private const val PRESCRIPTION_ID_ARG_KEY = "PRESCRIPTION_ID_ARG_KEY"

class DetailsPrescriptionFragment :
    Fragment(R.layout.fragment_derails_prescription),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentDerailsPrescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var prescriptionAdapter: PrescriptionAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDerailsPrescriptionBinding.bind(view)

        setHasOptionsMenu(true)

        saveDetailsReception()

        initPrescription()

        initDateView()

        viewModel.prescriptionListLiveDate.observe(viewLifecycleOwner) {
            prescriptionAdapter.setData(it)
        }

        viewModel.prescriptionLiveData.observe(viewLifecycleOwner) {
            fillPrescription(it)
        }

        binding.saveButton.setOnClickListener {
            saveDetailsReception()
            showCloseDialog("Выйти из рецепта?")
        }

        initSpinner(binding.unitMeasurementSpinner, unitMeasurementSpinnerLabels) {
            viewModel.onUnitMeasurementSelectSpinner(it)
        }

        initSpinner(binding.prescribedMedicineSpinner, prescribedMedicineSpinnerLabels) {
            viewModel.onMedicineSelectSpinner(it)
        }
    }

    private fun fillPrescription(prescription: PrescriptionEntity) {
        binding.dateStartEditText.setText(bpDataFormatter.format(prescription.dateStart))
        binding.nameMedicineEditText.setText(prescription.nameMedicine)
        binding.dosageEditText.setText(decimalForm.format(prescription.dosage))
        binding.commentEditText.setText(prescription.comment)

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
    }

    private fun saveDetailsReception() {

        // пример получения строки из Spinner (получаем позицию). эту строку и передаем
        val unitMeasurement =
            unitMeasurementSpinnerLabels[binding.unitMeasurementSpinner.selectedItemPosition]
        val prescribedMedicine =
            prescribedMedicineSpinnerLabels[binding.prescribedMedicineSpinner.selectedItemPosition]

        val dateStartMs = Calendar.getInstance().apply {
            set(Calendar.YEAR, saveYear)
            set(Calendar.MONTH, saveMonth)
            set(Calendar.DAY_OF_MONTH, saveDay)
            set(Calendar.HOUR_OF_DAY, saveHour)
            set(Calendar.MINUTE, saveMinute)
        }.timeInMillis

        viewModel.onSaveDetails(
            // собираем все данные которые имеются
            dateStartMs,
            binding.nameMedicineEditText.text.toString(),
            binding.dosageEditText.text.toString(),
            binding.commentEditText.text.toString(),
            prescribedMedicine,
            unitMeasurement
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


    private fun initSpinner(spinner: Spinner, labels: Array<String>, onSelect: (String) -> Unit) {
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
        prescriptionAdapter = PrescriptionAdapter(
            data = emptyList(),
            context = requireContext()
        )
        binding.recordsRecyclerView.adapter = prescriptionAdapter
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

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var saveDay = 0
    private var saveMonth = 0
    private var saveYear = 0
    private var saveHour = 0
    private var saveMinute = 0

    private fun fillTimeFields() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        saveDay = dayOfMonth
        saveMonth = month
        saveYear = year

        fillTimeFields()

        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        saveHour = hourOfDay
        saveMinute = minute

        binding.dateStartEditText.text =
            "$saveDay.$saveMonth.$saveYear\n Время: $saveHour:$saveMinute"
    }

    private fun initDateView() {
        binding.dateStartEditText.setOnClickListener {
            fillTimeFields()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

}