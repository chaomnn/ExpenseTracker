package com.htw.expensetracker.ui.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun colorSlider(
    hue: Float,
    saturation: Float,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    sliderHeight: Dp = 200.dp,
    sliderCornerRadius: Dp = 12.dp,
    thumbRadius: Dp = 10.dp
) {
    val heightPx = with(LocalDensity.current) { sliderHeight.toPx() }
    val cornerPx = with(LocalDensity.current) { sliderCornerRadius.toPx() }
    val thumbPx = with(LocalDensity.current) { thumbRadius.toPx() }
    val selectorStroke = with(LocalDensity.current) { 1.dp.toPx() }

    // Compute the start color and end color
    val topColor = Color.hsv(hue, saturation, 1f)
    val bottomColor = Color.Black

    Box(
        modifier = modifier
            .height(sliderHeight)
            .width(15.dp)
            .pointerInput(hue, saturation) {
                detectVerticalDragGestures { change, dragAmount ->
                    val y = change.position.y.coerceIn(0f, heightPx)
                    // Map y: 0 (top) => value=1, heightPx (bottom) => value=0
                    val newValue = (1f - y / heightPx).coerceIn(0f, 1f)
                    onValueChange(newValue)
                }
            }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val rect = Rect(
                left = 0f,
                top = 0f,
                right = size.width,
                bottom = size.height
            )

            // Draw the vertical gradient
            drawRoundRect(
                brush = Brush.verticalGradient(listOf(topColor, bottomColor)),
                topLeft = rect.topLeft,
                size = rect.size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerPx, cornerPx)
            )

            // Draw thumb
            val y = (1f - value) * size.height
            drawCircle(
                color = Color.Black,
                center = Offset(size.width / 2f, y),
                radius = thumbPx,
                style = Stroke(width = selectorStroke)
            )
        }
    }
}
