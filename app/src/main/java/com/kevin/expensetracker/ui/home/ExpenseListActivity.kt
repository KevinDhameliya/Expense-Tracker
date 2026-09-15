package com.kevin.expensetracker.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.expensetracker.R
import com.kevin.expensetracker.databinding.ActivityExpenseListBinding
import com.kevin.expensetracker.ui.account.AccountFragment
import com.kevin.expensetracker.ui.addExpense.AddExpenseActivity
import com.kevin.expensetracker.ui.friends.FriendsFragment
import com.kevin.expensetracker.ui.groups.GroupsFragment
import com.kevin.expensetracker.ui.mainActivity.ActivityFragment

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

    private fun setupWindowInsets() {

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->

            val systemBars = insets.getInsets(
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

    private fun setupRecyclerView() {

        binding.rvExpenses.layoutManager =
            LinearLayoutManager(this)
    }

    private fun setupBottomNavigation() {

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_groups -> {
                    openFragment(GroupsFragment())
                    true
                }

                R.id.nav_friends -> {
                    openFragment(FriendsFragment())
                    true
                }

                R.id.nav_activity -> {
                    openFragment(ActivityFragment())
                    true
                }

                R.id.nav_account -> {
                    openFragment(AccountFragment())
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

    private fun setupAddExpenseButton() {
        binding.btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }
}