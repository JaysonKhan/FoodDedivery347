package uz.gita.fooddedivery347.presentation.screen.home.page1

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.flow.distinctUntilChanged
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.presentation.components.CustomSearchView
import uz.gita.fooddedivery347.presentation.components.FoodComponent
import uz.gita.fooddedivery347.presentation.screen.home.HomeScreen
import uz.gita.fooddedivery347.ui.theme.MyColor1
import uz.gita.fooddedivery347.ui.theme.MyColor4

class Page1Tab(val listener: () -> Unit) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource(R.drawable.ic_home)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val parentScreen = LocalNavigator.current?.parent?.lastItem
        if (parentScreen !is HomeScreen) return
        val viewModel = parentScreen.getViewModel<Page1ViewModel>()
        val uiState = viewModel.collectAsState().value
        val firstListState = rememberLazyListState()
        val secondListState = rememberLazyListState()
        val index = remember { mutableStateOf(0) }

        var search by remember { mutableStateOf("") }
        Surface {

                when (uiState) {
                    Page1Contract.UiState.Loading -> {
                        Shimmer()
                        viewModel.onEventDispatcher(Page1Contract.Intent.Loading)
                    }

                    Page1Contract.UiState.Error -> {
                        Text(text = "ERROR", textAlign = TextAlign.Center, fontSize = 34.sp)
                    }

                    is Page1Contract.UiState.FullList -> {
                        Column(
                            modifier = Modifier
                                .background(MyColor1)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CustomSearchView(
                                    modifier = Modifier
                                        .weight(1f),
                                    search = search
                                ) {
                                    search = it
                                    viewModel.onEventDispatcher(Page1Contract.Intent.Search((search)))
                                }
                            }
                            LazyRow(
                                state = firstListState
                            ) {
                                items(uiState.list) { type ->
                                    TextButton(
                                        onClick = {
                                            index.value = uiState.list.indexOf(type)
                                        },
                                        modifier = Modifier
                                            .padding(8.dp, 4.dp)
                                            .border(1.dp, MyColor4, RoundedCornerShape(5.dp)),
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Text(text = type.typeData.type)
                                    }
                                }
                            }
                            LazyColumn(state = secondListState) {
                                itemsIndexed(
                                    items = uiState.list,
                                    key = { _, item -> item.hashCode() }
                                ) { index, category ->
                                    Text(
                                        text = category.typeData.type,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp, horizontal = 18.dp),
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (category.foods.isEmpty()) {
                                        LittleShimmer()
                                        viewModel.onEventDispatcher(Page1Contract.Intent.Loading)
                                    } else {
                                        LazyRow {
                                            itemsIndexed(
                                                items = category.foods,
                                                key = { _, item -> item.hashCode() }
                                            ) { _, food ->
                                                FoodComponent(
                                                    food = food,
                                                    modifier = Modifier
                                                        .width(175.dp)
                                                        .wrapContentHeight()
                                                        .padding(8.dp),
                                                    searchText = search
                                                ) {
                                                    viewModel.onEventDispatcher(
                                                        Page1Contract.Intent.Add(
                                                            food.id
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                item {
                                    Box(modifier = Modifier.height(50.dp))

                                }
                            }
                        }
                        if (uiState.sum > 0) {
                            Log.d("TTT", uiState.sum.toString())
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter,
                            ) {
                                BottomSheet(
                                    uiState.sum
                                ) {
                                    listener.invoke()
                                }
                            }
                        }
                        LaunchedEffect(index.value) {
                            secondListState.animateScrollToItem(index = index.value)
                        }
                        LaunchedEffect(secondListState) {
                            snapshotFlow { secondListState.firstVisibleItemIndex }
                                .distinctUntilChanged()
                                .collect { index ->
                                    firstListState.animateScrollToItem(index)
                                }
                        }

                    }

                }
            }
        }
    }

    @Composable
    fun BottomSheet(
        sum: Long = 0L,
        listenerOrderButton: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Yellow), contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = "25 - 30 min",
                )

                Button(modifier = Modifier.weight(1f), onClick = {
                    listenerOrderButton.invoke()
                }) {
                    Text(text = "Order")
                }

                Text(
                    modifier = Modifier.weight(1f),
                    text = "$sum sum",
                    textAlign = TextAlign.Center
                )
            }
        }
}

@Preview
@Composable
fun Shimmer() {
    Surface {
        val shim = shimmerBrush(targetValue = 1300f, showShimmer = true)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
                    .background(shim)
            )
            for (i in 0 until 4) {
                Box(
                    Modifier
                        .background(shim)
                        .padding(8.dp, 4.dp)
                        .fillMaxWidth()
                        .height(32.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .height(160.dp)
                            .background(shim)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .height(160.dp)
                            .background(shim)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LittleShimmer() {
    val shim = shimmerBrush(targetValue = 1300f, showShimmer = true)
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until 3) {
            Card(
                modifier = Modifier
                    .width(175.dp)
                    .padding(horizontal = 8.dp)
                    .height(200.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp)
                        .background(shim)
                )
            }
        }
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition()
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            )
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}


