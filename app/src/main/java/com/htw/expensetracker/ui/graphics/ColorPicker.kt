package com.htw.expensetracker.ui.graphics

import android.graphics.Color.HSVToColor
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.htw.expensetracker.R

@Composable
fun colorPicker(onChange: (categoryColor: Int) -> Unit) {
    var hue by remember { mutableStateOf(0f) }
    var saturation by remember { mutableStateOf(0f) }
    var value by remember { mutableStateOf(1f) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 12.dp)) {
        Row {
            Text(stringResource(R.string.selected_color))
            Canvas(modifier = Modifier.size(15.dp).offset(x = 4.dp, y = 5.dp)) {
                onChange(getArgbColor(hue, saturation, value))
                drawRect(Color.hsv(hue, saturation, value))
            }
        }
        Row {
            colorWheel(
                modifier = Modifier.padding(horizontal = 12.dp),
                hue = hue,
                saturation = saturation,
                onChange = { h, s ->
                    hue = h
                    saturation = s
                }
            )
            colorSlider(
                hue = hue,
                saturation = saturation,
                value = value,
                onValueChange = { value = it }
            )
        }
    }
}

fun getArgbColor(hue: Float, saturation: Float, value: Float?): Int {
    return HSVToColor(floatArrayOf(hue, saturation, value ?: 1f))
}
