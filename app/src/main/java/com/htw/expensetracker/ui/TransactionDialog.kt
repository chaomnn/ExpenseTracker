package com.htw.expensetracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.htw.expensetracker.R
import com.htw.expensetracker.data.DataSource
import com.htw.expensetracker.data.Transaction
import java.util.UUID

@Composable
fun EditTransactionDialog(onDismissRequest: () -> Unit, onSaveRequest: (transaction: Transaction) -> Unit) {
    val transactionDescription = remember { mutableStateOf("") }
    val transactionAmount = remember { mutableStateOf("") }
//    val transactionCategory = remember { mutableStateOf() } TODO add category selection
//    val transactionDate = remember { mutableStateOf() } TODO add date selection

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
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

                TextButton(
                    onClick = {
                        // TODO save to local database and cloud
                        val newTransaction = Transaction(id = UUID.randomUUID().toString(),
                            description = transactionDescription.value,
                            date = "2025-01-01", // TODO change
                            amount = (transactionAmount.value).toFloat(),
                            category = DataSource.defaultCategory) // TODO change
                        onSaveRequest(newTransaction)
                        onDismissRequest()
                    },
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}