package com.starorigins.crypstocks.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.starorigins.crypstocks.data.model.CompanyInfo
import com.starorigins.crypstocks.data.model.News
import com.starorigins.crypstocks.data.model.Price
import com.starorigins.crypstocks.data.model.Quote
import com.starorigins.crypstocks.data.model.Symbol
import com.starorigins.crypstocks.data.workmanager.UpdateSymbolsWorker
import java.util.concurrent.TimeUnit

@Database(
    entities = [CompanyInfo::class, Price::class, Quote::class, News::class, Symbol::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stocksDao(): StocksDao

    companion object {
        private const val DATABASE_NAME = "stocksApp.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = PeriodicWorkRequestBuilder<UpdateSymbolsWorker>(1, TimeUnit.DAYS)
                                .setConstraints(
                                    Constraints.Builder()
                                        .setRequiredNetworkType(NetworkType.CONNECTED)
                                        .build()
                                )
                                .addTag("updateSymbols")
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
    }
}
