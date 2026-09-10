package com.kevin.expensetracker.ui.expenses

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.expensetracker.data.ExpenseRepository
import com.kevin.expensetracker.databinding.FragmentExpenseListBinding
import com.kevin.expensetracker.ui.addExpense.AddExpenseActivity
import com.kevin.expensetracker.adapter.ExpenseAdapter
import com.kevin.expensetracker.ui.addExpense.EditExpenseActivity

class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ExpenseRepository
    private lateinit var adapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExpenseListBinding.inflate(
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
        setupAddButton()
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            loadExpenses()
        }
    }

    private fun setupRecyclerView() {

        adapter = ExpenseAdapter(

            onEditClick = { expense ->

                val intent = Intent(
                    requireContext(),
                    EditExpenseActivity::class.java
                )

                intent.putExtra(
                    "expense_id",
                    expense.id
                )

                startActivity(intent)
            },

            onDeleteClick = { expense ->

                repository.deleteExpense(expense.id)

                loadExpenses()
            }
        )

        binding.rvExpenses.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvExpenses.adapter = adapter
    }

    private fun loadExpenses() {

        val expenses = repository.getExpenses()

        adapter.submitList(expenses)

        binding.tvEmptyState.visibility =
            if (expenses.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.tvTotalExpense.text =
            "₹%.2f".format(
                repository.getTotalExpense()
            )
    }

    private fun setupAddButton() {

        binding.fabAddExpense.setOnClickListener {

            startActivity(
                Intent(
                    requireContext(),
                    AddExpenseActivity::class.java
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}