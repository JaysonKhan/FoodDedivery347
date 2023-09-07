package uz.gita.fooddedivery347.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.fooddedivery347.ui.theme.FoodDedivery347Theme
import uz.gita.fooddedivery347.ui.theme.MyColor1
import uz.gita.fooddedivery347.ui.theme.MyColor5

@Composable
fun CheckComponent(
    amunt: Long,
    deliveryAmount: Long
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MyColor5,
            contentColor = MyColor1
        ),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Yetkazish xizmati:")
                Text(
                    text = buildAnnotatedString {
                        append(deliveryAmount.toString())
                        append(" So'm")
                    },
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .border(
                            1.dp,
                            MyColor1,
                            CircleShape
                        )
                        .background(MyColor1.copy(alpha = 0.2f), CircleShape)
                        .padding(16.dp, 8.dp)
                )
            }
            Text(text = "Umumiy Summa:")
            Text(
                text = buildAnnotatedString {
                    append((amunt + deliveryAmount).toString())
                    append(" So'm")
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

    }

}

@Preview
@Composable
fun PrewCheck() {
    FoodDedivery347Theme {
        Surface {
            CheckComponent(amunt = 38000, deliveryAmount = 3000)
        }
    }
}