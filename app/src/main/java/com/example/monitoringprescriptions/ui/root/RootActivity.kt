package com.example.monitoringprescriptions.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.databinding.ActivityRootBinding
import com.example.monitoringprescriptions.domain.entities.ReceptionEntity
import com.example.monitoringprescriptions.ui.reception.DayChangedFragment
import com.example.monitoringprescriptions.ui.reception.ReceptionFragment
import com.example.monitoringprescriptions.ui.schedule.ScheduleFragment
import com.example.monitoringprescriptions.ui.settings.SettingsFragment

private const val TAG_MAIN_CONTAINER_LAYOUT_KEY = "TAG_MAIN_CONTAINER_LAYOUT_KEY"
private const val TEG_DAY_CHANGED_KEY = "TEG_DAY_CHANGED_KEY"

class RootActivity : AppCompatActivity(),
    ReceptionFragment.Controller,
    SettingsFragment.Controller,
    ScheduleFragment.Controller,
    DayChangedFragment.Controller {

    private lateinit var binding: ActivityRootBinding

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
                R.id.reception_list_item -> ScheduleFragment()
                R.id.settings_item -> SettingsFragment()
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

    private fun openDayChanged(calendar: String) {
        val fragment: Fragment = DayChangedFragment.newInstance(calendar)
//        childFragmentManager
//            .beginTransaction()
//            .replace(binding.fragmentContainerFrameLayout.id, fragment, TEG_DAY_CHANGED_KEY)
//            .commit()
    }

    override fun openDetailsReception(receptionEntity: ReceptionEntity) {
        TODO("Not yet implemented")
    }

}