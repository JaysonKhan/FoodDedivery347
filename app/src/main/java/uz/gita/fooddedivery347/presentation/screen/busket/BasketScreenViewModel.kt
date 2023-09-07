package uz.gita.fooddedivery347.presentation.screen.busket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPref
import uz.gita.fooddedivery347.data.model.OrderData
import uz.gita.fooddedivery347.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class BasketScreenViewModel @Inject constructor(
    private val useCase: UseCase,
    private val sharedPref: SharedPref
) : ViewModel(), BasketContract.ViewModel {
    override val container =
        container<BasketContract.UIState, BasketContract.SideEffect>(BasketContract.UIState())
    override val uiState = MutableStateFlow(BasketContract.UIState())

    override fun onEventDispatcher(intent: BasketContract.Intent) {
        when (intent) {
            is BasketContract.Intent.Loading -> {
                useCase.getFoods().onEach { foods ->
                    uiState.update {
                        it.copy(foods = foods.filter {food ->
                            food.count>0
                        })
                    }
                }.launchIn(viewModelScope)
            }

            is BasketContract.Intent.Add -> {
                viewModelScope.launch {
                    useCase.plusCount(intent.foodID)
                }
            }
            is BasketContract.Intent.Remove -> {
                viewModelScope.launch {
                    useCase.minusCount(intent.foodID)
                }
            }

            is BasketContract.Intent.Comment -> {
                val list = ArrayList<FoodEntity>()
                useCase.getFoods().onEach {
                    list.addAll(it)

                }.launchIn(viewModelScope)

                val orderData = OrderData(
                    list = list,
                    userId = sharedPref.phone,
                    comment = intent.message,
                    allPrice = intent.allPrice
                )

                useCase.addOrders(orderData).onEach {
                    it.onSuccess { message ->
                        intent {
                            postSideEffect(BasketContract.SideEffect.HasError(message))
                        }
                        list.clear()
                        useCase.clearData().onEach {

                        }.launchIn(viewModelScope)
                    }

                    it.onFailure { message ->
                        intent {
                            postSideEffect(BasketContract.SideEffect.HasError(message.message!!))
                        }
                    }
                }.launchIn(viewModelScope)
            }

        }
    }
}