package com.starorigins.crypstocks.ui.components.charts.graph.path

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import com.starorigins.crypstocks.ui.components.charts.LineChartData
import com.starorigins.crypstocks.ui.components.charts.LineChartUtils

class BezierLinePathCalculator : LinePathCalculator {
    private var previousDrawableArea: Rect? = null
    private var previousLineChartData: LineChartData? = null

    private var path = Path()
    private val pathMeasure = PathMeasure()

    override fun calculateLinePath(
        drawableArea: Rect,
        data: LineChartData,
        lineLength: Float
    ): Path {
        if ((data != previousLineChartData || drawableArea != previousDrawableArea) &&
            data.points.isNotEmpty()
        ) {
            // Only recalculate the path it it has actually changed
            path = Path().apply {
                val firstLocation = LineChartUtils.calculatePointLocation(
                    drawableArea = drawableArea,
                    lineChartData = data,
                    point = data.points.first(),
                    index = 0
                )
                moveTo(firstLocation.x, firstLocation.y)

                data.points.drop(1).foldIndexed(firstLocation) { index, previousLoc, current ->
                    val currentLoc = LineChartUtils.calculatePointLocation(
                        drawableArea = drawableArea,
                        lineChartData = data,
                        point = current,
                        index = index + 1
                    )
                    val controlPointX = (currentLoc.x - previousLoc.x) / 1.3f
                    cubicTo(
                        previousLoc.x + controlPointX, previousLoc.y,
                        currentLoc.x - controlPointX, currentLoc.y,
                        currentLoc.x, currentLoc.y
                    )
                    currentLoc
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
