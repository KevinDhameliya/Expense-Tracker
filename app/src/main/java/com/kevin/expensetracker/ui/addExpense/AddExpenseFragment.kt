package com.kevin.expensetracker.ui.addExpense

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kevin.expensetracker.R
import com.kevin.expensetracker.data.ExpenseRepository
import com.kevin.expensetracker.databinding.FragmentAddExpenseBinding
import com.kevin.expensetracker.model.Expense
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseFragment : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ExpenseRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddExpenseBinding.inflate(
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
        setupSaveButton()
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

    private fun setupDate() {

        binding.etDate.setOnClickListener {

            val calendar = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->

                    val selectedCalendar =
                        Calendar.getInstance()

                    selectedCalendar.set(
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
                        format.format(
                            selectedCalendar.time
                        )
                    )

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupSaveButton() {

        binding.btnSaveExpense.setOnClickListener {

            saveExpense()
        }
    }

    private fun saveExpense() {

        val title =
            binding.etTitle.text.toString().trim()

        val amountText =
            binding.etAmount.text.toString().trim()

        val date =
            binding.etDate.text.toString().trim()

        val notes =
            binding.etNotes.text.toString().trim()

        val category =
            binding.spinnerCategory.selectedItem.toString()

        if (title.isEmpty()) {

            binding.etTitle.error =
                "Enter expense title"

            return
        }

        if (amountText.isEmpty()) {

            binding.etAmount.error =
                "Enter amount"

            return
        }

        val amount =
            amountText.toDoubleOrNull()

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
            title = title,
            amount = amount,
            category = category,
            date = date,
            notes = notes
        )

        repository.addExpense(expense)

        Toast.makeText(
            requireContext(),
            "Expense added successfully",
            Toast.LENGTH_SHORT
        ).show()

        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}