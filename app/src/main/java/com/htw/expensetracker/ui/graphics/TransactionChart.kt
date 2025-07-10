package com.htw.expensetracker.ui.graphics

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.View
import com.htw.expensetracker.calculateAmount
import com.htw.expensetracker.data.Category

class TransactionChart(private val categories: List<Category>) : Drawable() {

    companion object {
        private const val C_ANGLE = 360f
        private const val GAP_ANGLE = 2f
    }

    private val chartPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val slicePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // TODO add colors dynamically, add color customization during category creation (color wheel)

    override fun draw(canvas: Canvas) {

        chartPaint.color = Color.WHITE
        val bounds = RectF(bounds)
        val innerCircleRadius = minOf(bounds.width(), bounds.height()) / 6f
        val startAngle = 0f

        for (i in categories.indices) {
            slicePaint.color = categories[i].clr
            val slicePercentage = (categories[i].amount / (calculateAmount(categories) / 100)).toDouble()
            val endAngle = (C_ANGLE / 100 * slicePercentage).toFloat()

            canvas.drawArc(bounds, startAngle, endAngle - GAP_ANGLE, true, slicePaint)
            canvas.rotate(endAngle - GAP_ANGLE, bounds.centerX(), bounds.centerY())
            canvas.drawArc(bounds, startAngle, GAP_ANGLE, true, chartPaint)
            canvas.rotate(GAP_ANGLE, bounds.centerX(), bounds.centerY())
        }
        canvas.drawCircle(bounds.width() / 2, bounds.height() / 2, innerCircleRadius, chartPaint)
    }

    override fun setAlpha(alpha: Int) { /* No-op */ }

    override fun setColorFilter(colorFilter: ColorFilter?) { /* No-op */ }

    override fun getOpacity(): Int = PixelFormat.OPAQUE
}

@SuppressLint("ViewConstructor")
class TransactionChartView(context: Context, private val categories: List<Category>) : View(context) {

    private val chartDrawable = TransactionChart(categories)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        chartDrawable.setBounds(0, 0, width, height)
        chartDrawable.draw(canvas)
    }
}
