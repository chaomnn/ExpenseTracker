package com.htw.expensetracker.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun categoryScreen(
    innerPadding: PaddingValues,
    categoryDataset: List<Category>,
    navController: NavController
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
        categoryList(categoryDataset, navController)
    }
}

@Composable
fun categoryItem(category: Category) {
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

@Composable
fun categoryList(categories: List<Category>, navController: NavController) {
    LazyColumn {
        items(categories) { category ->
            categoryItem(category)
        }
        item {
            allTransactionsButton(navController)
        }
    }
}

@Composable
fun allTransactionsButton(navController: NavController) {
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

fun onClickCategory(category: Category) {
    // TODO on long tap: show "edit category" button,
    //  after click on which the edit category dialog shows
    //  and "delete category button"
    //  on short tap: show all items in category
}

fun calculateAmount(categories: List<Category>): Float {
    // TODO change
    var totalAmount = 0f
    categories.forEach { category -> totalAmount += category.amount }
    return totalAmount
}