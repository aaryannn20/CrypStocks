package com.starorigins.crypstocks.data.repositories.utils

import com.starorigins.crypstocks.data.model.network.QuoteResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class BatchedQuotes

class BatchedQuotesAdapter {
    companion object {
        val adapter: JsonAdapter<QuoteResponse> = Moshi.Builder()
            .add(InstantAdapter())
            .build()
            .adapter(QuoteResponse::class.java)
    }

    @BatchedQuotes
    @FromJson
    fun fromJson(reader: JsonReader): List<QuoteResponse> {
        val quotes = mutableListOf<QuoteResponse>()
        reader.beginObject()
        while (reader.hasNext()) {
            reader.skipName()
            reader.beginObject()
            reader.skipName()
            adapter.fromJson(reader)?.let {
                quotes.add(it)
            }
            reader.endObject()
        }
        reader.endObject()
        return quotes
    }

    @ToJson
    fun toJson(@BatchedQuotes value: List<QuoteResponse>): String {
        throw UnsupportedOperationException()
    }
}
