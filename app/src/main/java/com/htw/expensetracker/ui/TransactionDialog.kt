package com.htw.expensetracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.htw.expensetracker.R
import com.htw.expensetracker.data.Category
import com.htw.expensetracker.data.Transaction
import java.util.UUID

private const val NO_CATEGORY_ID = "no-category"

@Composable
fun EditTransactionDialog(onDismissRequest: () -> Unit,
                          onSaveRequest: (transaction: Transaction) -> Unit,
                          categoryList: List<Category>) {
    val transactionDescription = remember { mutableStateOf("") }
    val transactionAmount = remember { mutableStateOf("") }
    val transactionCategoryId = remember { mutableStateOf(NO_CATEGORY_ID) }
//    val transactionDate = remember { mutableStateOf() } TODO add date selection

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .padding(16.dp),
            shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = stringResource(R.string.add_transaction))
                OutlinedTextField(
                    value = transactionDescription.value,
                    onValueChange = { transactionDescription.value = it },
                    label = { Text(stringResource(R.string.description)) },
                )

                OutlinedTextField(
                    value = transactionAmount.value,
                    onValueChange = { newValue ->
                        // Only accept numbers and one decimal point
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            transactionAmount.value = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.amount_eur)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                )
                SelectCategoryDropdownMenu(categoryList = categoryList,
                    onCategoryClick = { transactionCategoryId.value = it.id })
                TextButton(
                    onClick = {
                        // don't save if one of the fields is empty
                        if (transactionDescription.value.isNotEmpty() && transactionAmount.value.isNotEmpty()
                            && transactionCategoryId.value != NO_CATEGORY_ID) {
                            // TODO save to local database and cloud
                            val newTransaction = Transaction(id = UUID.randomUUID().toString(),
                                description = transactionDescription.value,
                                date = "2025-01-01", // TODO change
                                amount = (transactionAmount.value).toFloat(),
                                categoryId = transactionCategoryId.value)
                            onSaveRequest(newTransaction)
                            onDismissRequest()
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}

@Composable
fun SelectCategoryDropdownMenu(categoryList: List<Category>, onCategoryClick: (selectedCategory: Category) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val buttonPlaceholder = stringResource(R.string.select_category)
    var selectedCategory by remember { mutableStateOf(buttonPlaceholder) }
    Box(modifier = Modifier.padding(vertical = 8.dp)) {
        SelectCategoryButton(onClick = { expanded = !expanded }, selectedCategory)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categoryList.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategoryClick(category)
                        selectedCategory = category.name
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SelectCategoryButton(onClick: () -> Unit, selectedCategoryName: String) {
    OutlinedButton(onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp)
    ) {
        Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.select_category))
        Text(selectedCategoryName)
    }
}
