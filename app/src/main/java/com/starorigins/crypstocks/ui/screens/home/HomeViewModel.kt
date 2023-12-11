package com.starorigins.crypstocks.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starorigins.crypstocks.data.model.Price
import com.starorigins.crypstocks.data.model.Quote
import com.starorigins.crypstocks.data.model.Symbol
import com.starorigins.crypstocks.data.repositories.stocks.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stocksRepository: StocksRepository
) : ViewModel() {

    // private var getUserTrackedSymbolsJob: Job? = null
    // private val _trackedSymbolsUIState = MutableStateFlow<TrackedSymbolsUIState>(TrackedSymbolsUIState.Loading)
    // val trackedSymbolsUIState: StateFlow<TrackedSymbolsUIState> = _trackedSymbolsUIState

    private var getActiveSymbolsJob: Job? = null
    private val _activeSymbolsUIState = MutableStateFlow<ActiveSymbolsUIState>(ActiveSymbolsUIState.Loading)
    val activeSymbolsUIState: StateFlow<ActiveSymbolsUIState> = _activeSymbolsUIState

    private val _searchUIState = MutableStateFlow<SearchUIState>(SearchUIState.Empty)
    val searchUIState: StateFlow<SearchUIState> = _searchUIState

    private var getQueriedSymbolsJob: Job? = null

    init {
        // getUserTrackedSymbols()
        getTopActiveQuotes()
    }

    // private fun getUserTrackedSymbols() {
    //     getUserTrackedSymbolsJob?.cancel()
    //     getUserTrackedSymbolsJob = viewModelScope.launch {
    //         stocksRepository.fetchTrackedSymbols(
    //             onStart = { _trackedSymbolsUIState.value = TrackedSymbolsUIState.Loading },
    //             onError = { _trackedSymbolsUIState.value = TrackedSymbolsUIState.Error(it) }
    //         ).collect { chartPrices ->
    //             _trackedSymbolsUIState.value = TrackedSymbolsUIState.Success(chartPrices)
    //         }
    //     }
    // }

    private fun getTopActiveQuotes() {
        getActiveSymbolsJob?.cancel()
        getActiveSymbolsJob = viewModelScope.launch {
            stocksRepository.fetchTopActiveQuotes(
                onStart = { _activeSymbolsUIState.value = ActiveSymbolsUIState.Loading },
                onError = { _activeSymbolsUIState.value = ActiveSymbolsUIState.Error(it) }
            ).collect { quotes ->
                _activeSymbolsUIState.value = ActiveSymbolsUIState.Success(quotes)
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        getQueriedSymbolsJob?.cancel()
        if (newQuery.isBlank()) {
            _searchUIState.value = SearchUIState.Empty
        } else {
            getQueriedSymbolsJob = viewModelScope.launch {
                stocksRepository.fetchSymbols(
                    query = newQuery,
                    limit = 15,
                    onStart = {
                        _searchUIState.value = SearchUIState.Working(
                            results = when (val state = _searchUIState.value){
                                is SearchUIState.Working -> state.results
                                else -> emptyList()
                            },
                            loading = true,
                            query = newQuery
                        )
                    },
                    onError = { _searchUIState.value = SearchUIState.Error(it, _searchUIState.value.query) }
                ).collect { results ->
                    _searchUIState.value = SearchUIState.Working(
                        results = results,
                        loading = false,
                        query = _searchUIState.value.query
                    )
                }
            }
        }
    }
}

// sealed class TrackedSymbolsUIState {
//     object Loading : TrackedSymbolsUIState()
//     data class Success(val chartPrices: List<Pair<Quote, List<Price>>>) : TrackedSymbolsUIState()
//     data class Error(val message: String) : TrackedSymbolsUIState()
// }

sealed class ActiveSymbolsUIState {
    object Loading : ActiveSymbolsUIState()
    data class Success(val quotes: List<Quote>) : ActiveSymbolsUIState()
    data class Error(val message: String) : ActiveSymbolsUIState()
}

sealed class SearchUIState(val query: String) {
    object Empty : SearchUIState("")
    class Working(val results: List<Symbol>, loading: Boolean, query: String) : SearchUIState(query)
    class Error(val message: String, query: String) : SearchUIState(query)
}