package com.starorigins.crypstocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.starorigins.crypstocks.data.datastore.SettingsDataStore
import com.starorigins.crypstocks.ui.CrypStocksApp
import com.starorigins.crypstocks.ui.screens.stockdetail.StockDetailViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settings: SettingsDataStore

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun stockDetailViewModelFactory(): StockDetailViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manually handle insets
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CrypStocksApp(settings)
        }
    }
}
