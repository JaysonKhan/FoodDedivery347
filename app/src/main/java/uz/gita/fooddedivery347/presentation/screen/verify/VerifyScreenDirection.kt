package uz.gita.fooddedivery347.presentation.screen.verify

import uz.gita.fooddedivery347.presentation.screen.home.HomeScreen
import uz.gita.fooddedivery347.utils.navigation.AppNavigator
import javax.inject.Inject

class VerifyScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
): VerifyScreenContract.Direction {
    override suspend fun openMainScreen() {
        appNavigator.replaceAll(HomeScreen())
    }
}