package com.kevin.expensetracker.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kevin.expensetracker.R
import com.kevin.expensetracker.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccountBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupProfile()
        setupClickListeners()
    }

    private fun setupProfile() {

        // Replace these later with actual user data
        binding.tvProfileName.text = "Kevin"
        binding.tvProfileEmail.text = "kevin@email.com"

        binding.tvProfileAvatar.text =
            binding.tvProfileName.text
                .toString()
                .trim()
                .firstOrNull()
                ?.uppercase()
                ?: "?"
    }

    private fun setupClickListeners() {

        binding.rowPersonalInfo.setOnClickListener {

            showMessage("Personal information")
        }

        binding.rowNotifications.setOnClickListener {

            showMessage("Notifications")
        }

        binding.rowCurrency.setOnClickListener {

            showMessage("Currency")
        }

        binding.rowReports.setOnClickListener {

            showMessage("Reports")
        }

        binding.rowSettings.setOnClickListener {

            showMessage("Settings")
        }

        binding.rowHelp.setOnClickListener {

            showMessage("Help & Support")
        }

        binding.rowAbout.setOnClickListener {

            showMessage("About")
        }

        binding.btnLogout.setOnClickListener {

            showLogoutMessage()
        }
    }

    private fun showMessage(message: String) {

        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLogoutMessage() {

        Toast.makeText(
            requireContext(),
            "Logout functionality will be added later",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}