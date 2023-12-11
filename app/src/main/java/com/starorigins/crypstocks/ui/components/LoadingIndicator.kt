package com.starorigins.crypstocks.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.delay

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val alpha = remember { Animatable(0f) }
        LaunchedEffect(alpha) {
            delay(500)
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 350, easing = LinearOutSlowInEasing)
            )
        }

        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary, modifier = Modifier.alpha(alpha.value))
    }
}
