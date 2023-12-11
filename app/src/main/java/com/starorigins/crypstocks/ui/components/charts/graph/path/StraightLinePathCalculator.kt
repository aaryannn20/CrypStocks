package com.starorigins.crypstocks.ui.components.charts.graph.path

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import com.starorigins.crypstocks.ui.components.charts.LineChartData
import com.starorigins.crypstocks.ui.components.charts.LineChartUtils.calculatePointLocation

class StraightLinePathCalculator : LinePathCalculator {
    private var previousDrawableArea: Rect? = null
    private var previousLineChartData: LineChartData? = null

    private var path = Path()
    private val pathMeasure = PathMeasure()

    override fun calculateLinePath(
        drawableArea: Rect,
        data: LineChartData,
        lineLength: Float
    ): Path {
        if (data != previousLineChartData || drawableArea != previousDrawableArea) {
            // Only recalculate the path it it has actually changed
            path = Path().apply {
                data.points.forEachIndexed { index, point ->
                    val pointLocation = calculatePointLocation(
                        drawableArea = drawableArea,
                        lineChartData = data,
                        point = point,
                        index = index
                    )
                    if (index == 0) {
                        moveTo(pointLocation.x, pointLocation.y)
                    } else {
                        lineTo(pointLocation.x, pointLocation.y)
                    }
                }
            }
            pathMeasure.setPath(path, false)
            previousLineChartData = data
            previousDrawableArea = drawableArea
        }

        return if (lineLength < 1f) {
            // Animation in progress, calculate the sub section
            Path().apply {
                pathMeasure.getSegment(
                    0f,
                    pathMeasure.length * lineLength,
                    this
                )
            }
        } else {
            path
        }
    }
}
