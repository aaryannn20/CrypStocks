package com.starorigins.crypstocks.ui.components.charts.graph.path

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import com.starorigins.crypstocks.ui.components.charts.LineChartData

interface LinePathCalculator {
    fun calculateLinePath(
        drawableArea: Rect,
        data: LineChartData,
        lineLength: Float,
    ): Path
}
