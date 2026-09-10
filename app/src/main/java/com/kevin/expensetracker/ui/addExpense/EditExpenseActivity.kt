package com.kevin.expensetracker.ui.addExpense

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevin.expensetracker.R

class EditExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_expense)

        val expenseId =
            intent.getLongExtra("expense_id", -1)

        if (savedInstanceState == null) {

            val fragment =
                EditExpenseFragment.newInstance(expenseId)

            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.editExpenseContainer,
                    fragment
                )
                .commit()
        }
    }
}