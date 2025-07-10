package com.htw.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.htw.expensetracker.data.Category
import com.htw.expensetracker.ui.graphics.TransactionChartView
import com.htw.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            expensesUi(exampleDataSet) // TODO change
        }
    }
}

val defaultHorizontalPadding = 12.dp

val exampleDataSet: List<Category> = listOf(Category("Transfers", 13.3f, android.graphics.Color.RED),
    Category("Travel", 50.0f, android.graphics.Color.BLUE),
    Category("Food", 20.5f, android.graphics.Color.MAGENTA),
    Category("Taxes", 33.0f, android.graphics.Color.GREEN),
)

@Composable
fun expensesUi(categoryDataset: List<Category>) {
    ExpenseTrackerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                Text(
                    text = stringResource(R.string.my_expenses),
                    modifier = Modifier.padding(horizontal = defaultHorizontalPadding, vertical = 4.dp),
                )
                transactionChart(categoryDataset)
                Text(
                    text = stringResource(R.string.total_expenses) + ": " + calculateAmount(categoryDataset).toString()
                            + " " + stringResource(R.string.currency_eur),
                    modifier = Modifier.padding(horizontal = defaultHorizontalPadding, vertical = 4.dp)
                )
                categoryList(categoryDataset)
                editCategoriesButton()
            }
        }
    }
}

@Composable
@Preview
fun expensesUiPreview() {
    expensesUi(exampleDataSet)
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
    }
}

@Composable
@Preview
fun categoryListPreview() {
    categoryList(categories = exampleDataSet)
}

fun calculateAmount(categories: List<Category>): Float {
    // TODO
    var totalAmount = 0f
    categories.forEach { category -> totalAmount += category.amount }
    return totalAmount
}

@Composable
fun transactionChart(categories: List<Category>) {
    AndroidView(
        modifier = Modifier.width(250.dp).height(250.dp).offset(x = defaultHorizontalPadding),
        factory = { context ->
            TransactionChartView(context, categories)
        }
    )
}

@Composable
@Preview
fun editCategoriesButton() {
    // shape, elevation
    FilledTonalButton(
        onClick = {
        // todo
    },
        modifier = Modifier.fillMaxWidth().padding(horizontal = defaultHorizontalPadding),
    ) {
        Text(stringResource(R.string.edit_categories))
    }
}
