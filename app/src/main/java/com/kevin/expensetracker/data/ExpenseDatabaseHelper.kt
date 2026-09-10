package com.kevin.expensetracker.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kevin.expensetracker.model.Expense

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE $TABLE_EXPENSES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_AMOUNT REAL NOT NULL,
                $COLUMN_CATEGORY TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_NOTES TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }

    fun insertExpense(expense: Expense): Long {

        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITLE, expense.title)
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY, expense.category)
            put(COLUMN_DATE, expense.date)
            put(COLUMN_NOTES, expense.notes)
        }

        return db.insert(
            TABLE_EXPENSES,
            null,
            values
        )
    }

    fun getAllExpenses(): List<Expense> {

        val expenses = mutableListOf<Expense>()

        val db = readableDatabase

        val cursor = db.query(
            TABLE_EXPENSES,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_ID DESC"
        )

        cursor.use {

            while (it.moveToNext()) {

                expenses.add(
                    Expense(
                        id = it.getLong(
                            it.getColumnIndexOrThrow(COLUMN_ID)
                        ),
                        title = it.getString(
                            it.getColumnIndexOrThrow(COLUMN_TITLE)
                        ),
                        amount = it.getDouble(
                            it.getColumnIndexOrThrow(COLUMN_AMOUNT)
                        ),
                        category = it.getString(
                            it.getColumnIndexOrThrow(COLUMN_CATEGORY)
                        ),
                        date = it.getString(
                            it.getColumnIndexOrThrow(COLUMN_DATE)
                        ),
                        notes = it.getString(
                            it.getColumnIndexOrThrow(COLUMN_NOTES)
                        ) ?: ""
                    )
                )
            }
        }

        return expenses
    }

    fun getExpenseById(id: Long): Expense? {

        val db = readableDatabase

        val cursor = db.query(
            TABLE_EXPENSES,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use {

            if (it.moveToFirst()) {

                return Expense(
                    id = it.getLong(
                        it.getColumnIndexOrThrow(COLUMN_ID)
                    ),
                    title = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_TITLE)
                    ),
                    amount = it.getDouble(
                        it.getColumnIndexOrThrow(COLUMN_AMOUNT)
                    ),
                    category = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_CATEGORY)
                    ),
                    date = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_DATE)
                    ),
                    notes = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_NOTES)
                    ) ?: ""
                )
            }
        }

        return null
    }

    fun updateExpense(expense: Expense): Int {

        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITLE, expense.title)
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY, expense.category)
            put(COLUMN_DATE, expense.date)
            put(COLUMN_NOTES, expense.notes)
        }

        return db.update(
            TABLE_EXPENSES,
            values,
            "$COLUMN_ID = ?",
            arrayOf(expense.id.toString())
        )
    }

    fun deleteExpense(id: Long): Int {

        val db = writableDatabase

        return db.delete(
            TABLE_EXPENSES,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    fun getTotalExpense(): Double {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES",
            null
        )

        cursor.use {

            if (it.moveToFirst()) {
                return it.getDouble(0)
            }
        }

        return 0.0
    }

    companion object {

        private const val DATABASE_NAME = "expense_tracker.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_EXPENSES = "expenses"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"
        const val COLUMN_NOTES = "notes"
    }
}
