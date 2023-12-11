package com.starorigins.crypstocks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starorigins.crypstocks.data.model.Quote
import com.starorigins.crypstocks.ui.components.charts.LineChart
import com.starorigins.crypstocks.ui.components.charts.LineChartData
import com.starorigins.crypstocks.ui.components.charts.graph.line.SolidLineDrawer
import com.starorigins.crypstocks.ui.components.charts.graph.path.BezierLinePathCalculator
import com.starorigins.crypstocks.ui.components.charts.graph.xaxis.NoXAxisDrawer
import com.starorigins.crypstocks.ui.components.charts.graph.yaxis.NoYAxisDrawer
import com.starorigins.crypstocks.ui.theme.CrypStocksTheme
import com.starorigins.crypstocks.ui.theme.loss
import com.starorigins.crypstocks.ui.theme.profit
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import java.time.Instant
import kotlin.math.sign
import kotlin.random.Random

@Composable
fun QuoteListItem(
    quote: Quote,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f, true)
        ) {
            Text(
                text = quote.symbol,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = quote.companyName,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 32.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "%.2f".format(quote.latestPrice),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.End
            )
            QuoteChange(
                sign = quote.change.sign,
                changePercent = quote.changePercent
            )
        }
    }
}

@Composable
fun QuoteListItemPlaceholder(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                )
        )
    }
}

private val CARD_SIZE = 144.dp

@Composable
fun QuoteWithChartCard(
    quote: Quote,
    chartData: LineChartData,
    modifier: Modifier = Modifier,
    onSymbolSelected: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSymbolSelected(quote.symbol) })
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f, true)
        ) {
            Text(
                text = quote.symbol,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = quote.companyName,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LineChart(
                lineChartData = chartData,
                linePathCalculator = BezierLinePathCalculator(),
                lineDrawer = SolidLineDrawer(),
                xAxisDrawer = NoXAxisDrawer,
                yAxisDrawer = NoYAxisDrawer,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .aspectRatio(1f, matchHeightConstraintsFirst = true)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 32.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "%.2f".format(quote.latestPrice),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.End
                )
                val change = with(chartData.points) { (last().value - first().value) / first().value }.toDouble()
                QuoteChange(
                    sign = change.sign,
                    changePercent = change,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }

@Composable
private fun QuoteChange(
    sign: Double,
    changePercent: Double,
    modifier: Modifier = Modifier
) {
    val changeColor = when (sign) {
        -1.0 -> MaterialTheme.colorScheme.loss
        1.0 -> MaterialTheme.colorScheme.profit
        else -> LocalContentColor.current.copy(alpha = 0.38f)
    }
    val backgroundModifier = modifier.background(
        shape = MaterialTheme.shapes.small,
        color = changeColor.copy(alpha = 0.14f)
    )
    Box(modifier = backgroundModifier.padding(horizontal = 2.dp)) {
        Text(
            text = "${"%+.2f".format(changePercent * 100)}%",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
            textAlign = TextAlign.End,
            color = changeColor
        )
    }
}

@Composable
fun QuoteWithChartCardPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(CARD_SIZE)
            .clip(MaterialTheme.shapes.medium)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
            )
    )
}

@Preview
@Composable
fun QuoteListItemsPreview() {
    val quotes = List(5) {
        generatePreviewQuote()
    }
    CrypStocksTheme {
        Surface {
            LazyColumn {
                items(items = quotes) {
                    QuoteListItem(
                        quote = it,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TickerCardPreview() {
    QuoteWithChartCard(
        quote = generatePreviewQuote(),
        chartData = LineChartData(
            points = (1..10).map { LineChartData.Point(randomYValue(), "#$it") }
        ),
        onSymbolSelected = {}
    )
}

private fun generatePreviewQuote() = Quote(
    symbol = "AMD",
    companyName = "Advanced Micro Devices, Inc",
    primaryExchange = "",
    openPrice = 0.0,
    openTime = Instant.now(),
    closePrice = 0.0,
    closeTime = Instant.now(),
    highPrice = 0.0,
    highTime = Instant.now(),
    lowPrice = 0.0,
    lowTime = Instant.now(),
    latestPrice = 86.77,
    latestSource = "",
    latestTime = Instant.now(),
    latestVolume = 0,
    extendedPrice = 0.0,
    extendedChange = 0.0,
    extendedChangePercent = 0.0,
    extendedPriceTime = Instant.now(),
    previousClose = 0.0,
    previousVolume = 0,
    change = -2.84,
    changePercent = -0.0317,
    volume = 0,
    avgTotalVolume = 0,
    marketCap = 0,
    peRatio = 0.0,
    week52High = 0.0,
    week52Low = 0.0,
    ytdChange = 0.0,
    lastTradeTime = Instant.now(),
    isUSMarketOpen = true,
    isTopActive = false,
    fetchTimestamp = Instant.now()
)

private fun randomYValue() = Random.nextDouble(5.0, 20.0).toFloat()
