package com.kevin.expensetracker.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.kevin.expensetracker.R
import com.kevin.expensetracker.databinding.ActivityMainBinding
import com.kevin.expensetracker.ui.account.AccountFragment
import com.kevin.expensetracker.ui.friends.FriendsFragment
import com.kevin.expensetracker.ui.groups.GroupsFragment
import com.kevin.expensetracker.ui.mainActivity.ActivityFragment

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
            binding.bottomNavigation.selectedItemId =
                R.id.nav_groups
        }
    }

    private fun setupWindowInsets() {

        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { _, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            binding.main.setPadding(
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

                R.id.nav_groups -> {
                    openFragment(
                        GroupsFragment(),
                        "Groups"
                    )
                    true
                }

                R.id.nav_friends -> {
                    openFragment(
                        FriendsFragment(),
                        "Friends"
                    )
                    true
                }

                R.id.nav_activity -> {
                    openFragment(
                        ActivityFragment(),
                        "Activity"
                    )
                    true
                }

                R.id.nav_account -> {
                    openFragment(
                        AccountFragment(),
                        "Account"
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun openFragment(
        fragment: Fragment,
        tag: String
    ) {

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment,
                tag
            )
            .commit()
    }

    // ----------------------------------------
    // Bottom Navigation Visibility
    // ----------------------------------------

    fun hideBottomNavigation() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }
}