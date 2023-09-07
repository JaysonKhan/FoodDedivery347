package uz.gita.fooddedivery347.presentation.screen.busket

import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddedivery347.data.local.entity.FoodEntity

interface BasketContract {
    sealed interface Intent {
        object Loading: Intent
        data class Add(val foodID: Int): Intent
        data class Remove(val foodID: Int): Intent
        data class Comment(val message: String, val allPrice:Long): Intent
    }

    data class UIState(val foods: List<FoodEntity> = emptyList())

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }
}