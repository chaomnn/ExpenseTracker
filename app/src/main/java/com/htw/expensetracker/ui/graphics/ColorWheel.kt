package com.htw.expensetracker.ui.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun colorWheel(
    modifier: Modifier = Modifier,
    wheelSize: Dp = 200.dp,
    hue: Float,
    saturation: Float,
    onChange: (hue: Float, saturation: Float) -> Unit
) {
    val sizePx = with(LocalDensity.current) { wheelSize.toPx() }
    val radius = sizePx / 2f
    val thumbRadius = with(LocalDensity.current) { 10.dp.toPx() }
    val thumbStroke = with(LocalDensity.current) { 1.dp.toPx() }
    val marginPx = with(LocalDensity.current) { 2.dp.toPx() }

    Box(
        modifier = modifier
            .size(wheelSize)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val center = Offset(radius, radius)
                    val pos = change.position
                    val dx = pos.x - center.x
                    val dy = pos.y - center.y
                    val dist = sqrt(dx * dx + dy * dy).coerceAtMost(radius)
                    val newSaturation = (dist / radius).coerceIn(0f, 1f)
                    var newHue = Math.toDegrees(atan2(dy, dx).toDouble()).toFloat()
                    if (newHue < 0) newHue += 360f
                    onChange(newHue, newSaturation)
                }
            }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val hueColors = listOf(
                Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red
            )
            val center = Offset(radius, radius)
            val sweepBrush = Brush.sweepGradient(colors = hueColors, center = center)
            val radialBrush = Brush.radialGradient(
                colors = listOf(Color.White, Color.Transparent),
                center = center,
                radius = radius
            )

            drawCircle(
                brush = sweepBrush,
                center = center,
                radius = radius - marginPx
            )
            drawCircle(
                brush = radialBrush,
                center = center,
                radius = radius - marginPx
            )
            // Draw thumb
            val angleRad = Math.toRadians(hue.toDouble())
            val selX = (cos(angleRad) * saturation * radius + radius).toFloat()
            val selY = (sin(angleRad) * saturation * radius + radius).toFloat()
            drawCircle(
                color = Color.Black,
                center = Offset(selX, selY),
                radius = thumbRadius,
                style = Stroke(width = thumbStroke)
            )
        }
    }
}
