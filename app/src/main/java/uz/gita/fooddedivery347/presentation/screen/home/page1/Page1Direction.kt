package uz.gita.fooddedivery347.presentation.screen.home.page1

import uz.gita.fooddedivery347.presentation.screen.busket.BasketScreen
import uz.gita.fooddedivery347.utils.navigation.AppNavigator
import javax.inject.Inject

interface Page1Direction {
    suspend fun goOrderScreen()
}

class Page1DirectionImpl @Inject constructor(
    val appNavigator: AppNavigator
) : Page1Direction {
    override suspend fun goOrderScreen() {
        appNavigator.navigateTo(BasketScreen())
    }
}