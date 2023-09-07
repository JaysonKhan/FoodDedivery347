package uz.gita.fooddedivery347.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPref
import uz.gita.fooddedivery347.domain.repository.auth.AuthRepository
import uz.gita.fooddedivery347.utils.myLog
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val direction: LoginScreenContract.Direction,
    private val authRepository: AuthRepository,
    private val sharedPref: SharedPref

) : LoginScreenContract.ViewModel, ViewModel() {
    override val container =
        container<LoginScreenContract.UIState, LoginScreenContract.SideEffect>(LoginScreenContract.UIState.DeclareScreen)

    override fun onEventDispatcher(intent: LoginScreenContract.Intent) {
        when (intent) {
            is LoginScreenContract.Intent.Login -> {
                intent{
                    reduce {
                        LoginScreenContract.UIState.Loading
                    }
                }
                authRepository.createUserWithPhone(intent.phone, intent.activity).onEach {
                    it.onSuccess {
                        direction.openVerifyScreen()
                        myLog("Sms code VIewModel -> $it")
                        sharedPref.phone = intent.phone.trim()
                        sharedPref.name = intent.name.trim()
                    }

                    it.onFailure {
                        myLog("Sms code VIewModel -> $it")
                        intent {
                            postSideEffect(LoginScreenContract.SideEffect.HasError(it.message!!))
                            reduce {
                                LoginScreenContract.UIState.DeclareScreen
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
