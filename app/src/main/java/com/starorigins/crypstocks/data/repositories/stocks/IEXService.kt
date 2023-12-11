package com.starorigins.crypstocks.data.repositories.stocks

import com.starorigins.crypstocks.data.model.network.CompanyInfoResponse
import com.starorigins.crypstocks.data.model.network.NewsResponse
import com.starorigins.crypstocks.data.model.network.PriceResponse
import com.starorigins.crypstocks.data.model.network.QuoteResponse
import com.starorigins.crypstocks.data.model.network.SymbolResponse
import com.starorigins.crypstocks.data.model.network.SymbolType
import com.starorigins.crypstocks.data.repositories.utils.BatchedNews
import com.starorigins.crypstocks.data.repositories.utils.BatchedNewsAdapter
import com.starorigins.crypstocks.data.repositories.utils.BatchedQuotes
import com.starorigins.crypstocks.data.repositories.utils.BatchedQuotesAdapter
import com.starorigins.crypstocks.data.repositories.utils.HttpRequestInterceptor
import com.starorigins.crypstocks.data.repositories.utils.InstantAdapter
import com.starorigins.crypstocks.data.repositories.utils.LocalDateAdapter
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IEXService {

    @GET("ref-data/symbols")
    suspend fun fetchSymbols(): ApiResponse<List<SymbolResponse>>

    @GET("stock/market/list/mostactive")
    suspend fun fetchMostActiveSymbols(
        @Query("listLimit") numberReturned: Int
    ): ApiResponse<List<QuoteResponse>>

    @GET("stock/market/batch?types=quote")
    @BatchedQuotes
    suspend fun fetchQuotes(
        @Query("symbols") symbols: String
    ): ApiResponse<List<QuoteResponse>>

    @GET("stock/{symbol}/chart/{range}")
    suspend fun fetchChartPrices(
        @Path("symbol") symbol: String,
        @Path("range") range: ChartRange,
        @Query("includeToday") includeToday: Boolean = false,
        @Query("chartCloseOnly") closeOnly: Boolean = true
    ): ApiResponse<List<PriceResponse>>

    @GET("stock/market/batch?types=news")
    @BatchedNews
    suspend fun fetchNews(
        @Query("symbols") symbols: String,
        @Query("last") numberPerSymbol: Int = 2
    ): ApiResponse<List<NewsResponse>>

    @GET("stock/{symbol}/company")
    suspend fun fetchCompanyInfo(
        @Path("symbol") symbol: String
    ): ApiResponse<CompanyInfoResponse>

    companion object {
        private const val BASE_URL = "https://cloud.iexapis.com/stable/"
        // private const val BASE_URL = "https://sandbox.iexapis.com/stable/"

        fun create(): IEXService {
            return Retrofit.Builder()
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            Interceptor { chain ->
                                // Add the API token to the request
                                val originalRequest = chain.request()
                                val urlWithToken = originalRequest.url.newBuilder()
                                    .addQueryParameter("token", "pk_2c23e8a7802041ca83b33d34af37ba76")
                                    .build()
                                val requestWithToken = originalRequest.newBuilder()
                                    .url(urlWithToken)
                                    .build()
                                chain.proceed(requestWithToken)
                            }
                        )
                        .addInterceptor(HttpRequestInterceptor())
                        .build()
                )
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .add(
                                SymbolType::class.java,
                                EnumJsonAdapter.create(SymbolType::class.java).withUnknownFallback(
                                    SymbolType.Unknown)
                            )
                            .add(BatchedQuotesAdapter())
                            .add(BatchedNewsAdapter())
                            .add(LocalDateAdapter())
                            .add(InstantAdapter())
                            .build()
                    )
                )
                .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
                .build()
                .create(IEXService::class.java)
        }
    }
}

fun buildLogoURL(symbol: String) = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/$symbol.png"