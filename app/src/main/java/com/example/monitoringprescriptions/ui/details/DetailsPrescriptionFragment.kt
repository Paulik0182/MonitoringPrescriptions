package com.example.monitoringprescriptions.ui.details

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentDerailsPrescriptionBinding
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.utils.bpDataFormatter
import com.example.monitoringprescriptions.utils.decimalForm
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val PRESCRIPTION_ID_ARG_KEY = "PRESCRIPTION_ID_ARG_KEY"

class DetailsPrescriptionFragment : Fragment(R.layout.fragment_derails_prescription) {

    private var _binding: FragmentDerailsPrescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var exitMenuItem: MenuItem

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

    private val dosageSpinnerLabels: Array<String> by lazy {
        resources.getStringArray(R.array.dosage)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDerailsPrescriptionBinding.bind(view)

        setHasOptionsMenu(true)

        saveDetailsReception()

        viewModel.prescriptionLiveData.observe(viewLifecycleOwner) {
            fillPrescription(it)
        }

        binding.saveButton.setOnClickListener {
            saveDetailsReception()
        }

        initSpinner(binding.unitMeasurementSpinner, unitMeasurementSpinnerLabels) {
            viewModel.onUnitMeasurementSelectSpinner(it)
        }

        initSpinner(binding.prescribedMedicineSpinner, prescribedMedicineSpinnerLabels) {
            viewModel.onMedicineSelectSpinner(it)
        }

        initSpinner(binding.dosageSpinner, dosageSpinnerLabels) {
            viewModel.onDosageSelectSpinner(it)
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

        dosageSpinnerLabels.forEachIndexed { i, dosage ->
            if (dosage == prescription.dosage.toString()) {
                binding.dosageSpinner.setSelection(i)
            }
        }
    }

    private fun saveDetailsReception() {

//        activity?.supportFragmentManager?.popBackStack() // выход

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_for_detailed_fragment, menu)
    }


    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_icon_menu_items -> {
                // Делаем копию, далее изменяем данные.
//                val changedAppointEntity = appointEntity?.copy(
//                    nameMedicine = binding.nameMedicineEditText.text.toString(),
//                    prescribedMedicine = binding.prescribedMedicineTextView.text.toString(),
//                    dosage = binding.dosageTextView.text.toString().toFloat(),
//                    unitMeasurement = binding.unitMeasurementTextView.text.toString(),
//                    comment = binding.commentEditText.text.toString(),
//                    dateStart = binding.dateStartEditText.text.toString().toLong()
//                )

//                val appointmentsRepo = appointmentsRepo
//                appointmentsRepo.updateAppointments(changedAppointEntity) // добавляем данные
//                getController().onDataChanged() // обновляем данные

                activity?.supportFragmentManager?.popBackStack() //выход (кнопка назад)
                return true
            }

            R.id.exit_icon_menu_items -> {
                activity?.supportFragmentManager?.popBackStack() //выход (кнопка назад)
            }

            R.id.delete_icon_menu_items -> {
//                val appointmentsRepo = appointmentsRepo

                // всплывающее окно (уточнее действия)!!!
                AlertDialog.Builder(requireContext())
                    .setTitle("Вы уверены что хотите удалить запись?")//сообщение на всплыв. окне
                    .setPositiveButton("ДА") { dialogInterface: DialogInterface, i: Int ->
                        viewModel.onDeleteClick() //Удаление записи
                        dialogInterface.dismiss()//закрываем окно. Обязательно!!
                    }
                    .setNegativeButton("НЕТ") { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()//закрываем окно
                    }
                    .show()
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

//                Toast.makeText(
//                    requireContext(),
//                    labels[position],
//                    Toast.LENGTH_SHORT
//                ).show()
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
}