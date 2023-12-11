package com.starorigins.crypstocks.ui.components.charts

data class LineChartData(
    val points: List<Point> = listOf(Point(0f, ""), Point(0f, "")),
    val bottomPaddingRatio: Float = 0.2f,
    val topPaddingRatio: Float = 0.2f,
) {
    init {
        require(topPaddingRatio in 0f..1f) {
            "Vertical padding ratio has to be between 0 and 1"
        }

        require(bottomPaddingRatio in 0f..1f) {
            "Bottom padding ratio has to be between 0 and 1"
        }
    }

    private val minPoint
        get() = points.minByOrNull { it.value }?.value ?: 0f

    private val maxPoint
        get() = points.maxByOrNull { it.value }?.value ?: 0f

    internal val maxYValue = maxPoint + ((maxPoint - minPoint) * topPaddingRatio)
    internal val minYValue = minPoint - ((maxPoint - minPoint) * bottomPaddingRatio)

    data class Point(val value: Float, val label: String)
}
