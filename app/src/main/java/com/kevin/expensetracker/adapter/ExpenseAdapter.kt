package com.kevin.expensetracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.expensetracker.databinding.ItemExpenseBinding
import com.kevin.expensetracker.model.Expense

class ExpenseAdapter(
    private val onEditClick: (Expense) -> Unit,
    private val onDeleteClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private val expenses = mutableListOf<Expense>()

    fun submitList(newList: List<Expense>) {

        expenses.clear()
        expenses.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseViewHolder {

        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ExpenseViewHolder,
        position: Int
    ) {
        holder.bind(expenses[position])
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    inner class ExpenseViewHolder(
        private val binding: ItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {

            binding.tvExpenseTitle.text =
                expense.title

            binding.tvExpenseDate.text =
                "${expense.category} • ${expense.date}"

            binding.tvExpenseAmount.text =
                "₹%.2f".format(expense.amount)

            binding.root.setOnClickListener {
                onEditClick(expense)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(expense)
            }
        }
    }
}