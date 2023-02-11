package com.example.monitoringprescriptions.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ActivityRootBinding
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.AppointmentFullEntity
import com.example.monitoringprescriptions.ui.appointments.AppointmentsFragment
import com.example.monitoringprescriptions.ui.details.DetailsPrescriptionFragment
import com.example.monitoringprescriptions.ui.details.create.NewPrescriptionFragment
import com.example.monitoringprescriptions.ui.schedule.ScheduleFragment
import com.example.monitoringprescriptions.ui.settings.SettingsFragment

private const val TAG_MAIN_CONTAINER_LAYOUT_KEY = "TAG_MAIN_CONTAINER_LAYOUT_KEY"
private const val TAG_DETAILS_PRESCRIPTION_KEY = "TAG_DETAILS_RECEPTION_KEY"
private const val TAG_NEW_PRESCRIPTION_KEY = "TAG_NEW_PRESCRIPTION_KEY"

@Suppress("DEPRECATION")
class RootActivity : AppCompatActivity(),
    SettingsFragment.Controller,
    AppointmentsFragment.Controller,
    DetailsPrescriptionFragment.Controller,
    NewPrescriptionFragment.Controller {

    private lateinit var binding: ActivityRootBinding

    // попытка сохранить состаяния фрагмента
    private val scheduleFragment: ScheduleFragment by lazy { ScheduleFragment() }
    private val settingsFragment: SettingsFragment by lazy { SettingsFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavBar()

        if (savedInstanceState == null) {
            binding.bottomNavBar.selectedItemId = R.id.reception_list_item
        } else {
            //todo иначе достать из --
        }
    }

    private fun setupBottomNavBar() {
        binding.bottomNavBar.setOnItemSelectedListener {
            title = it.title
            val fragment = when (it.itemId) {
                R.id.reception_list_item -> scheduleFragment
                R.id.settings_item -> settingsFragment
                else -> throw IllegalStateException("Такого фрагмента нет")
            }
            swapFragment(fragment)
            return@setOnItemSelectedListener true
        }
    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                binding.fragmentContainerFrameLayout.id,
                fragment,
                TAG_MAIN_CONTAINER_LAYOUT_KEY
            ).commit()
    }

    private fun detailsPrescription(appointmentFullEntity: AppointmentFullEntity) {
        val fragment: Fragment =
            DetailsPrescriptionFragment.newInstance(appointmentFullEntity.prescriptionId)
        supportFragmentManager
            .beginTransaction()
            .replace(
                binding.fragmentContainerFrameLayout.id,
                fragment,
                TAG_DETAILS_PRESCRIPTION_KEY
            )
            .addToBackStack(null)
            .commit()

        binding.bottomNavBar.visibility = View.GONE
    }

    private fun showPrescriptionFragment(
        typeMedicine: TypeMedicine,
        unitMeasurement: UnitsMeasurement
    ) {
        val fragment: Fragment = NewPrescriptionFragment.newInstance(typeMedicine, unitMeasurement)
        supportFragmentManager
            .beginTransaction()
            .replace(
                binding.fragmentContainerFrameLayout.id,
                fragment,
                TAG_NEW_PRESCRIPTION_KEY
            )
            .addToBackStack(null)
            .commit()

        binding.bottomNavBar.visibility = View.GONE
    }

    override fun openDetailsPrescription(appointmentFullEntity: AppointmentFullEntity) {
        detailsPrescription(appointmentFullEntity)
    }

    override fun openNewPrescription(
        typeMedicine: TypeMedicine,
        unitMeasurement: UnitsMeasurement
    ) {
        showPrescriptionFragment(typeMedicine, unitMeasurement)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        binding.bottomNavBar.visibility = View.VISIBLE
        super.onBackPressed()
    }

    override fun onDataChanged() {
        finish()
    }
}