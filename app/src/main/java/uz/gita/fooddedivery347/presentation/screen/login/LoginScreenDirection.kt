package uz.gita.fooddedivery347.presentation.screen.login

import uz.gita.fooddedivery347.presentation.screen.verify.VerifyScreen
import uz.gita.fooddedivery347.utils.navigation.AppNavigator
import javax.inject.Inject

class LoginScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
): LoginScreenContract.Direction {
    override suspend fun openVerifyScreen() {
        appNavigator.navigateTo(VerifyScreen())
    }
}