package com.htw.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.htw.expensetracker.data.Category
import com.htw.expensetracker.ui.addCategoryDialog
import com.htw.expensetracker.ui.graphics.transactionChart
import com.htw.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            expensesApp() // TODO change
        }
    }
}

val defaultPadding = 12.dp

// TODO remove
val exampleDataSet: ArrayList<Category> = arrayListOf(Category("Transfers", 13.3f, android.graphics.Color.RED),
    Category("Travel", 50.0f, android.graphics.Color.BLUE),
    Category("Food", 20.5f, android.graphics.Color.MAGENTA),
    Category("Taxes", 33.0f, android.graphics.Color.GREEN),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun expensesApp() {
    // TODO fill data set
    val dataSet: ArrayList<Category> by remember { mutableStateOf(exampleDataSet) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Dialog state
    val showDialog = remember { mutableStateOf(false) }
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
                FloatingActionButton(onClick = { showDialog.value = true }) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_category))
                }
            }
        ) {
            innerPadding -> scrollContent(innerPadding, dataSet)
            if (showDialog.value) {
                addCategoryDialog(
                    onDismissRequest = { showDialog.value = false },
                    onSaveRequest = { category -> dataSet.add(category)}
                )
            }
        }
    }
}

@Composable
fun scrollContent(innerPadding: PaddingValues, categoryDataset: List<Category>) {
    Column(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        transactionChart(categoryDataset,
            modifier = Modifier.width(250.dp).height(250.dp).offset(x = defaultPadding, y = defaultPadding))
        Text(
            text = stringResource(R.string.total_expenses) + ": " + calculateAmount(categoryDataset).toString()
                    + " " + stringResource(R.string.currency_eur),
            modifier = Modifier.padding(top = defaultPadding, bottom = defaultPadding)
        )
        categoryList(categoryDataset)
    }
}

@Composable
@Preview
fun expensesAppPreview() {
    expensesApp()
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
@Preview
fun categoryItemPreview() {
    categoryItem(category = Category("Transfers", 13.3f, android.graphics.Color.CYAN))
}

@Composable
fun categoryList(categories: List<Category>) {
    LazyColumn {
        items(categories) { category ->
            categoryItem(category)
        }
        item {
            allTransactionsButton()
        }
    }
}

@Composable
@Preview
fun categoryListPreview() {
    categoryList(categories = exampleDataSet)
}

fun calculateAmount(categories: List<Category>): Float {
    // TODO change
    var totalAmount = 0f
    categories.forEach { category -> totalAmount += category.amount }
    return totalAmount
}

@Composable
@Preview
fun allTransactionsButton() {
    FilledTonalButton(
        onClick = {
        // todo switch to all transactions fragment list
    },
        modifier = Modifier.fillMaxWidth().padding(horizontal = defaultPadding),
    ) {
        Text(stringResource(R.string.all_transactions))
    }
}

fun onClickCategory(category: Category) {
    // TODO on long tap: show "edit category" button,
    //  on short tap: show all items in category
}
