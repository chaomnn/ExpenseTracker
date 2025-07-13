package com.htw.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.htw.expensetracker.ui.theme.ExpenseTrackerTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.htw.expensetracker.data.DataSource
import com.htw.expensetracker.ui.EditCategoryDialog
import com.htw.expensetracker.ui.EditTransactionDialog
import com.htw.expensetracker.ui.CategoryScreen
import com.htw.expensetracker.ui.transactionsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpensesApp() // TODO change
        }
    }
}

object NavDestinations {
    const val CATEGORIES = "categories"
    const val TRANSACTIONS = "transactions"
}

val appRoutes = listOf(NavDestinations.CATEGORIES, NavDestinations.TRANSACTIONS)

val defaultPadding = 12.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = appRoutes.find { it == currentDestination?.route } ?: NavDestinations.CATEGORIES

    // TODO change
    var categoriesList by remember { mutableStateOf(DataSource.fetchCategories().toList()) }
    var transactionsList by remember { mutableStateOf(DataSource.fetchTransactions().toList()) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val showAddCategoryDialog = remember { mutableStateOf(false) }
    val showAddTransactionDialog = remember { mutableStateOf(false) }

    ExpenseTrackerTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(R.string.my_expenses))
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    when (currentScreen) {
                        NavDestinations.CATEGORIES -> showAddCategoryDialog.value = true
                        NavDestinations.TRANSACTIONS -> showAddTransactionDialog.value = true
                    }
                }) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_item))
                }
            }
        ) { // Scaffold content
            innerPadding ->
            NavHost(navController = navController, startDestination = NavDestinations.CATEGORIES) {
                composable(NavDestinations.CATEGORIES) {
                    CategoryScreen(innerPadding, categoriesList, navController,
                        deleteCategory = { categoryToDelete ->
                            categoriesList = categoriesList.filter { it.id != categoryToDelete.id }
                        })
                    if (showAddCategoryDialog.value) {
                        EditCategoryDialog(
                            onDismissRequest = { showAddCategoryDialog.value = false },
                            onSaveRequest = { category -> categoriesList = categoriesList + category }
                        )
                    }
                } // NavDestinations.CATEGORIES

                composable(NavDestinations.TRANSACTIONS) {
                    transactionsScreen(innerPadding, transactionsList)
                    if (showAddTransactionDialog.value) {
                        EditTransactionDialog(
                            onDismissRequest = { showAddTransactionDialog.value = false },
                            onSaveRequest = { transaction ->  transactionsList = transactionsList + transaction },
                            categoryList = categoriesList
                        )
                    }
                } // NavDestinations.TRANSACTIONS

            } // navHost
        } // Scaffold content end
    } // ExpenseTrackerTheme
} // expensesApp
