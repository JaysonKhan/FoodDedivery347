package uz.gita.fooddedivery347.presentation.screen.home.page1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddedivery347.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class Page1ViewModel @Inject constructor(
    private val useCase: UseCase,
    private val direction: Page1Direction
): Page1Contract.ViewModel, ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    init {
        scope.launch {
            useCase.insertAllFoods()
        }
    }

    override fun onEventDispatcher(intent: Page1Contract.Intent) {
        when(intent){
            is Page1Contract.Intent.Add -> {
                viewModelScope.launch{
                    useCase.plusCount(intent.foodID)
                }
            }
            Page1Contract.Intent.Loading -> {
                 viewModelScope.launch {
                     useCase.getTypeData().onEach { typeList->
                         intent {
                             reduce {
                                 Page1Contract.UiState.FullList(typeList, useCase.getTotalSum())
                             }
                         }
                     }.launchIn(scope)
                 }
            }
            Page1Contract.Intent.OpenOrderScreen -> {
                viewModelScope.launch {
                    direction.goOrderScreen()
                }
            }
            is Page1Contract.Intent.Search -> {
                useCase.getTypeDataWithFoodsBySearchQuery(intent.search).onEach {typeList->
                    intent {
                        reduce {
                            Page1Contract.UiState.FullList(typeList, useCase.getTotalSum())
                        }
                    }
                }.launchIn(scope)
            }
        }

    }

    override val container = container<Page1Contract.UiState, Page1Contract.SideEffect>(Page1Contract.UiState.Loading)
}