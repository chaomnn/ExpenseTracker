package com.htw.expensetracker.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.htw.expensetracker.NavDestinations
import com.htw.expensetracker.R
import com.htw.expensetracker.data.Category
import com.htw.expensetracker.defaultPadding
import com.htw.expensetracker.ui.graphics.transactionChart

@Composable
fun CategoryScreen(
    innerPadding: PaddingValues,
    categoryDataset: List<Category>,
    navController: NavController,
    deleteCategory: (category: Category) -> Unit
) {
    Column(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        transactionChart(categoryDataset,
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
                .offset(x = defaultPadding, y = defaultPadding))
        Text(
            text = stringResource(R.string.total_expenses) + ": " + calculateAmount(categoryDataset).toString()
                    + " " + stringResource(R.string.currency_eur),
            modifier = Modifier.padding(top = defaultPadding, bottom = defaultPadding)
        )
        CategoryList(categoryDataset, navController, deleteCategory)
    }
}

@Composable
fun CategoryItem(category: Category, onDeleteButtonClicked: () -> Unit) {
    val menuExpanded = remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { menuExpanded.value = true }) {
            Row(modifier = Modifier.padding(all = 8.dp)) {
                Canvas(modifier = Modifier.size(20.dp).padding(end = 4.dp, top = 4.dp)) {
                    drawCircle(
                        color = Color(category.clr),
                        radius = 5f,
                    )
                }
                Text(text = category.name, modifier = Modifier.weight(1f))
                Text(text = category.amount.toString() + " " + stringResource(R.string.currency_eur))
            }
        }
        DropdownMenu(
            expanded = menuExpanded.value,
            onDismissRequest = { menuExpanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.delete_category)) },
                onClick = {
                    // Show alertDialog with confirmation of category deletion
                    menuExpanded.value = false
                    onDeleteButtonClicked()
                }
            )
        }
    }
}

@Composable
fun DeleteCategoryAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    categoryName: String
) {
    AlertDialog(
        text = {
            Text(text = stringResource(R.string.delete_category) + " \"" + categoryName + "\"?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.dismiss))
            }
        }
    )
}

@Composable
fun CategoryList(categories: List<Category>, navController: NavController,
                 deleteCategory: (category: Category) -> Unit) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val currentCategoryToDelete = remember { mutableStateOf<Category?>(null) }
    LazyColumn {
        items(categories) { category ->
            CategoryItem(category, onDeleteButtonClicked = { currentCategoryToDelete.value = category
                showDeleteDialog.value = true })
        }
        item {
            AllTransactionsButton(navController)
        }
    }
    when {
        showDeleteDialog.value -> {
            DeleteCategoryAlertDialog(
                onDismissRequest = { showDeleteDialog.value = false },
                onConfirmation = {
                    showDeleteDialog.value = false
                    deleteCategory(currentCategoryToDelete.value!!)
                },
                currentCategoryToDelete.value?.name ?: ""
            )
        }
    }
}

@Composable
fun AllTransactionsButton(navController: NavController) {
    FilledTonalButton(
        onClick = {
            navController.navigate(NavDestinations.TRANSACTIONS)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = defaultPadding),
    ) {
        Text(stringResource(R.string.all_transactions))
    }
}

fun calculateAmount(categories: List<Category>): Float {
    // TODO change
    var totalAmount = 0f
    categories.forEach { category -> totalAmount += category.amount }
    return totalAmount
}