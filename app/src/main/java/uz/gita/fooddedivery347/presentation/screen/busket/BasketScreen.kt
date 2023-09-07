package uz.gita.fooddedivery347.presentation.screen.busket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.presentation.components.OrderFoodComp
import uz.gita.fooddedivery347.ui.theme.LightGrayColor
import uz.gita.fooddedivery347.utils.navigation.MyScreen

class BasketScreen : Tab, MyScreen() {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Orders"
            val icon = painterResource(id = R.drawable.ic_buy)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: BasketContract.ViewModel = getViewModel<BasketScreenViewModel>()
        val uiState = viewModel.uiState.collectAsState().value

        BasketScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreenContent(
    uiState: BasketContract.UIState,
    onEventDispatcher: (BasketContract.Intent) -> Unit
) {
    onEventDispatcher.invoke(BasketContract.Intent.Loading)
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (uiState.foods.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.empty_box), contentDescription = "")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
            ) {
                itemsIndexed(
                    items = uiState.foods,
                    key = { _, item -> item.hashCode() }
                ) { _, foodEntity ->
                    if (foodEntity.count != 0) {
                        OrderFoodComp(
                            item = foodEntity,
                            listenerP = {
                                onEventDispatcher.invoke(BasketContract.Intent.Add(foodEntity.id))
                            },
                            listenerM = {
                                onEventDispatcher.invoke(BasketContract.Intent.Remove(foodEntity.id))
                            }
                        ){
                            for(i in 0 until  foodEntity.count){
                                onEventDispatcher.invoke(BasketContract.Intent.Remove(foodEntity.id))
                            }
                        }
                    }
                }
                item {
                    if (uiState.foods.isEmpty()) {
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
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp), contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        onEventDispatcher.invoke(
                            BasketContract.Intent.Comment(
                                comment,
                                getAllPrice(uiState.foods)
                            )
                        )
                    },
                    enabled = uiState.foods.isNotEmpty()
                ) {
                    Text(text = "Rasmiylashtirish")
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
