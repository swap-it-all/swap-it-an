package com.swapit.oopswap.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.oopswap.R
import com.swapit.oopswap.data.datasource.local.model.alert.AlertType
import com.swapit.oopswap.domain.model.alert.Alert
import com.swapit.oopswap.ui.navigation.NavItem
import com.swapit.oopswap.ui.shopping.model.calculateTime
import com.swapit.oopswap.ui.theme.BackgroundColor
import com.swapit.oopswap.ui.theme.Gray4
import com.swapit.oopswap.ui.theme.Paddings
import com.swapit.oopswap.ui.theme.Primary
import com.swapit.oopswap.ui.theme.Typography
import com.swapit.oopswap.ui.theme.White

@Composable
fun AlertCard(
    alertCardData: Alert,
    alertViewModel: AlertViewModel,
    navController: NavHostController,
) {
    val convertTime = calculateTime(alertCardData.createdAt)
    val icon =
        when (alertCardData.type) {
            AlertType.CHAT.name -> R.drawable.ic_chat
            AlertType.COMPLETED.name -> R.drawable.ic_shopping_bag
            AlertType.REJECTED.name -> R.drawable.ic_shopping_bag
            AlertType.ACCEPTED.name -> R.drawable.ic_shopping_bag
            AlertType.REQUESTED.name -> R.drawable.ic_shopping_bag
            AlertType.REVIEW.name -> R.drawable.ic_pencil
            else -> R.drawable.ic_bell
        }
    Card(
        modifier = Modifier.padding(Paddings.xlarge, Paddings.medium),
        colors =
            CardDefaults.cardColors(
                containerColor = BackgroundColor,
            ),
        onClick = {
            alertViewModel.readAllAlertsByRelatedId(alertCardData.relatedData)
            if (alertCardData.type == AlertType.REQUESTED.name ||
                alertCardData.type == AlertType.ACCEPTED.name ||
                alertCardData.type == AlertType.REJECTED.name
            ) {
                navController.navigate(NavItem.ShoppingDetail.screenRoute + "/${alertCardData.relatedData}")
            } else if (alertCardData.type == AlertType.CHAT.name) {
                navController.navigate(NavItem.ChatRoom.screenRoute + "/${alertCardData.relatedData}")
            } else if (alertCardData.type == AlertType.REVIEW.name) {
                navController.navigate(NavItem.User.screenRoute)
            } else if (alertCardData.type == AlertType.COMPLETED.name) {
                navController.navigate(NavItem.User.screenRoute)
            }
        },
    ) {
        Column {
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier =
                        Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(White),
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = stringResource(R.string.alert_icon),
                        tint = Primary,
                        modifier = Modifier.size(36.dp),
                    )
                }
                Column(modifier = Modifier.padding(Paddings.medium)) {
                    Text(
                        text = alertCardData.body,
                        style = Typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = convertTime,
                            style = Typography.labelLarge,
                            color = Gray4,
                        )
                    }
                }
            }
            Spacer(Modifier.size(20.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray4),
            )
        }
    }
}
