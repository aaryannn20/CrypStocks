package com.starorigins.crypstocks.ui.components.charts.graph.xaxis

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface XAxisDrawer {
    fun calculateHeight(drawScope: DrawScope): Float

    fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        labels: List<String>
    )
}
