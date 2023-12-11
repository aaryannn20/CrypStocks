package com.starorigins.crypstocks.ui.components.charts.graph.yaxis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max
import kotlin.math.roundToInt


class SimpleYAxisDrawer(
    private val labelTextSize: TextUnit = 12.sp,
    private val labelTextColor: Color = Color.Black,
    private val labelRatio: Int = 3,
    private val labelValueFormatter: (Any) -> String = { value -> "%.1f".format(value) },
    private val axisLineThickness: Dp = 1.dp,
    private val axisLineColor: Color = Color.Black
) : YAxisDrawer {
    private val axisLinePaint = androidx.compose.ui.graphics.Paint().apply {
        isAntiAlias = true
        color = axisLineColor
        style = PaintingStyle.Stroke
    }
    private val textPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        color = labelTextColor.toArgb()
    }
    private val textBounds = android.graphics.Rect()

    override fun calculateWidth(drawScope: DrawScope) = with(drawScope) {
        minOf(50.dp.toPx(), size.width * 10f / 100f)
    }

    override fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        minValue: Float,
        maxValue: Float
    ) = with(drawScope) {
        // Draw axis
        val lineThickness = axisLineThickness.toPx()
        val axisX = drawableArea.right - lineThickness / 2f
        canvas.drawLine(
            p1 = Offset(x = axisX, y = drawableArea.top),
            p2 = Offset(x = axisX, y = drawableArea.bottom),
            paint = axisLinePaint.apply { strokeWidth = lineThickness }
        )

        // Draw labels
        val labelPaint = textPaint.apply {
            textSize = labelTextSize.toPx()
            textAlign = android.graphics.Paint.Align.RIGHT
        }
        val minLabelHeight = (labelTextSize.toPx() * labelRatio.toFloat())
        val totalHeight = drawableArea.height
        val labelCount = max((drawableArea.height / minLabelHeight).roundToInt(), 2)

        for (i in 0..labelCount) {
            val value = minValue + (i * ((maxValue - minValue) / labelCount))

            val label = labelValueFormatter(value)
            val x = drawableArea.right - axisLineThickness.toPx() - labelTextSize.toPx() / 2f

            labelPaint.getTextBounds(label, 0, label.length, textBounds)

            val y = drawableArea.bottom - (i * (totalHeight / labelCount)) +
                (textBounds.height() / 2f)

            canvas.nativeCanvas.drawText(label, x, y, labelPaint)
        }
    }
}