package uz.gita.fooddedivery347.presentation.screen.home.page3

import com.google.android.gms.maps.model.LatLng
import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddedivery347.data.model.MapState
import uz.gita.fooddedivery347.data.model.ParkingSpot

interface Page3Contract {
    sealed interface Model: ContainerHost<UiState, SideEffect> {
        fun eventDispatcher(intent: Intent)
    }
    sealed interface UiState{
        object Loading: UiState
        data class State(val mapState: MapState): UiState

    }
    sealed interface SideEffect{
        data class ShowToast(val message:String): SideEffect

    }
    sealed interface Intent{
        data class ToggleFalloutMap(val mode: Boolean, val theme:MapState): Intent
        data class OnMapLongClick(val latLng: LatLng): Intent
        data class OnInfoWindowLongClick(val spot: ParkingSpot): Intent
    }
}