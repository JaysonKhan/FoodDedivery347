package uz.gita.fooddedivery347.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.ui.theme.MyColor1
import uz.gita.fooddedivery347.ui.theme.MyColor2
import uz.gita.fooddedivery347.ui.theme.MyColor4
import uz.gita.fooddedivery347.ui.theme.MyColor5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    Box(
        modifier = modifier
            .padding(top = 8.dp)
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))


    ) {
        val trailingIconView = @Composable {
            IconButton(
                onClick = {
                    onValueChange("")
                }
            ) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "",
                    tint = MyColor4
                )
            }
        }

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = search,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MyColor1,
                focusedIndicatorColor = MyColor5,
                unfocusedIndicatorColor = MyColor5,
                disabledIndicatorColor = MyColor5,
                cursorColor = MyColor2,
                containerColor = MyColor5
            ),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MyColor4)
                )
            },
            trailingIcon = if (search.isNotBlank()) trailingIconView else null,
            placeholder = { Text(text = "Search...", color = MyColor4) }
        )
    }
}