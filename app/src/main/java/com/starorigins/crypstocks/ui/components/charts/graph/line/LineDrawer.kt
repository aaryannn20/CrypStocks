package com.starorigins.crypstocks.ui.components.charts.graph.line

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

interface LineDrawer {
    fun drawLine(drawScope: DrawScope, color: Color, linePath: Path)
}
