package com.kevin.expensetracker.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kevin.expensetracker.R
import com.kevin.expensetracker.databinding.ActivityExpenseListBinding

class ExpenseListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupRecyclerView()
        setupBottomNavigation()
        setupAddExpenseButton()
    }

    /**
     * Handles status bar and navigation bar insets.
     */
    private fun setupWindowInsets() {

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            // Apply only top/left/right padding to the root.
            // Bottom inset is handled by BottomNavigationView.
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

    /**
     * Setup expense RecyclerView.
     */
    private fun setupRecyclerView() {

        binding.rvExpenses.layoutManager =
            LinearLayoutManager(this)

        // We will connect the SQLite data and adapter here.
        //
        // Example:
        //
        // val adapter = ExpenseAdapter(expenses)
        // binding.rvExpenses.adapter = adapter
    }

    /**
     * Bottom navigation setup.
     */
    private fun setupBottomNavigation() {

        binding.bottomNavigation.selectedItemId =
            R.id.nav_expenses

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_home -> {
                    // TODO: Open Home screen
                    true
                }

                R.id.nav_expenses -> {
                    // Already on Expense screen
                    true
                }

                R.id.nav_reports -> {
                    // TODO: Open Reports screen
                    true
                }

                R.id.nav_settings -> {
                    // TODO: Open Settings screen
                    true
                }

                else -> false
            }
        }
    }

    /**
     * Add Expense button.
     */
    private fun setupAddExpenseButton() {

        binding.fabAddExpense.setOnClickListener {

            // TODO:
            // Open AddExpenseActivity
            //
            // startActivity(
            //     Intent(this, AddExpenseActivity::class.java)
            // )
        }
    }
}