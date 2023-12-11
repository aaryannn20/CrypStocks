@file:OptIn(ExperimentalMaterial3Api::class)

package com.starorigins.crypstocks.ui.screens.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.starorigins.crypstocks.R
import com.starorigins.crypstocks.data.repositories.stocks.StocksRepository.Companion.MOST_ACTIVE_COUNT
import com.starorigins.crypstocks.ui.components.QuoteListItem
import com.starorigins.crypstocks.ui.components.QuoteListItemPlaceholder
import com.starorigins.crypstocks.ui.components.SectionTitle
import com.starorigins.crypstocks.ui.components.SymbolListItem
import com.starorigins.crypstocks.ui.components.fadingItems
import com.starorigins.crypstocks.ui.screens.Screen
import com.google.accompanist.insets.statusBarsPadding


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    HomeContent(
        searchUIState = viewModel.searchUIState.collectAsState(),
        onQueryChanged = viewModel::onQueryChanged,
        onSymbolCLicked = { symbol ->
            navController.navigate(Screen.StockDetail.buildRoute(symbol))
        },
        // trackedSymbolsUIState = viewModel.trackedSymbolsUIState.collectAsState(),
        activeSymbolsUIState = viewModel.activeSymbolsUIState.collectAsState(),
        modifier = modifier,
        onSymbolSelected = { symbol ->
            navController.navigate(Screen.StockDetail.buildRoute(symbol))
        }
    )
}

@Composable
fun HomeContent(
    searchUIState: State<SearchUIState>,
    onQueryChanged: (String) -> Unit,
    onSymbolCLicked: (String) -> Unit,
    // trackedSymbolsUIState: State<TrackedSymbolsUIState>,
    activeSymbolsUIState: State<ActiveSymbolsUIState>,
    modifier: Modifier = Modifier,
    onSymbolSelected: (String) -> Unit


) {
    LazyColumn(modifier.fillMaxSize()) {
        item { Spacer(modifier = Modifier.statusBarsPadding()) }
        item { SectionTitle(stringResource(R.string.active_symbols_section_title)) }
        item { SearchField(query = searchUIState.value.query, onQueryChanged = onQueryChanged) }
        when (val state = searchUIState.value) {
            is SearchUIState.Error -> item { Text("ERROR") }
            is SearchUIState.Working -> items(state.results) { symbol ->
                SymbolListItem(
                    symbol = symbol,
                    onClick = { onSymbolCLicked(symbol.symbol) }
                )
            }

            else -> {}
        }
        item { Spacer(modifier = Modifier.statusBarsPadding()) }
        // userTrackedSymbolsSection(trackedSymbolsUIState, onSymbolSelected)
        activeQuotesSection(activeSymbolsUIState, onSymbolSelected)
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        OutlinedTextField(
            value = query,
            singleLine = true,
            label = { Text(stringResource(id = R.string.search_field_label)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.isNotBlank(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier.clickable { onQueryChanged("") }
                    )
                }
            },
            onValueChange = onQueryChanged
        )
    }
}


private fun LazyListScope.activeQuotesSection(
    activeSymbolsUIState: State<ActiveSymbolsUIState>,
    onSymbolSelected: (String) -> Unit,
) {
    // item { SectionTitle(stringResource(R.string.active_symbols_section_title)) }
    when (val state = activeSymbolsUIState.value) {
        is ActiveSymbolsUIState.Loading -> {
            fadingItems(MOST_ACTIVE_COUNT, delayDurationMillis = 500) { _, alphaModifier ->
                QuoteListItemPlaceholder(modifier = alphaModifier)
            }
        }
        is ActiveSymbolsUIState.Error -> item { Text(state.message) }
        is ActiveSymbolsUIState.Success -> {
            fadingItems(
                items = state.quotes,
                key = { it.symbol }
            ) { quote, alphaModifier ->
                QuoteListItem(
                    quote = quote,
                    onClick = { onSymbolSelected(quote.symbol) },
                    modifier = alphaModifier
                )
            }
        }
    }
}

