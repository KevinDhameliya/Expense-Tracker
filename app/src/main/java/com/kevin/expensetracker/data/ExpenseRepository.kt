package com.kevin.expensetracker.data

import android.content.Context
import com.kevin.expensetracker.model.Expense

class ExpenseRepository(context: Context) {

    private val databaseHelper =
        ExpenseDatabaseHelper(context)

    fun addExpense(expense: Expense): Long {
        return databaseHelper.insertExpense(expense)
    }

    fun getExpenses(): List<Expense> {
        return databaseHelper.getAllExpenses()
    }

    fun getExpense(id: Long): Expense? {
        return databaseHelper.getExpenseById(id)
    }

    fun updateExpense(expense: Expense): Int {
        return databaseHelper.updateExpense(expense)
    }

    fun deleteExpense(id: Long): Int {
        return databaseHelper.deleteExpense(id)
    }

    fun getTotalExpense(): Double {
        return databaseHelper.getTotalExpense()
    }
}