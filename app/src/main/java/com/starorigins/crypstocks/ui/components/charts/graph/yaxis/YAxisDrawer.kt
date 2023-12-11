package com.starorigins.crypstocks.ui.components.charts.graph.yaxis

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface YAxisDrawer {
    fun calculateWidth(drawScope: DrawScope): Float

    fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        minValue: Float,
        maxValue: Float
    )
}
