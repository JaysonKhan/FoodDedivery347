package uz.gita.fooddedivery347.presentation.screen.login

import android.app.Activity
import org.orbitmvi.orbit.ContainerHost

interface LoginScreenContract {

    sealed interface Intent {
        data class Login(val phone: String, val name: String,val activity: Activity): Intent
    }

    sealed interface UIState {
        object Loading: UIState
        object DeclareScreen: UIState
    }

    sealed interface SideEffect {
        data class HasError(val message: String) : SideEffect
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun openVerifyScreen()
    }
}