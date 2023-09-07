package uz.gita.fooddedivery347.presentation.screen.home.page1

import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddedivery347.data.local.entity.TypeDataWithFoods

interface Page1Contract {
    sealed interface Intent {
        object Loading: Intent
        object OpenOrderScreen: Intent
        data class Add(val foodID: Int): Intent
        data class Search(val search: String): Intent
    }

    sealed interface UiState {
        object Loading:UiState
        object Error:UiState
        data class FullList(
            val list: List<TypeDataWithFoods>,
            val sum:Long
            ) : UiState
    }

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    sealed interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }
}