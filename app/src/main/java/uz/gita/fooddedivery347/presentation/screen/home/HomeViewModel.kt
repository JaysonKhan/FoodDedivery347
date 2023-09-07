package uz.gita.fooddedivery347.presentation.screen.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddedivery347.domain.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase,
    private val direction: HomeDirection
): HomeContract.Model, ViewModel() {
    override val container = container<HomeContract.UiState, HomeContract.SideEffect>(HomeContract.UiState.Loading)

    override fun eventDispatcher(intent: HomeContract.Intent) {

    }
}