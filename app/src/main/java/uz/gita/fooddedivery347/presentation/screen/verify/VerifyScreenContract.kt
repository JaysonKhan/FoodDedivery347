package uz.gita.fooddedivery347.presentation.screen.verify

import org.orbitmvi.orbit.ContainerHost


interface VerifyScreenContract {
    sealed interface Intent {
        data class Verify(val smsCode: String): Intent
    }

    sealed interface UIState {
        object Loading: UIState
    }

    sealed interface SideEffect {
        data class HasError(val message: String): SideEffect
    }


    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun ovEventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun openMainScreen()
    }
}