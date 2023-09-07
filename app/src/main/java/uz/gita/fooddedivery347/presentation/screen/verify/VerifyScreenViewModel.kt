package uz.gita.fooddedivery347.presentation.screen.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPref
import uz.gita.fooddedivery347.domain.repository.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class VerifyScreenViewModel @Inject constructor(
    private val direction: VerifyScreenContract.Direction,
    private val authRepository: AuthRepository,
    private val sharedPref: SharedPref
): VerifyScreenContract.ViewModel, ViewModel(){

    override val container = container<VerifyScreenContract.UIState, VerifyScreenContract.SideEffect>(
        VerifyScreenContract.UIState.Loading)

    override fun ovEventDispatcher(intent: VerifyScreenContract.Intent) {
        when(intent) {
            is VerifyScreenContract.Intent.Verify -> {
                authRepository.signWithCredential(intent.smsCode).onEach {
                    it.onSuccess {
                        direction.openMainScreen()
                        sharedPref.hasToken = true
                    }

                    it.onFailure {
                        intent {
                            postSideEffect(VerifyScreenContract.SideEffect.HasError(it.message.toString()))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}