package uz.gita.fooddedivery347.presentation.screen.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.fooddedivery347.ui.theme.LightGrayColor
import uz.gita.fooddedivery347.ui.theme.MyColor2
import uz.gita.fooddedivery347.ui.theme.MyColor3
import uz.gita.fooddedivery347.ui.theme.MyColor4
import uz.gita.fooddedivery347.ui.theme.MyColor5
import uz.gita.fooddedivery347.ui.theme.PinkColor

@Composable
fun CommonText(
    text: String,
    color: Color = MyColor2,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    function: () -> Unit
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = Modifier.clickable {
            function()
        }
    )
}

@Composable
fun CommonLoginButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .background(MyColor4, RoundedCornerShape(16.dp))
            .height(58.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MyColor4
        ),
        onClick = { onClick() }
    ) {
        Text(text = text, fontSize = 20.sp, color = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = { Text(text = placeholder, color = MyColor5) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MyColor5,
            cursorColor = Color.Black,
            placeholderColor = MyColor3,
            textColor = MyColor2,
            disabledBorderColor = MyColor4,
            unfocusedBorderColor = MyColor5
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = visualTransformation
    )
}