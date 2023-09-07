package uz.gita.fooddedivery347.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Badge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ResetTv
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.gita.fooddedivery347.presentation.screen.home.page1.Page1Tab
import uz.gita.fooddedivery347.presentation.screen.home.page2.Page2Tab
import uz.gita.fooddedivery347.presentation.screen.home.page3.Page3Tab
import uz.gita.fooddedivery347.ui.theme.MyColor1
import uz.gita.fooddedivery347.ui.theme.MyColor3
import uz.gita.fooddedivery347.ui.theme.MyColor4
import uz.gita.fooddedivery347.ui.theme.MyColor5
import uz.gita.fooddedivery347.utils.navigation.MyScreen
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.utils.ConnectionState
import uz.gita.fooddedivery347.utils.connectivityState

class HomeScreen : MyScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val connection by connectivityState()
        val isConnected = connection === ConnectionState.Available
        if (!isConnected) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.no_internet),
                        contentDescription = "No Internet"
                    )
                    Text(
                        text = "Internetga ulanishni tekshiring...",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MyColor4
                    )
                }
            }
        } else {
            TabNavigator(tab = Page3Tab()) { nav ->
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier,
                            containerColor = MyColor4,
                            contentColor = MyColor3
                        ) {
                            TabNavigationItem(tab = Page1Tab {
                                nav.current = Page2Tab()
                            })
                            TabNavigationItem(tab = Page2Tab())
                            TabNavigationItem(tab = Page3Tab())
                        }
                    },
                    content = { paddingValues ->
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            CurrentTab()
                        }
                    }
                )
            }
        }

    }

    @Composable
    fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = {
                tabNavigator.current = tab;
            },
            icon = {
                Icon(
                    painter = tab.options.icon!!,
                    contentDescription = tab.options.title,
                    tint = MyColor1,
                    modifier = Modifier.size(30.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MyColor4,
                indicatorColor = MyColor5
            )
        )
    }
}