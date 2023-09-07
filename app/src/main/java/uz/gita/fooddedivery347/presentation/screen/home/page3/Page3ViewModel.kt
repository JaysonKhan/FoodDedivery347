package uz.gita.fooddedivery347.presentation.screen.home.page3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.MapProperties
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddedivery347.data.model.MapState
import uz.gita.fooddedivery347.data.model.ParkingSpot
import uz.gita.fooddedivery347.domain.usecase.UseCase
import uz.gita.fooddedivery347.utils.MapStyle
import uz.gita.fooddedivery347.utils.MapStyleDark
import uz.gita.fooddedivery347.utils.myLog
import javax.inject.Inject

@HiltViewModel
class Page3ViewModel @Inject constructor(
    private val useCase: UseCase,
    private val direction: Page3Direction
) : Page3Contract.Model, ViewModel() {

    val dark = MapStyleOptions(MapStyleDark.json)
    val light = MapStyleOptions(MapStyle.json)

    init {
        viewModelScope.launch {
            useCase.getParkingSpots().collectLatest { spots ->
                intent {
                    reduce {
                        Page3Contract.UiState.State(MapState(parkingSpots = spots))
                    }
                }
            }
        }
    }

    override fun eventDispatcher(intent: Page3Contract.Intent) {
        when (intent) {
            is Page3Contract.Intent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    useCase.deleteParkingSpot(intent.spot)
                }
            }

            is Page3Contract.Intent.OnMapLongClick -> {
                viewModelScope.launch {
                    useCase.insertParkingSpot(
                        ParkingSpot(
                            intent.latLng.latitude,
                            intent.latLng.longitude
                        )
                    )
                }
            }

            is Page3Contract.Intent.ToggleFalloutMap -> {
                myLog(intent.mode.toString(), "COLOR")
                intent {
                    reduce {
                        Page3Contract.UiState.State(
                            intent.theme.copy(
                                properties = MapProperties(mapStyleOptions = if (intent.mode) light else dark)
                            )
                        )
                    }
                }
            }
        }
    }

    override val container =
        container<Page3Contract.UiState, Page3Contract.SideEffect>(Page3Contract.UiState.Loading)
}