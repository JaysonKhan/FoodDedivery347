package uz.gita.fooddedivery347.presentation.screen.verify

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.presentation.screen.login.component.CommonLoginButton
import uz.gita.fooddedivery347.presentation.screen.login.component.CommonText
import uz.gita.fooddedivery347.ui.theme.LightGrayColor
import uz.gita.fooddedivery347.ui.theme.MyColor4
import uz.gita.fooddedivery347.utils.ConnectionState
import uz.gita.fooddedivery347.utils.OtpView
import uz.gita.fooddedivery347.utils.connectivityState
import uz.gita.fooddedivery347.utils.navigation.MyScreen

class VerifyScreen : MyScreen() {
    @Composable
    override fun Content() {
        val viewModel: VerifyScreenContract.ViewModel = getViewModel<VerifyScreenViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        VerifyScreenContent(uiState, viewModel::ovEventDispatcher, context)
    }
}

@Composable
fun VerifyScreenContent(
    uiState: State<VerifyScreenContract.UIState>,
    onEventDispatcher: (VerifyScreenContract.Intent) -> Unit,
    context: Context
) {
    var smsCode by remember { mutableStateOf("") }
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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CommonText(
                    text = "Enter SMS code,",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Normal,
                    color = MyColor4
                ) {}
                Spacer(modifier = Modifier.height(5.dp))

                Spacer(modifier = Modifier.height(20.dp))

                OtpView(otpText = smsCode, modifier = Modifier.fillMaxWidth(), onOtpTextChange = { smsCode = it }) {
                    onEventDispatcher(VerifyScreenContract.Intent.Verify(smsCode))
                }

                Spacer(modifier = Modifier.height(142.dp))

                CommonLoginButton(text = "Next", modifier = Modifier.fillMaxWidth()) {
                    if (smsCode.isNotEmpty()) {

                        onEventDispatcher(VerifyScreenContract.Intent.Verify(smsCode))

                    } else {
                        Toast.makeText(context, "Please fill data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun VerifyScreenContentPreview() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        VerifyScreenContent(
            uiState = remember {
                mutableStateOf(VerifyScreenContract.UIState.Loading)
            },
            onEventDispatcher = {

            },
            context = context
        )
    }
}