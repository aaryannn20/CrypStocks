package com.starorigins.crypstocks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Instant,
    val headline: String,
    val source: String,
    val url: String,
    val summary: String,
    val symbols: List<String>,
    val imageUrl: String,

    val fetchTimestamp: Instant
)
