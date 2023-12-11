package com.starorigins.crypstocks.ui.screens.news

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.starorigins.crypstocks.R
import com.starorigins.crypstocks.data.model.News
import com.starorigins.crypstocks.data.repositories.stocks.StocksRepository.Companion.MAX_NEWS
import com.starorigins.crypstocks.ui.components.ListItemState
import com.starorigins.crypstocks.ui.components.NewsListItem
import com.starorigins.crypstocks.ui.components.NewsListItemPlaceholder
import com.starorigins.crypstocks.ui.components.SectionTitle
import com.starorigins.crypstocks.ui.components.fadingItems
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    NewsContent(
        newsUIState = viewModel.newsUIState.collectAsState(),
        modifier = modifier,
        onReadMoreClicked = { news ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(news.url)))
        }
    )
}

@Composable
fun NewsContent(
    newsUIState: State<NewsUIState>,
    modifier: Modifier = Modifier,
    onReadMoreClicked: (News) -> Unit
) {
    var expandedNewsId by remember { mutableStateOf<Long?>(null) }

    LazyColumn(modifier.fillMaxSize()) {
        item { Spacer(modifier = Modifier.statusBarsPadding()) }
        item { SectionTitle(stringResource(R.string.news_section_title)) }
        when (val state = newsUIState.value) {
            is NewsUIState.Loading -> fadingItems(MAX_NEWS, delayDurationMillis = 500) { _, alphaModifier ->
                NewsListItemPlaceholder(alphaModifier)
            }
            is NewsUIState.Error -> item { Text("ERROR ${state.message}") }
            is NewsUIState.Success -> fadingItems(state.news) { newsItem, alphaModifier ->
                NewsListItem(
                    news = newsItem,
                    itemState = if (expandedNewsId == newsItem.id) {
                        ListItemState.Expanded
                    } else {
                        ListItemState.Collapsed
                    },
                    modifier = alphaModifier,
                    onClick = { expandedNewsId = if (expandedNewsId == newsItem.id) null else newsItem.id },
                    onReadMoreClicked = { onReadMoreClicked(newsItem) }
                )
            }
        }
    }
}