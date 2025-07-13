package com.htw.expensetracker.data

// Stub data source class
object DataSource {
    // TODO add local data base
    // TODO integrate with cloud and fetch data from there

    private val categoriesExample: ArrayList<Category> = arrayListOf(
        Category("1","Transfers", 13.3f, android.graphics.Color.RED),
        Category("2","Travel", 50.0f, android.graphics.Color.BLUE),
        Category("3","Food", 20.5f, android.graphics.Color.MAGENTA),
        Category("4","Taxes", 33.0f, android.graphics.Color.GREEN),
    )

    val defaultCategory = Category("5","Default", 0f, android.graphics.Color.YELLOW)

    private val transactionsExample: ArrayList<Transaction> = arrayListOf(
        Transaction("1","Lidl", "10.07.2025", 12f, defaultCategory.id),
        Transaction("2","Rewe", "09.07.2025", 13f, defaultCategory.id),
        Transaction("3","Edeka", "08.07.2025", 15f, defaultCategory.id),
        Transaction("4","Rossman", "07.07.2025", 19f, defaultCategory.id),
        Transaction("5","Apotheke", "06.07.2025", 22f, defaultCategory.id),
        Transaction("6","Money transfer", "05.07.2025", 11f, defaultCategory.id),
        Transaction("7","Coffee", "04.07.2025", 5f, defaultCategory.id),
        Transaction("8","Train ticket", "03.07.2025", 59f, defaultCategory.id),
        Transaction("9","Breakfast", "02.07.2025", 15f, defaultCategory.id)
    )

    fun fetchCategories(): ArrayList<Category> {
        // Stub
        return categoriesExample
    }

    fun fetchTransactions(): ArrayList<Transaction> {
        // Stub
        return transactionsExample
    }
}
