package uz.gita.fooddedivery347.presentation.screen.login

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.presentation.screen.login.component.CommonLoginButton
import uz.gita.fooddedivery347.presentation.screen.login.component.CommonText
import uz.gita.fooddedivery347.presentation.screen.login.component.CommonTextField
import uz.gita.fooddedivery347.ui.theme.LightGrayColor
import uz.gita.fooddedivery347.ui.theme.MyColor1
import uz.gita.fooddedivery347.ui.theme.MyColor2
import uz.gita.fooddedivery347.ui.theme.MyColor4
import uz.gita.fooddedivery347.ui.theme.MyColor5
import uz.gita.fooddedivery347.utils.ConnectionState
import uz.gita.fooddedivery347.utils.connectivityState
import uz.gita.fooddedivery347.utils.navigation.MyScreen

class LoginScreen : MyScreen() {
    @Composable
    override fun Content() {
        val viewModel: LoginScreenContract.ViewModel = getViewModel<LoginScreenViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        LoginScreenContent(uiState, viewModel::onEventDispatcher, context)
    }
}

@Composable
fun LoginScreenContent(
    uiState: State<LoginScreenContract.UIState>,
    onEventDispatcher: (LoginScreenContract.Intent) -> Unit,
    context: Context
) {
    var mobile by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
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
        when (uiState.value) {
            is LoginScreenContract.UIState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LoginScreenContract.UIState.DeclareScreen -> {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.height(60.dp))
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CommonText(
                                text = "Welcome,",
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Bold,
                                color = MyColor4
                            ) {

                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            CommonText(
                                text = "Sign in to continue!",
                                fontSize = 28.sp,
                                color = MyColor4
                            ) {}
                        }
                        Spacer(modifier = Modifier.height(60.dp))
                        CommonTextField(
                            text = name,
                            placeholder = "Ism:",
                            onValueChange = { name = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        CommonTextField(
                            text = mobile,
                            placeholder = "Telefon raqam:",
                            onValueChange = {
                                if (it.length < 10) {
                                    mobile = it
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                            visualTransformation = PhoneVisualTransformation(
                                mask = "+998 00 000 00 00",
                                '0'
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        CommonLoginButton(text = "Login", modifier = Modifier.fillMaxWidth()) {
                            if (mobile.isNotEmpty() && name.isNotEmpty()) {
                                onEventDispatcher.invoke(
                                    LoginScreenContract.Intent.Login(
                                        "+998$mobile",
                                        name,
                                        context as Activity
                                    )
                                )
                            } else {
                                Toast.makeText(context, "Please fill data", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LoginScreenContent(
            uiState = remember {
                mutableStateOf(LoginScreenContract.UIState.Loading)
            },
            onEventDispatcher = {

            },
            context = context
        )
    }
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}











