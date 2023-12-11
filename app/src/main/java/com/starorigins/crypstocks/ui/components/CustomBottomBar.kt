package com.starorigins.crypstocks.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.starorigins.crypstocks.ui.screens.RootDestination
import com.starorigins.crypstocks.ui.screens.Screen
import com.starorigins.crypstocks.ui.theme.CrypStocksTheme
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch

@Composable
fun CustomBottomBar(
    currentDestination: RootDestination,
    onDestinationSelected: (RootDestination) -> Unit,
    destinations: List<RootDestination>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    indicatorColor: Color = MaterialTheme.colors.secondary,
    unselectedColor: Color = contentColorFor(backgroundColor),
    selectedColor: Color = contentColorFor(indicatorColor)
) {
    Surface(
        color = backgroundColor,
        elevation = 8.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        val springSpec = remember { SpringSpec<Float>(stiffness = 300f) }
        Box {
            CustomBottomBarLayout(
                selectedIndex = destinations.indexOf(currentDestination),
                itemCount = destinations.size,
                animSpec = springSpec,
                modifier = Modifier
                    .align(Alignment.Center)
                    .navigationBarsPadding(start = false, end = false)
                    .fillMaxWidth(0.88f)
            ) {
                val colorSpec = with(springSpec) { SpringSpec<Color>(dampingRatio, stiffness) }

                destinations.forEach { section ->
                    val selected = section == currentDestination
                    val tint by animateColorAsState(
                        targetValue = if (selected) selectedColor else unselectedColor,
                        animationSpec = colorSpec
                    )
                    CustomBottomBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(section.icon),
                                tint = tint,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(section.title),
                                color = tint,
                                style = MaterialTheme.typography.button,
                                maxLines = 1
                            )
                        },
                        selected = selected,
                        onSelected = { onDestinationSelected(section) },
                        animSpec = springSpec
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomBottomBarLayout(
    selectedIndex: Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Initialize the fractions that track how "selected" each item is [0, 1] at any point
    // TODO: can this be done with animateFloatAsState()?
    val selectionFractions = remember(itemCount) {
        List(itemCount) { i ->
            Animatable(if (i == selectedIndex) 1f else 0f)
        }
    }
    // When selection changes, animate from the current selection fraction towards the target continuously
    val scope = rememberCoroutineScope()
    selectionFractions.forEachIndexed { index, selectionFraction ->
        val target = if (index == selectedIndex) 1f else 0f
        if (selectionFraction.targetValue != target) {
            scope.launch {
                selectionFraction.animateTo(target, animSpec)
            }
        }
    }

    Layout(
        modifier = modifier.height(BottomNavHeight),
        content = { content() }
    ) { measurables, constraints ->
        // Make sure that [itemCount] actually matches the number of composables on [content]
        check(itemCount == measurables.size)

        // Divide the width into n+1 slots and give the selected item 2 slots
        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = 2 * unselectedWidth

        val itemPlaceables = measurables
            .mapIndexed { index, measurable ->
                // Animate item's width based upon the selection amount
                val width = lerp(unselectedWidth, selectedWidth, selectionFractions[index].value)
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            // Place the items
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.place(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}

@Composable
private fun CustomBottomBarItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.secondary,
    shape: Shape = IndicatorShape,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>
) {
    // Animate the icon/text positions within the item based on selection
    val animationProgress by animateFloatAsState(if (selected) 1f else 0f, animSpec)
    Box(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onSelected,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .fillMaxSize()
            .then(ItemPadding)
            .clip(shape)
            .background(color.copy(alpha = animationProgress)),
        contentAlignment = Alignment.Center
    ) {
        CustomBottomBarItemLayout(
            icon = icon,
            text = text,
            animationProgress = animationProgress
        )
    }
}

@Composable
private fun CustomBottomBarItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(
        content = {
            Box(Modifier.layoutId("icon"), content = icon)
            // Alpha curve on the 0.5f to 1f of the animation progress
            val alphaValue = (animationProgress * 2f - 1f).coerceAtLeast(0f)
            Box(
                modifier = Modifier
                    .layoutId("text")
                    .padding(start = TextIconSpacing)
                    .graphicsLayer {
                        alpha = alphaValue
                        transformOrigin = BottomNavLabelTransformOrigin
                    },
                content = text
            )
        }
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        placeTextAndIcon(
            textPlaceable,
            iconPlaceable,
            constraints.maxWidth,
            constraints.maxHeight,
            animationProgress
        )
    }
}

private fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2

    val textWidth = textPlaceable.width * animationProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width

    return layout(width, height) {
        iconPlaceable.place(iconX.toInt(), iconY)
        // Don't place the text if it's not selected
        if (animationProgress != 0f) {
            textPlaceable.place(textX.toInt(), textY)
        }
    }
}

private val TextIconSpacing = 4.dp
private val BottomNavHeight = 64.dp
private val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
private val IndicatorShape = RoundedCornerShape(percent = 50)
private val ItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)

@Preview
@Composable
private fun CustomBottomBarPreview() {
    CrypStocksTheme {
        CustomBottomBar(
            currentDestination = previewDestinations.first(),
            onDestinationSelected = { },
            destinations = previewDestinations
        )
    }
}

@Preview
@Composable
private fun CustomBottomBarPreviewDark() {
    CrypStocksTheme(darkTheme = true) {
        CustomBottomBar(
            currentDestination = previewDestinations.first(),
            onDestinationSelected = { },
            destinations = previewDestinations
        )
    }
}

private val previewDestinations = Screen.listRootDestinations()