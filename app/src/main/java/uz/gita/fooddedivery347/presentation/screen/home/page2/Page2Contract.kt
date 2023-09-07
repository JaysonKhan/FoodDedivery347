package uz.gita.fooddedivery347.presentation.screen.home.page2

import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddedivery347.data.local.entity.FoodEntity

interface Page2Contract {
    sealed interface Intent {
        data class Add(val foodID: Int): Intent
        data class Remove(val foodID: Int): Intent
        data class Comment(val message: String, val allPrice:Long): Intent
    }

    sealed interface UIState{
        object Loading:UIState
        data class OrderedList(val foods: List<FoodEntity>,val sum:Long):UIState
    }

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }
}