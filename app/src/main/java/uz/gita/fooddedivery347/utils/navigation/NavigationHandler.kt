package uz.gita.fooddedivery347.utils.navigation

import kotlinx.coroutines.flow.SharedFlow
import uz.gita.fooddedivery347.utils.navigation.NavigationArg

interface NavigationHandler {
    val navigatorBuffer:SharedFlow<NavigationArg>
}