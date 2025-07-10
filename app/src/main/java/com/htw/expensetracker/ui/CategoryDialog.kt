package com.htw.expensetracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import com.htw.expensetracker.R
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.htw.expensetracker.data.Category
import com.htw.expensetracker.ui.graphics.colorPicker

@Composable
fun addCategoryDialog(onDismissRequest: () -> Unit, onSaveRequest: (category: Category) -> Unit) {
    var categoryColor by remember { mutableStateOf(0) }
    var categoryName by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                colorPicker(onChange = { categoryColor = it })
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Label") },
                    placeholder = { stringResource(R.string.category_name) }
                )
                TextButton(
                    onClick = {
                        // TODO save category to database
                        val newCategory = Category(name = categoryName, clr = categoryColor, amount = 10f)
                        onSaveRequest(newCategory)
                        onDismissRequest()
                              },
                    modifier = Modifier.padding(8.dp).align(Alignment.End),
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}
