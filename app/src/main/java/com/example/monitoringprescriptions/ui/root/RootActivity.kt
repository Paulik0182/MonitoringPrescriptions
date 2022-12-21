package com.example.monitoringprescriptions.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ActivityRootBinding
import com.example.monitoringprescriptions.domain.entities.PrescriptionEntity
import com.example.monitoringprescriptions.ui.appointments.AppointmentsFragment
import com.example.monitoringprescriptions.ui.details.DetailsReceptionFragment
import com.example.monitoringprescriptions.ui.schedule.ScheduleFragment
import com.example.monitoringprescriptions.ui.settings.SettingsFragment

private const val TAG_MAIN_CONTAINER_LAYOUT_KEY = "TAG_MAIN_CONTAINER_LAYOUT_KEY"
private const val TAG_DETAILS_RECEPTION_KEY = "TAG_DETAILS_RECEPTION_KEY"

class RootActivity : AppCompatActivity(),
    SettingsFragment.Controller,
    AppointmentsFragment.Controller,
    DetailsReceptionFragment.Controller {

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

    private fun detailsReception(prescriptionEntity: PrescriptionEntity) {
        val fragment: Fragment = DetailsReceptionFragment.newInstance(prescriptionEntity)
        supportFragmentManager
            .beginTransaction()
            .replace(
                binding.fragmentContainerFrameLayout.id,
                fragment,
                TAG_DETAILS_RECEPTION_KEY
            )
            .addToBackStack(null)
            .commit()

        binding.bottomNavBar.visibility = View.GONE
    }

    override fun openDetailsReception(prescriptionEntity: PrescriptionEntity) {
        detailsReception(prescriptionEntity)
    }

    override fun onBackPressed() {
        binding.bottomNavBar.visibility = View.VISIBLE
        super.onBackPressed()
    }
}