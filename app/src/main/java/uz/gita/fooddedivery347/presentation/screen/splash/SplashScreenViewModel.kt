package uz.gita.fooddedivery347.presentation.screen.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPref
import uz.gita.fooddedivery347.utils.myLog
import javax.inject.Inject

@HiltViewModel
class
SplashScreenViewModel @Inject constructor(
    private val direction: SplashScreenDirection,
    private val sharedPref: SharedPref
) : ViewModel() {

    init {
        findScreen()
    }

    private fun findScreen() {
        if (sharedPref.hasToken) {
            myLog(
                "Phone -> ${sharedPref.phone}\n" +
                    "Name -> ${sharedPref.name}\n" +
                    "Token -> ${sharedPref.hasToken}\n" +
                    "Sms Verification -> ${sharedPref.smsVerification}"
            )

            viewModelScope.launch {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModelScope.launch {
                        direction.openMainScreen()
                    }
                }, 2000)
            }
        } else {
            viewModelScope.launch {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModelScope.launch {
                        direction.openLoginScreen()
                    }
                }, 2000)
            }
        }
    }
}