package com.starorigins.crypstocks.ui.screens.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starorigins.crypstocks.ui.components.fadingItems
import androidx.navigation.NavController
import com.starorigins.crypstocks.R
import com.starorigins.crypstocks.ui.components.QuoteWithChartCard
import com.starorigins.crypstocks.ui.components.QuoteWithChartCardPlaceholder
import com.starorigins.crypstocks.ui.components.SectionTitle
import com.starorigins.crypstocks.ui.components.charts.LineChartData
import com.starorigins.crypstocks.ui.components.fadingItem
import com.starorigins.crypstocks.ui.screens.Screen
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
){
    WatchlistContent(
        trackedSymbolsUIState = viewModel.trackedSymbolsUIState.collectAsState(),
        modifier = modifier,
        onSymbolSelected = { symbol ->
            navController.navigate(Screen.StockDetail.buildRoute(symbol))
        }
    )
}


@Composable
fun WatchlistContent(
    trackedSymbolsUIState: State<TrackedSymbolsUIState>,
    modifier: Modifier = Modifier,
    onSymbolSelected: (String) -> Unit,
){
    LazyColumn(modifier.fillMaxSize()) {
        item { Spacer(modifier = Modifier.statusBarsPadding()) }
        userTrackedSymbolsSection(trackedSymbolsUIState, onSymbolSelected)
    }
}

private fun LazyListScope.userTrackedSymbolsSection(
    trackedSymbolsUIState: State<TrackedSymbolsUIState>,
    onSymbolSelected: (String) -> Unit,
) {
    item { SectionTitle(stringResource(R.string.user_symbols_section_title)) }
            when (val state = trackedSymbolsUIState.value) {
                is TrackedSymbolsUIState.Loading -> fadingItems(5, delayDurationMillis = 500) { _, alphaModifier ->
                    QuoteWithChartCardPlaceholder(alphaModifier)
                }

                is TrackedSymbolsUIState.Error -> item { Text(state.message) }
                is TrackedSymbolsUIState.Success -> {
                    if (state.chartPrices.isEmpty()) {
                        fadingItem { alphaModifier -> EmptyUserTrackedSymbols(alphaModifier) }
                    } else {
                        items(
                            items = state.chartPrices,
                            key = { it.first.symbol }
                        ) { quoteAndChartPrices ->
                            QuoteWithChartCard(
                                quote = quoteAndChartPrices.first,
                                chartData = LineChartData(quoteAndChartPrices.second.map {
                                    LineChartData.Point(it.closePrice.toFloat(), it.date.toString())
                                }),
                                onSymbolSelected = onSymbolSelected
                            )
                        }
                    }
                }
            }
}

@Preview
@Composable
private fun EmptyUserTrackedSymbols(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.padding(top = 16.dp, bottom = 16.dp, start = 24.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_tracked),
            contentDescription = stringResource(id = R.string.track),
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.user_symbols_section_suggestion),
            style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)
        )
    }
}