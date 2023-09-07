package uz.gita.fooddedivery347

import android.Manifest
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.fooddedivery347.ui.theme.FoodDedivery347Theme
import uz.gita.fooddedivery347.presentation.screen.home.HomeScreen
import uz.gita.fooddedivery347.presentation.screen.splash.SplashScreen
import uz.gita.fooddedivery347.utils.navigation.NavigationHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        setContent {

            FoodDedivery347Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigator(screen = SplashScreen()){ navigator ->
                        LaunchedEffect(navigator){
                            navigationHandler.navigatorBuffer
                                .onEach { it.invoke(navigator) }
                                .launchIn(lifecycleScope)
                        }
                        CurrentScreen()
                    }
                }
            }
        }
    }

    private fun checkPermission() {
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, LocationManager.GPS_PROVIDER)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (it.areAllPermissionsGranted()) {
                            // Ruxsat berildi
                            // Notificationlarni chiqaring
                        }
                        if (it.isAnyPermissionPermanentlyDenied) {
                            // Foydalanuvchi ruxsat berishni rad qildi
                            // Xodimiz qo'shimcha ma'lumot yoki qo'llanma ko'rsating
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener { error ->
                // Xatolik sodir bo'ldi
            }
            .onSameThread()
            .check()
    }
}
