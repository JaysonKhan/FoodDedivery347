package uz.gita.fooddedivery347.presentation.screen.home

import org.orbitmvi.orbit.ContainerHost

interface HomeContract {
    sealed interface Model:ContainerHost<UiState, SideEffect>{
        fun eventDispatcher(intent: Intent)
    }
    sealed interface UiState{
        object Loading: UiState
        object Error: UiState

    }
    sealed interface SideEffect{
        data class ShowToast(val message:String): SideEffect

    }
    sealed interface Intent{

    }
}