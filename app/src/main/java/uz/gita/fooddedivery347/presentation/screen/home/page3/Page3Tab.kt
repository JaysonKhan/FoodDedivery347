package uz.gita.fooddedivery347.presentation.screen.home.page3

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.maps.android.ktx.utils.heatmaps.toWeightedLatLng
import com.google.protobuf.DescriptorProtos.SourceCodeInfo.Location
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.presentation.screen.home.HomeScreen
import uz.gita.fooddedivery347.ui.theme.MyColor1
import uz.gita.fooddedivery347.ui.theme.MyColor2
import uz.gita.fooddedivery347.ui.theme.MyColor4


class Page3Tab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "SOMETHING"
            val icon = painterResource(R.drawable.ic_location)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MissingPermission")
    @Composable
    override fun Content() {
        val parentScreen = LocalNavigator.current?.parent?.lastItem
        if (parentScreen !is HomeScreen) return
        val viewModel = parentScreen.getViewModel<Page3ViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current

        val filials = listOf(
            LatLng(41.436455669435766, 69.07788254320621), //1
            LatLng(41.436455669435766, 69.41918961703777), //2
            LatLng(41.436455669435766, 69.25302676856518), //3
            LatLng(41.36346795948001, 69.25302676856518), //4
            LatLng(41.36346795948001, 69.07788254320621), //5
            LatLng(41.36346795948001, 69.41918961703777), //6
            LatLng(41.36346795948001, 69.25302676856518), //7
            LatLng(41.20973952341823, 69.07788254320621), //8
            LatLng(41.36346795948001, 69.25302676856518), //9
            LatLng(41.20973952341823, 69.41918961703777)
        )
        val wayPointsState = remember { mutableStateListOf<LatLng>() }

        var mapState by remember {
            mutableStateOf(false)
        }

        when (uiState.value) {
            Page3Contract.UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Page3Contract.UiState.State -> {
                val theme by remember { mutableStateOf((uiState.value as Page3Contract.UiState.State).mapState) }
                val scaffoldState = rememberScaffoldState()
                val uiSettings = remember {
                    MapUiSettings(
                        zoomControlsEnabled = false,
                        compassEnabled = true,
                        myLocationButtonEnabled = true
                    )
                }
                val tashkent = LatLng(41.31376302904714, 69.25917472690344)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(tashkent, 10f)
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                viewModel.eventDispatcher(
                                    Page3Contract.Intent.ToggleFalloutMap(
                                        mapState,
                                        theme
                                    )
                                )
                                mapState = !mapState
                            },
                            backgroundColor = MyColor4,
                            modifier = Modifier.padding(bottom = 56.dp)
                        ) {
                            Icon(
                                imageVector = if (mapState) {
                                    Icons.Default.ToggleOff
                                } else Icons.Default.ToggleOn,
                                contentDescription = "Toggle Fallout map",
                                tint = MyColor1
                            )
                        }
                    }
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        properties = (uiState.value as Page3Contract.UiState.State).mapState.properties,
                        uiSettings = uiSettings,
                        onMapLongClick = {
                            viewModel.eventDispatcher(Page3Contract.Intent.OnMapLongClick(it))
                        },
                        googleMapOptionsFactory = {
                            GoogleMapOptions()
                                .camera(CameraPosition.fromLatLngZoom(tashkent, 10f))
                        },
                        cameraPositionState = cameraPositionState,
                        onMyLocationButtonClick = {
                            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                            fusedLocationClient.lastLocation.addOnSuccessListener {
                                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                    LatLng(it.latitude, it.longitude),
                                    15f
                                )
                            }
                            true
                        }) {
                        (uiState.value as Page3Contract.UiState.State).mapState.parkingSpots.forEach { spot ->
                            Marker(
                                state = MarkerState(LatLng(spot.lat, spot.lng)),
                                title = "Tanlandi:  (${spot.lat}, ${spot.lng})",
                                snippet = "Uzuuun bosib tur o'chirish uchun...",
                                onInfoWindowLongClick = {
                                    viewModel.eventDispatcher(
                                        Page3Contract.Intent.OnInfoWindowLongClick(spot)
                                    )
                                },
                                onClick = {
                                    it.showInfoWindow()
                                    true
                                },
                                icon = BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_ORANGE
                                ),
                                draggable = true
                            )
                        }
                        filials.forEach {
                            wayPointsState.add(it)
                            Marker(
                                state = MarkerState(it),
                                draggable = false,
                                title = "HELLO FROM KHAN347",
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.location)
                            )
                        }
                        Polyline(
                            points = wayPointsState,
                            color = MyColor4, // Qizil rang
                            width = 40f // Qalinlik
                        )
                    }

                }
            }
        }
    }

}