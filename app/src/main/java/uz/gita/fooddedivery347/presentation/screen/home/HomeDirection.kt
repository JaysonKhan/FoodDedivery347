package uz.gita.fooddedivery347.presentation.screen.home

import uz.gita.fooddedivery347.presentation.screen.home.page1.Page1Tab
import uz.gita.fooddedivery347.presentation.screen.home.page2.Page2Tab
import uz.gita.fooddedivery347.presentation.screen.home.page3.Page3Tab
import uz.gita.fooddedivery347.utils.navigation.AppNavigator
import javax.inject.Inject

interface HomeDirection {
    suspend fun navigatePage2()
    suspend fun navigatePage3()

}

class HomeDirectionImpl @Inject constructor(
    private val navigator: AppNavigator
): HomeDirection {
    override suspend fun navigatePage2() {
//        navigator.navigateTo(Page2Tab)
    }

    override suspend fun navigatePage3() {
//        navigator.navigateTo(Page3Tab)
    }

}