package uz.gita.fooddedivery347.presentation.screen.home.page2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.presentation.components.CheckComponent
import uz.gita.fooddedivery347.presentation.components.OrderFoodComp
import uz.gita.fooddedivery347.presentation.components.SwipeButton
import uz.gita.fooddedivery347.presentation.screen.busket.BasketContract
import uz.gita.fooddedivery347.presentation.screen.home.HomeScreen
import uz.gita.fooddedivery347.ui.theme.LightGrayColor
import uz.gita.fooddedivery347.utils.navigation.MyScreen

class Page2Tab : MyScreen(), Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Categories"
            val icon = painterResource(id = R.drawable.ic_buy)
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
        val parentScreen = LocalNavigator.current?.parent?.lastItem // HomeScreen
        if (parentScreen !is HomeScreen) return
        val viewModel = parentScreen.getViewModel<Page2ViewModel>()
        val uiState = viewModel.collectAsState()

        Page2ScreenContent(uiState = uiState, onEventDispatcher = viewModel::onEventDispatcher)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Page2ScreenContent(
        uiState: State<Page2Contract.UIState>,
        onEventDispatcher: (Page2Contract.Intent) -> Unit
    ) {
        var comment by remember { mutableStateOf("") }
        var (isComplete, setIsComplete) = remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrayColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState.value) {
                Page2Contract.UIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Page2Contract.UIState.OrderedList -> {
                    if ((uiState.value as Page2Contract.UIState.OrderedList).foods.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.empty_box),
                                contentDescription = ""
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            itemsIndexed(
                                items = (uiState.value as Page2Contract.UIState.OrderedList).foods,
                                key = { _, item -> item.hashCode() }
                            ) { _, foodEntity ->
                                if (foodEntity.count != 0) {
                                    OrderFoodComp(
                                        item = foodEntity,
                                        listenerP = {
                                            onEventDispatcher.invoke(
                                                Page2Contract.Intent.Add(
                                                    foodEntity.id
                                                )
                                            )
                                        },
                                        listenerM = {
                                            onEventDispatcher.invoke(
                                                Page2Contract.Intent.Remove(
                                                    foodEntity.id
                                                )
                                            )
                                        }
                                    ) {
                                        for (i in 0 until foodEntity.count) {
                                            onEventDispatcher.invoke(
                                                Page2Contract.Intent.Remove(
                                                    foodEntity.id
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                            item {
                                if ((uiState.value as Page2Contract.UIState.OrderedList).foods.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {

                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .fillMaxWidth()
                                            .height(50.dp)
                                            .padding(horizontal = 10.dp)
                                            .clip(RoundedCornerShape(8.dp))


                                    ) {

                                        TextField(
                                            modifier = Modifier.fillMaxSize(),
                                            value = comment,
                                            onValueChange = { newValue -> comment = newValue },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color.Black,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent,
                                                cursorColor = Color(0xFF050505)
                                            ),
                                            placeholder = {
                                                Text(
                                                    text = "Izoh...",
                                                    color = Color.Gray
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                            item {
                                CheckComponent(
                                    amunt = (uiState.value as Page2Contract.UIState.OrderedList).sum,
                                    deliveryAmount = 10000
                                )
                                SwipeButton(text = "TO'LOV QILISH", isComplete = isComplete) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        onEventDispatcher.invoke(
                                            Page2Contract.Intent.Comment(
                                                comment,
                                                getAllPrice((uiState.value as Page2Contract.UIState.OrderedList).foods) + 10000
                                            )
                                        )
                                        delay(3000)
                                        setIsComplete.invoke(true)
                                        delay(1000)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAllPrice(list: List<FoodEntity>): Long {
        var p = 0L
        list.forEach {
            p += it.cost * it.count
        }
        return p
    }

}