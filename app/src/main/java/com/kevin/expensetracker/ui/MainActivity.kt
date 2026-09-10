package com.kevin.expensetracker.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.kevin.expensetracker.R
import com.kevin.expensetracker.databinding.ActivityMainBinding
import com.kevin.expensetracker.ui.expenses.ExpenseListFragment
import com.kevin.expensetracker.ui.home.HomeFragment
import com.kevin.expensetracker.ui.reports.ReportsFragment
import com.kevin.expensetracker.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupWindowInsets()
        setupBottomNavigation()

        if (savedInstanceState == null) {
            openFragment(HomeFragment())
        }
    }

    private fun setupWindowInsets() {

        ViewCompat.setOnApplyWindowInsetsListener(
            binding.main
        ) { view, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                0
            )

            binding.bottomNavigation.setPadding(
                0,
                0,
                0,
                systemBars.bottom
            )

            insets
        }
    }

    private fun setupBottomNavigation() {

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_home -> {
                    openFragment(HomeFragment())
                    true
                }

                R.id.nav_expenses -> {
                    openFragment(ExpenseListFragment())
                    true
                }

                R.id.nav_reports -> {
                    openFragment(ReportsFragment())
                    true
                }

                R.id.nav_settings -> {
                    openFragment(SettingsFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment
            )
            .commit()
    }
}