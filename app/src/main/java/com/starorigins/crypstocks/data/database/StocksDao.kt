package com.starorigins.crypstocks.data.database

import androidx.compose.ui.util.fastFirstOrNull
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.starorigins.crypstocks.data.model.CompanyInfo
import com.starorigins.crypstocks.data.model.News
import com.starorigins.crypstocks.data.model.Price
import com.starorigins.crypstocks.data.model.Quote
import com.starorigins.crypstocks.data.model.Symbol
import com.starorigins.crypstocks.data.model.network.SymbolType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.time.Instant
import java.time.LocalDate

@Dao
interface StocksDao {

    // SYMBOLS

    @Query("SELECT * FROM symbols WHERE symbol LIKE '%' ||:query ||'%' ORDER BY LENGTH(symbol) ASC LIMIT :limit")
    suspend fun getSymbolsByQuery(query: String, limit: Int): List<Symbol>

    @Transaction
    suspend fun refreshSymbols(newSymbols: List<Symbol>) {
        val tracked = getTrackedSymbols().first()
        val newWithTracked = newSymbols.map { newSymbol ->
            tracked.fastFirstOrNull { it.symbol == newSymbol.symbol } ?: newSymbol
        }

        deleteSymbols()
        insertSymbols(newWithTracked)
    }

    @Query("DELETE FROM symbols")
    suspend fun deleteSymbols()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymbols(symbols: List<Symbol>)

    @Query("SELECT * FROM symbols WHERE userTracked = 1")
    fun getTrackedSymbols(): Flow<List<Symbol>>

    @Query("SELECT IFNULL(MIN(userTracked), 0) FROM symbols WHERE symbol = :symbol")
    fun symbolIsTracked(symbol: String): Flow<Boolean>

    @Transaction
    suspend fun safeUpdateIsTracked(symbol: String, isTracked: Boolean) {
        if (!symbolExists(symbol)) {
            // safeguard in case the symbols table hasn't been populated
            insertSymbols(
                listOf(
                    Symbol(
                        symbol = symbol,
                        creationDate = LocalDate.now(),
                        type = SymbolType.Unknown,
                        region = "US",
                        currency = "USD",
                        userTracked = isTracked,
                        fetchTimestamp = Instant.now()
                    )
                )
            )
        } else {
            updateIsTracked(symbol, isTracked)
        }
    }

    @Query("UPDATE symbols SET userTracked = :isTracked WHERE symbol = :symbol")
    suspend fun updateIsTracked(symbol: String, isTracked: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM symbols WHERE symbol = :symbol LIMIT 1)")
    fun symbolExists(symbol: String): Boolean

    // QUOTES

    @Transaction
    suspend fun refreshTopActiveQuotes(topActiveQuotes: List<Quote>) {
        removeTopActiveFromQuotes()
        insertQuotes(topActiveQuotes)
    }

    @Query("UPDATE quotes SET isTopActive = 0")
    fun removeTopActiveFromQuotes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quotes WHERE symbol IN (:symbols) AND fetchTimestamp >= :timestampCutoff")
    fun getQuotes(symbols: List<String>, timestampCutoff: Instant): Flow<List<Quote>>

    @Query("SELECT * FROM quotes WHERE isTopActive = :isTopActive AND fetchTimestamp >= :timestampCutoff")
    fun getQuotesByActivity(isTopActive: Boolean, timestampCutoff: Instant): Flow<List<Quote>>

    // CHARTS

    @Query("SELECT * FROM prices WHERE symbol = :symbol AND date BETWEEN :firstDate AND :lastDate ORDER BY date")
    fun getChartPrices(symbol: String, firstDate: LocalDate, lastDate: LocalDate): Flow<List<Price>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChartPrices(prices: List<Price>)

    // NEWS

    @Transaction
    suspend fun refreshNews(updatedNews: List<News>) {
        deleteNews()
        insertNews(updatedNews)
    }

    @Query("DELETE FROM news")
    fun deleteNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    @Query("SELECT * FROM news WHERE fetchTimestamp >= :timestampCutoff")
    fun getNews(timestampCutoff: Instant): Flow<List<News>>

    // COMPANY INFO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyInfo(companyInfo: CompanyInfo)

    @Query("SELECT * FROM company_infos WHERE symbol = :symbol AND fetchTimestamp >= :timestampCutoff")
    fun getCompanyInfo(symbol: String, timestampCutoff: Instant): Flow<CompanyInfo?>
}
