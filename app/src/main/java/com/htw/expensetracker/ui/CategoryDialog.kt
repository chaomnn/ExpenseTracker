package com.htw.expensetracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import com.htw.expensetracker.R
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.htw.expensetracker.data.Category
import com.htw.expensetracker.ui.graphics.colorPicker
import java.util.UUID

// TODO make the same dialog appear when editing a category
@Composable
fun EditCategoryDialog(onDismissRequest: () -> Unit, onSaveRequest: (category: Category) -> Unit) {
    val categoryColor = remember { mutableStateOf(0) }
    val categoryName = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = stringResource(R.string.add_category))
                colorPicker(onChange = { categoryColor.value = it })
                OutlinedTextField(
                    value = categoryName.value,
                    onValueChange = { categoryName.value = it },
                    label = { Text(stringResource(R.string.category_name)) }
                )
                TextButton(
                    onClick = {
                        // TODO save to database and cloud
                        val newCategory = Category(id = UUID.randomUUID().toString(), name = categoryName.value,
                            clr = categoryColor.value, amount = 10f)
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
