package com.starorigins.crypstocks.ui.screens.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starorigins.crypstocks.data.model.Price
import com.starorigins.crypstocks.data.model.Quote
import com.starorigins.crypstocks.data.repositories.stocks.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val stocksRepository: StocksRepository
): ViewModel(){

    private var getUserTrackedSymbolsJob: Job? = null
    private val _trackedSymbolsUIState = MutableStateFlow<TrackedSymbolsUIState>(TrackedSymbolsUIState.Loading)
    val trackedSymbolsUIState: StateFlow<TrackedSymbolsUIState> = _trackedSymbolsUIState

    init {
            getUserTrackedSymbols()
    }

    private fun getUserTrackedSymbols() {
        getUserTrackedSymbolsJob?.cancel()
        getUserTrackedSymbolsJob = viewModelScope.launch {
            stocksRepository.fetchTrackedSymbols(
                onStart = { _trackedSymbolsUIState.value = TrackedSymbolsUIState.Loading },
                onError = { _trackedSymbolsUIState.value = TrackedSymbolsUIState.Error(it) }
            ).collect { chartPrices ->
                _trackedSymbolsUIState.value = TrackedSymbolsUIState.Success(chartPrices)
            }
        }
    }

}

sealed class TrackedSymbolsUIState {
    object Loading : TrackedSymbolsUIState()
    data class Success(val chartPrices: List<Pair<Quote, List<Price>>>) : TrackedSymbolsUIState()
    data class Error(val message: String) : TrackedSymbolsUIState()
}
