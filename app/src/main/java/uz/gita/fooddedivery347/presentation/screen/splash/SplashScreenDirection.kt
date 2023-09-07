package uz.gita.fooddedivery347.presentation.screen.splash

import uz.gita.fooddedivery347.presentation.screen.home.HomeScreen
import uz.gita.fooddedivery347.presentation.screen.login.LoginScreen
import uz.gita.fooddedivery347.utils.navigation.AppNavigator
import javax.inject.Inject

class SplashScreenDirection @Inject constructor(private val appNavigator: AppNavigator): Direction {
    override suspend fun openMainScreen() {
        appNavigator.replace(HomeScreen())
    }

    override suspend fun openLoginScreen() {
        appNavigator.replace(LoginScreen())
    }
}