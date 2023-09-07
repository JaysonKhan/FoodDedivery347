package uz.gita.fooddedivery347.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.ui.theme.MyColor1
import uz.gita.fooddedivery347.ui.theme.MyColor2
import uz.gita.fooddedivery347.ui.theme.MyColor3
import uz.gita.fooddedivery347.ui.theme.MyColor5

@Composable
fun FoodComponent(
    food: FoodEntity,
    modifier: Modifier,
    searchText: String = "",
    addListener: () -> Unit
) {
    val specificString by remember { mutableStateOf(searchText) }
    var showShimmer by remember { mutableStateOf(true) }
    val annotatedLinkString = buildAnnotatedString {
        val str = food.name
        val startIndex = str.indexOf(specificString)
        val endIndex = startIndex + specificString.length
        append(str)
        addStyle(
            style = SpanStyle(
                color = MyColor5,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            MyColor1
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer ))
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(food.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.errorimage),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                onSuccess = {
                    showShimmer = false
                }
            )

            Text(
                text = annotatedLinkString,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .width(190.dp)
                    .height(32.dp)
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

//            Text(
//                text = food.description,
//                fontWeight = FontWeight.Normal,
//                style = MaterialTheme.typography.bodySmall,
//                color = MyColor1,
//                modifier = Modifier.width(170.dp),
//                textAlign = TextAlign.Start,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                MyColor3,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(food.cost.toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                MyColor3
                            )
                        ) {
                            append(" So'm")
                        }
                    },
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = { addListener.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.addtobasket),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(6.dp)
                            .size(48.dp),
                        tint = MyColor3
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun prewFoComp() {
    Surface {
        FoodComponent(
            food = FoodEntity(
                id = 0,
                name = "Lavash Burger Qazi Osh fjknsf",
                description = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
                cost = 23000,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlflfn4ceOpcmtTBdbyg3vPZsuytdyqOCgCQ&usqp=CAU",
                count = 0,
                typeDataId = 1
            ), modifier = Modifier.padding(8.dp),
            searchText = "Bur"
        ) {

        }
    }
}
@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition()
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            )
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

