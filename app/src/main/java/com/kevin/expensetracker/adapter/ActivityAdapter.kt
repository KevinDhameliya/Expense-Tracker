package com.kevin.expensetracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.expensetracker.model.Expense
import java.util.Locale
import com.kevin.expensetracker.databinding.ItemActivityBinding

class ActivityAdapter(
    private val onClick: (Expense) -> Unit
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    private val activities = mutableListOf<Expense>()

    fun submitList(newList: List<Expense>) {

        activities.clear()
        activities.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityViewHolder {

        val binding = ItemActivityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ActivityViewHolder,
        position: Int
    ) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    inner class ActivityViewHolder(
        private val binding: ItemActivityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {

            binding.tvActivityItemTitle.text =
                "Added expense"

            binding.tvActivityDescription.text =
                "${expense.category} • ${expense.title}"

            binding.tvActivityDate.text =
                expense.date

            binding.tvActivityAmount.text =
                String.format(
                    Locale.getDefault(),
                    "₹%.2f",
                    expense.amount
                )

            binding.root.setOnClickListener {
                onClick(expense)
            }
        }
    }
}