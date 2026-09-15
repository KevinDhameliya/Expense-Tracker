package com.kevin.expensetracker.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.expensetracker.R
import com.kevin.expensetracker.adapter.ExpenseAdapter
import com.kevin.expensetracker.data.ExpenseRepository
import com.kevin.expensetracker.databinding.FragmentGroupsBinding
import com.kevin.expensetracker.model.Expense
import com.kevin.expensetracker.ui.addExpense.AddExpenseFragment
import java.util.Locale

class GroupsFragment : Fragment() {

    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ExpenseRepository
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGroupsBinding.inflate(
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

        setupRecyclerView()
        setupButtons()

        loadExpenses()
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            loadExpenses()
        }
    }

    private fun setupRecyclerView() {

        expenseAdapter = ExpenseAdapter(
            onEditClick = { expense ->
                // TODO: Open edit expense screen
            },

            onDeleteClick = { expense ->

                repository.deleteExpense(expense.id)

                loadExpenses()
            }
        )

        binding.rvExpenses.apply {

            layoutManager = LinearLayoutManager(
                requireContext()
            )

            adapter = expenseAdapter
        }
    }

    private fun setupButtons() {

        binding.btnAddExpense.setOnClickListener {

            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    AddExpenseFragment()
                )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun loadExpenses() {

        val expenses = repository.getExpenses()

        // Update total amount
        val total = repository.getTotalExpense()

        binding.tvOverallAmount.text =
            String.format(
                Locale.getDefault(),
                "₹%.2f",
                total
            )

        // Update RecyclerView
        expenseAdapter.submitList(expenses)

        // Show / hide empty state
        if (expenses.isEmpty()) {

            binding.tvNoExpenses.visibility =
                View.VISIBLE

            binding.rvExpenses.visibility =
                View.GONE

        } else {

            binding.tvNoExpenses.visibility =
                View.GONE

            binding.rvExpenses.visibility =
                View.VISIBLE
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}