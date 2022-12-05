package com.example.monitoringprescriptions.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ActivityRootBinding
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.ui.reception.ReceptionFragment
import com.example.monitoringprescriptions.ui.records.OneDeyRecordsFragment
import com.example.monitoringprescriptions.ui.schedule.ScheduleFragment
import com.example.monitoringprescriptions.ui.settings.SettingsFragment

private const val TAG_MAIN_CONTAINER_LAYOUT_KEY = "TAG_MAIN_CONTAINER_LAYOUT_KEY"

class RootActivity : AppCompatActivity(),
    ReceptionFragment.Controller,
    SettingsFragment.Controller,
    OneDeyRecordsFragment.Controller {

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

    override fun openDetailsReception(receptionEntity: ReceptionEntity) {
        TODO("Not yet implemented")
    }

}