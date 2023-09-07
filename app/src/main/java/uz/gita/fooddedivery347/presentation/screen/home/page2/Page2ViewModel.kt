package uz.gita.fooddedivery347.presentation.screen.home.page2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPref
import uz.gita.fooddedivery347.data.model.OrderData
import uz.gita.fooddedivery347.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class Page2ViewModel  @Inject constructor(
    private val useCase: UseCase,
    private val sharedPref: SharedPref
) : ViewModel(), Page2Contract.ViewModel {
    override val container = container<Page2Contract.UIState, Page2Contract.SideEffect>(Page2Contract.UIState.Loading)

    init {
        useCase.getFoods().onEach { foods ->
            intent {
                reduce {
                    Page2Contract.UIState.OrderedList(foods, useCase.getTotalSum())
                }
            }
        }.launchIn(viewModelScope)
    }


    override fun onEventDispatcher(intent: Page2Contract.Intent) {
        when (intent) {
            is Page2Contract.Intent.Add -> {
                viewModelScope.launch {
                    useCase.plusCount(intent.foodID)
                }
            }

            is Page2Contract.Intent.Remove -> {
                viewModelScope.launch {
                    useCase.minusCount(intent.foodID)
                }
            }
            is Page2Contract.Intent.Comment -> {
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
                            postSideEffect(Page2Contract.SideEffect.HasError(message))
                        }
                        list.clear()
                        useCase.clearData().onEach {

                        }.launchIn(viewModelScope)
                    }

                    it.onFailure { message ->
                        intent {
                            postSideEffect(Page2Contract.SideEffect.HasError(message.message!!))
                        }
                    }
                }.launchIn(viewModelScope)
            }

        }
    }
}