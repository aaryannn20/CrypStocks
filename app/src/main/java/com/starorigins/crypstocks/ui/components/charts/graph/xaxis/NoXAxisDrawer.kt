package com.starorigins.crypstocks.ui.components.charts.graph.xaxis

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

object NoXAxisDrawer : XAxisDrawer {
    override fun calculateHeight(drawScope: DrawScope) = 0f

    override fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        labels: List<String>
    ) {
        // Leave empty on purpose, we do not want to draw anything.
    }
}
