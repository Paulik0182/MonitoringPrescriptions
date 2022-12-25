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
import com.example.monitoringprescriptions.databinding.FragmentDerailsReceptionBinding
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import com.example.monitoringprescriptions.domain.interactors.AppointmentsInteractor
import com.example.monitoringprescriptions.domain.repo.AppointmentsRepo
import com.example.monitoringprescriptions.domain.repo.PrescriptionRepo
import org.koin.android.ext.android.inject

private const val DETAILS_RECEPTION_KEY = "DETAILS_RECEPTION_KEY"

class DetailsReceptionFragment : Fragment(R.layout.fragment_derails_reception) {

    private var _binding: FragmentDerailsReceptionBinding? = null
    private val binding get() = _binding!!

    private val appointmentsRepo: AppointmentsRepo by inject()
    private val prescriptionRepo: PrescriptionRepo by inject()
    private val appointmentsInteractor: AppointmentsInteractor by inject()

    private lateinit var saveMenuItem: MenuItem
    private lateinit var delMenuItem: MenuItem
    private lateinit var exitMenuItem: MenuItem

    private lateinit var appointEntity: AppointmentFullEntity

    private lateinit var unitMeasurement: Array<String>
    private lateinit var prescribedMedicine: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDerailsReceptionBinding.bind(view)

        unitMeasurement = resources.getStringArray(R.array.unit_measurement)
        prescribedMedicine = resources.getStringArray(R.array.prescribed_medicine)

        setHasOptionsMenu(true)

        appointEntity =
            requireArguments().getParcelable(DETAILS_RECEPTION_KEY)
                ?: appointEntity // todo взяли передоваемое значение

        if (appointEntity != null) {
            setAppointmentFullEntity(appointEntity) // Положили переданное значение
        } else {
            setAppointmentFullEntity(null)
        }


        getValuesViaSpinner()
    }

    private fun setAppointmentFullEntity(appointEntity: AppointmentFullEntity?) {
        if (appointEntity != null) {
            binding.nameMedicineEditText.setText(appointEntity.nameMedicine)
            binding.prescribedMedicineTextView.text = appointEntity.prescribedMedicine
            binding.dosageEditText.setText(appointEntity.dosage.toString())
            binding.unitMeasurementTextView.text = appointEntity.unitMeasurement
            binding.commentEditText.setText(appointEntity.comment)
        } else {
            // todo
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_for_detailed_fragment, menu)
        saveMenuItem = menu.findItem(R.id.save_icon_menu_items)
        delMenuItem = menu.findItem(R.id.delete_icon_menu_items)
        exitMenuItem = menu.findItem(R.id.exit_icon_menu_items)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_icon_menu_items -> {
                // Делаем копию, далее изменяем данные.
                val changedAppointEntity = appointEntity.copy(
                    nameMedicine = binding.nameMedicineEditText.text.toString(),
                    prescribedMedicine = binding.prescribedMedicineTextView.text.toString(),
                    dosage = binding.dosageEditText.text.toString().toFloat(),
                    unitMeasurement = binding.unitMeasurementTextView.text.toString(),
                    comment = binding.commentEditText.text.toString()
                )

                val appointmentsRepo = appointmentsRepo
//                appointmentsRepo.updateAppointments(changedAppointEntity) // добавляем данные
//                getController().onDataChanged() // обновляем данные
                activity?.supportFragmentManager?.popBackStack() // выход

                return true
            }

            R.id.exit_icon_menu_items -> {
                activity?.supportFragmentManager?.popBackStack() //выход (кнопка назад)
            }

            R.id.delete_icon_menu_items -> {
                val appointmentsRepo = appointmentsRepo

                // всплывающее окно (уточнее действия)!!!
                AlertDialog.Builder(requireContext())
                    .setTitle("Вы уверены что хотите удалить запись?")//сообщение на всплыв. окне
                    .setPositiveButton("ДА") { dialogInterface: DialogInterface, i: Int ->
                        appointmentsRepo //Удаление записи
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


    private fun getValuesViaSpinner() {
        val units = binding.unitMeasurementSpinner
        if (units != null) {
            val unitsAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                unitMeasurement
            )
            units.adapter = unitsAdapter

            units.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.selected_item) + unitMeasurement,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
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
        fun newInstance(appointmentFullEntity: AppointmentFullEntity?) =
            DetailsReceptionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DETAILS_RECEPTION_KEY, appointmentFullEntity)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}