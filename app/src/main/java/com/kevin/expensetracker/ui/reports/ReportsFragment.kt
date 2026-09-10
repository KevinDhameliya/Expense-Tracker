package com.kevin.expensetracker.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kevin.expensetracker.data.ExpenseRepository
import com.kevin.expensetracker.databinding.FragmentReportsBinding

class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ExpenseRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentReportsBinding.inflate(
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

        loadReport()
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            loadReport()
        }
    }

    private fun loadReport() {

        val expenses =
            repository.getExpenses()

        val total =
            repository.getTotalExpense()

        binding.tvTotal.text =
            "₹%.2f".format(total)

        if (expenses.isNotEmpty()) {

            val highest =
                expenses.maxByOrNull {
                    it.amount
                }

            binding.tvHighestExpense.text =
                "Highest: ${highest?.title} - ₹%.2f"
                    .format(highest?.amount ?: 0.0)

        } else {

            binding.tvHighestExpense.text =
                "No expenses available"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}