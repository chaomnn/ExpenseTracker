package com.htw.expensetracker.ui.graphics

import androidx.compose.runtime.Composable
import com.htw.expensetracker.calculateAmount
import com.htw.expensetracker.data.Category
import androidx.compose.foundation.Canvas
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import kotlin.math.min

@Composable
fun transactionChart(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    // Calculate total amount
    val total = calculateAmount(categories)
    val gapAngle = 2f
    val fullAngle = 360f

    Canvas(modifier = modifier) {
        // Bounds of the outer circle
        val diameter = min(size.width, size.height)
        val radius = diameter / 2f
        val innerRadius = diameter / 6f
        val center = center
        val arcRect = Rect(
            left = center.x - radius,
            top = center.y - radius,
            right = center.x + radius,
            bottom = center.y + radius
        )
        // Pie slices
        var startAngle = 0f
        categories.forEach { category ->
            val sweepAngle = if (total == 0f) 0f
            else (category.amount / total * fullAngle)
            // Draw slice
            drawArc(
                color = Color(category.clr),
                startAngle = startAngle,
                sweepAngle = sweepAngle - gapAngle,
                useCenter = true,
                topLeft = arcRect.topLeft,
                size = arcRect.size
            )
            // Optional: draw gap as white arc
            if (sweepAngle > gapAngle) {
                drawArc(
                    color = Color.White,
                    startAngle = startAngle + (sweepAngle - gapAngle),
                    sweepAngle = gapAngle,
                    useCenter = true,
                    topLeft = arcRect.topLeft,
                    size = arcRect.size
                )
            }
            startAngle += sweepAngle
        }
        // Draw center circle (for donut effect)
        drawCircle(
            color = Color.White,
            radius = innerRadius,
            center = center
        )
    }
}