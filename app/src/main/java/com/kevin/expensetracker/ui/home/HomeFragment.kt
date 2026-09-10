package com.kevin.expensetracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kevin.expensetracker.data.ExpenseRepository
import com.kevin.expensetracker.databinding.FragmentHomeBinding
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ExpenseRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(
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

        repository = ExpenseRepository(requireContext())

        loadData()
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            loadData()
        }
    }

    private fun loadData() {

        val total = repository.getTotalExpense()

        binding.tvTotalExpense.text =
            String.format(
                Locale.getDefault(),
                "₹%.2f",
                total
            )

        val expenses = repository.getExpenses()

        binding.tvExpenseCount.text =
            expenses.size.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}