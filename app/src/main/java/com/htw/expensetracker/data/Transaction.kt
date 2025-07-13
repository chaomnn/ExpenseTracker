package com.htw.expensetracker.data

data class Transaction(val id: String, val description: String, val date: String, val amount: Float, val categoryId: String)
