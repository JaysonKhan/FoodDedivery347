package uz.gita.fooddedivery347.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.fooddedivery347.R

class SplashScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SplashScreenViewModel>()
        SplashScreenContent(viewModel)
    }
}

@Composable
fun SplashScreenContent(viewModel: SplashScreenViewModel) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.max_way_1),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }

}