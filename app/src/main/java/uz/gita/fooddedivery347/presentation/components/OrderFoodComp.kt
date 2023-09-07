package uz.gita.fooddedivery347.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.gita.fooddedivery347.R
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.ui.theme.FoodDedivery347Theme
import uz.gita.fooddedivery347.ui.theme.MyColor2
import uz.gita.fooddedivery347.ui.theme.MyColor3
import uz.gita.fooddedivery347.ui.theme.colorBlack
import uz.gita.fooddedivery347.ui.theme.colorRedDark
import uz.gita.fooddedivery347.ui.theme.colorWhite

@Composable
fun OrderFoodComp(
    item: FoodEntity,
    listenerP: () -> Unit,
    listenerM: () -> Unit,
    listenerRemove: () -> Unit
) {
    var countState by remember { mutableStateOf(item.count) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            AsyncImage(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.food512),
                error = painterResource(id = R.drawable.errorimage),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    color = colorBlack,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    softWrap = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                MyColor3,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(item.cost.toString())
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            if (countState > 0) {
                                countState--
                                listenerM.invoke()
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(1.dp, MyColor2, CircleShape)
                            .padding(4.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp, 15.dp),
                            painter = painterResource(id = R.drawable.remove),
                            contentDescription = "Remove",
                        )
                    }
                    Text(
                        text = countState.toString(),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        modifier = Modifier.padding(16.dp)
                    )

                    IconButton(
                        onClick = {
                            if (countState < 10) {
                                countState++
                                listenerP.invoke()
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(1.dp, MyColor2, CircleShape)
                            .padding(4.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp, 15.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            listenerRemove.invoke()
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .padding(4.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun PrewOrderContent() {
    FoodDedivery347Theme {
        Surface {
            OrderFoodComp(
                item = FoodEntity(
                    id = 0,
                    name = "Lavash Burger Qazi Osh fjknsf",
                    description = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
                    cost = 23000,
                    image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlflfn4ceOpcmtTBdbyg3vPZsuytdyqOCgCQ&usqp=CAU",
                    count = 0,
                    typeDataId = 1
                ), listenerP = { /*TODO*/ }, listenerM = { }) {
            }
        }
    }
}