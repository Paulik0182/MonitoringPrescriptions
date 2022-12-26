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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.FragmentDerailsPrescriptionBinding
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import com.example.monitoringprescriptions.utils.bpDataFormatter

private const val PRESCRIPTION_ID_ARG_KEY = "PRESCRIPTION_ID_ARG_KEY"

class DetailsPrescriptionFragment : Fragment(R.layout.fragment_derails_prescription) {

    private var _binding: FragmentDerailsPrescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var exitMenuItem: MenuItem

    private var appointEntity: AppointmentFullEntity? = null

    private lateinit var unitMeasurement: Array<String>
    private lateinit var prescribedMedicine: Array<String>
    private lateinit var dosageSpinner: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDerailsPrescriptionBinding.bind(view)

        unitMeasurement = resources.getStringArray(R.array.unit_measurement)
        prescribedMedicine = resources.getStringArray(R.array.prescribed_medicine)
        dosageSpinner = resources.getStringArray(R.array.dosage)

        setHasOptionsMenu(true)

        appointEntity =
            requireArguments().getParcelable(PRESCRIPTION_ID_ARG_KEY)// todo взяли передоваемое значение

        if (appointEntity != null) {
            setAppointmentFullEntity(appointEntity) // Положили переданное значение
        } else {
            setAppointmentFullEntity(null)
        }
        saveDetailsReception()

        getUnitMeasurementSpinner(appointEntity)
        getPrescribedMedicineSpinner(appointEntity)
        getDosageSpinner(appointEntity)
    }

    private fun setAppointmentFullEntity(appointEntity: AppointmentFullEntity?) {
        if (appointEntity != null) {
            binding.dateStartEditText.setText(bpDataFormatter.format(appointEntity.dateStart))
            binding.nameMedicineEditText.setText(appointEntity.nameMedicine)
            binding.prescribedMedicineTextView.text = appointEntity.prescribedMedicine
            binding.dosageTextView.text = appointEntity.dosage.toString()
            binding.unitMeasurementTextView.text = appointEntity.unitMeasurement
            binding.commentEditText.setText(appointEntity.comment)
        } else {
            // todo
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_for_detailed_fragment, menu)
        exitMenuItem = menu.findItem(R.id.exit_icon_menu_items)
    }

    private fun saveDetailsReception() {
        binding.saveButton.setOnClickListener {


            activity?.supportFragmentManager?.popBackStack() // выход
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_icon_menu_items -> {
                // Делаем копию, далее изменяем данные.
                val changedAppointEntity = appointEntity?.copy(
                    nameMedicine = binding.nameMedicineEditText.text.toString(),
                    prescribedMedicine = binding.prescribedMedicineTextView.text.toString(),
                    dosage = binding.dosageTextView.text.toString().toFloat(),
                    unitMeasurement = binding.unitMeasurementTextView.text.toString(),
                    comment = binding.commentEditText.text.toString(),
                    dateStart = binding.dateStartEditText.text.toString().toLong()
                )

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
//                        appointmentsRepo //Удаление записи
                        activity?.onBackPressed()//выход (кнопка назад)
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


    private fun getUnitMeasurementSpinner(appointEntity: AppointmentFullEntity?) {
        val units = binding.unitMeasurementSpinner
        if (units != null) {
            val unitsAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                unitMeasurement
            )
            units.adapter = unitsAdapter

            units.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    var value = units.selectedItemPosition
                    when (value) {
                        1 -> appointEntity?.unitMeasurement
                        2 -> appointEntity?.unitMeasurement
                        3 -> appointEntity?.unitMeasurement
//                        else -> throw IllegalStateException("Такого значения нет")
                    }

                    Toast.makeText(
                        requireContext(),
                        unitMeasurement[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // todo
                }
            }
        }
    }

    private fun getPrescribedMedicineSpinner(appointEntity: AppointmentFullEntity?) {
        val units = binding.prescribedMedicineSpinner
        if (units != null) {
            val unitsAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                prescribedMedicine
            )
            units.adapter = unitsAdapter

            units.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    var value = units.selectedItemPosition
                    when (value) {
                        1 -> appointEntity?.prescribedMedicine
                        2 -> appointEntity?.prescribedMedicine
                        3 -> appointEntity?.prescribedMedicine
                        4 -> appointEntity?.prescribedMedicine
                        5 -> appointEntity?.prescribedMedicine
                        6 -> appointEntity?.prescribedMedicine
//                        else -> throw IllegalStateException("Такого значения нет")
                    }

                    Toast.makeText(
                        requireContext(),
                        prescribedMedicine[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // todo
                }
            }
        }
    }

    private fun getDosageSpinner(appointEntity: AppointmentFullEntity?) {
        val units = binding.dosageSpinner
        if (units != null) {
            val unitsAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                dosageSpinner
            )
            units.adapter = unitsAdapter

            units.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    var value = units.selectedItemPosition
                    when (value) {
                        1 -> appointEntity?.dosage
                        2 -> appointEntity?.dosage
                        3 -> appointEntity?.dosage
                        4 -> appointEntity?.dosage
                        5 -> appointEntity?.dosage
                        6 -> appointEntity?.dosage
                        7 -> appointEntity?.dosage
                        8 -> appointEntity?.dosage
//                        else -> throw IllegalStateException("Такого значения нет")
                    }

                    Toast.makeText(
                        requireContext(),
                        dosageSpinner[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // todo
                }
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
        fun newInstance(appointmentId: String) =
            DetailsPrescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(PRESCRIPTION_ID_ARG_KEY, appointmentId)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}