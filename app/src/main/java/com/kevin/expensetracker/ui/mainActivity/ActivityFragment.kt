package com.kevin.expensetracker.ui.mainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.expensetracker.adapter.ActivityAdapter
import com.kevin.expensetracker.data.ExpenseRepository
import com.kevin.expensetracker.databinding.FragmentActivityBinding
import com.kevin.expensetracker.model.Expense

class ActivityFragment : Fragment() {

    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ExpenseRepository
    private lateinit var activityAdapter: ActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentActivityBinding.inflate(
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

        repository =
            ExpenseRepository(requireContext())

        setupRecyclerView()
        setupButtons()
        loadActivity()
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            loadActivity()
        }
    }

    private fun setupRecyclerView() {

        activityAdapter = ActivityAdapter { expense ->

            openExpense(expense)
        }

        binding.rvActivity.apply {

            layoutManager =
                LinearLayoutManager(requireContext())

            adapter = activityAdapter

            setHasFixedSize(true)
        }
    }

    private fun loadActivity() {

        val expenses =
            repository.getExpenses()

        // Show count
        binding.tvActivityCount.text =
            when (expenses.size) {

                0 -> "No expenses"

                1 -> "1 expense"

                else ->
                    "${expenses.size} expenses"
            }

        if (expenses.isEmpty()) {

            binding.rvActivity.visibility =
                View.GONE

            binding.emptyState.visibility =
                View.VISIBLE

        } else {

            binding.rvActivity.visibility =
                View.VISIBLE

            binding.emptyState.visibility =
                View.GONE

            activityAdapter.submitList(
                expenses.reversed()
            )
        }
    }

    private fun setupButtons() {

        binding.btnActivityFilter.setOnClickListener {

            showFilterOptions()
        }
    }

    private fun openExpense(expense: Expense) {

        // Later you can open ExpenseDetailsFragment here.
    }

    private fun showFilterOptions() {

        // Filter functionality can be added here.
    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}