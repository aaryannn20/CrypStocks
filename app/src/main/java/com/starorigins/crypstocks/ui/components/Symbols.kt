package com.starorigins.crypstocks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.starorigins.crypstocks.data.model.Symbol
import com.starorigins.crypstocks.data.model.network.SymbolType
import com.starorigins.crypstocks.data.repositories.stocks.buildLogoURL
import com.starorigins.crypstocks.ui.theme.CrypStocksTheme
import java.time.Instant
import java.time.LocalDate

@Composable
fun SymbolListItem(
    symbol: Symbol,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f, true)
        ) {
            Text(
                text = symbol.symbol,
                style = MaterialTheme.typography.h5
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${stringResource(symbol.type.uiStringResource)} - ${symbol.region}",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Image(
            painter = rememberImagePainter(
                data = buildLogoURL(symbol.symbol),
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "${symbol.symbol} logo",
            modifier = Modifier
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

@Preview
@Composable
fun SymbolListItemsPreview() {
    val symbols = List(5) {
        generatePreviewSymbol()
    }
    CrypStocksTheme {
        Surface {
            LazyColumn {
                items(items = symbols) {
                    SymbolListItem(
                        symbol = it,
                        onClick = {}
                    )
                }
            }
        }
    }
}

private fun generatePreviewSymbol() = Symbol(
    symbol = "AMD",
    creationDate = LocalDate.now(),
    type = SymbolType.CommonStock,
    region = "USD",
    currency = "$",
    userTracked = false,
    fetchTimestamp = Instant.now()
)