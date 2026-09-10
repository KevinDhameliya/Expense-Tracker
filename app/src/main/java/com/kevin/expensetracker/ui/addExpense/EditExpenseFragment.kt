package com.kevin.expensetracker.ui.addExpense

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kevin.expensetracker.data.ExpenseRepository
import com.kevin.expensetracker.databinding.FragmentEditExpenseBinding
import com.kevin.expensetracker.model.Expense
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditExpenseFragment : Fragment() {

    private var _binding: FragmentEditExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ExpenseRepository

    private var expenseId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        expenseId =
            arguments?.getLong(ARG_EXPENSE_ID) ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditExpenseBinding.inflate(
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

        setupCategory()
        setupDate()
        loadExpense()

        binding.btnUpdateExpense.setOnClickListener {

            updateExpense()
        }
    }

    private fun setupCategory() {

        val categories = arrayOf(
            "Food",
            "Transport",
            "Shopping",
            "Bills",
            "Entertainment",
            "Health",
            "Education",
            "Other"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        binding.spinnerCategory.adapter = adapter
    }

    private fun loadExpense() {

        val expense =
            repository.getExpense(expenseId)

        if (expense == null) {
            requireActivity().finish()
            return
        }

        binding.etTitle.setText(expense.title)
        binding.etAmount.setText(
            expense.amount.toString()
        )
        binding.etDate.setText(expense.date)
        binding.etNotes.setText(expense.notes)

        val adapter = binding.spinnerCategory.adapter

        if (adapter != null) {
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString() == expense.category) {
                    binding.spinnerCategory.setSelection(i)
                    break
                }
            }
        }
    }

    private fun setupDate() {

        binding.etDate.setOnClickListener {

            val calendar = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->

                    val selected =
                        Calendar.getInstance()

                    selected.set(
                        year,
                        month,
                        day
                    )

                    val format =
                        SimpleDateFormat(
                            "dd MMM yyyy",
                            Locale.getDefault()
                        )

                    binding.etDate.setText(
                        format.format(selected.time)
                    )

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateExpense() {

        val title =
            binding.etTitle.text.toString().trim()

        val amount =
            binding.etAmount.text
                .toString()
                .trim()
                .toDoubleOrNull()

        val category =
            binding.spinnerCategory.selectedItem
                .toString()

        val date =
            binding.etDate.text.toString().trim()

        val notes =
            binding.etNotes.text.toString().trim()

        if (title.isEmpty()) {
            binding.etTitle.error =
                "Enter title"
            return
        }

        if (amount == null || amount <= 0) {
            binding.etAmount.error =
                "Enter valid amount"
            return
        }

        if (date.isEmpty()) {
            binding.etDate.error =
                "Select date"
            return
        }

        val expense = Expense(
            id = expenseId,
            title = title,
            amount = amount,
            category = category,
            date = date,
            notes = notes
        )

        repository.updateExpense(expense)

        Toast.makeText(
            requireContext(),
            "Expense updated",
            Toast.LENGTH_SHORT
        ).show()

        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val ARG_EXPENSE_ID =
            "expense_id"

        fun newInstance(id: Long):
                EditExpenseFragment {

            return EditExpenseFragment().apply {

                arguments = Bundle().apply {
                    putLong(ARG_EXPENSE_ID, id)
                }
            }
        }
    }
}