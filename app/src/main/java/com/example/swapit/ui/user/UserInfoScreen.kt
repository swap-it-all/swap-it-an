package com.example.swapit.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swapit.R
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.BottomNavigationBar
import com.example.swapit.ui.theme.BackgroundColor
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray4
import com.example.swapit.ui.theme.Paddings
import com.example.swapit.ui.theme.Primary
import com.example.swapit.ui.theme.Typography
import com.example.swapit.ui.theme.White

@Composable
fun UserInfoScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBar(navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    ) { contentPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            color = BackgroundColor,
        ) {
            Column {
                ProfileCard()
                ProfileSwapCard()
                LazyColumn {
                    item {
                        ProfileItem(
                            text = "내가 등록한 물건",
                            count = 5,
                        )
                        HorizontalDivider()
                        ProfileItem(
                            text = "받은 스왑 리뷰",
                            count = 11,
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileCard(
    userName: String = "하울의움직이는성",
    userEmail: String = "swapit202501@gmail.com",
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Paddings.xlarge, Paddings.smallMedium),
        colors =
            CardDefaults.cardColors(
                containerColor = White,
            ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add_plus_circle),
                contentDescription = "Profile Picture",
                modifier =
                    Modifier
                        .size(86.dp)
                        .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.height(86.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(userName, style = Typography.titleLarge, color = Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pencil),
                        contentDescription = "프로필 수정",
                        tint = Gray4,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(userEmail, style = Typography.bodyMedium, color = Gray4)
            }
        }
    }
}

@Composable
fun ProfileSwapCard(
    productCount: Int = 22,
    swapCount: Int = 16,
    review: Float = 4.8f,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Paddings.xlarge, Paddings.smallMedium),
        colors =
            CardDefaults.cardColors(
                containerColor = White,
            ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier =
                    Modifier
                        .height(80.dp)
                        .width(127.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(productCount.toString(), style = Typography.headlineSmall, color = Primary)
                Spacer(modifier = Modifier.size(8.dp))
                Text("물건 수", style = Typography.labelLarge, color = Gray4)
            }
            VerticalDivider(
                modifier = Modifier.height(60.dp),
            )
            Column(
                modifier =
                    Modifier
                        .height(80.dp)
                        .width(127.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(swapCount.toString(), style = Typography.headlineSmall, color = Primary)
                Spacer(modifier = Modifier.size(8.dp))
                Text("스왑 수", style = Typography.labelLarge, color = Gray4)
            }
            VerticalDivider(
                modifier = Modifier.height(60.dp),
            )
            Column(
                modifier =
                    Modifier
                        .height(80.dp)
                        .width(127.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(review.toString(), style = Typography.headlineSmall, color = Primary)
                Spacer(modifier = Modifier.size(8.dp))
                Text("리뷰 평점", style = Typography.labelLarge, color = Gray4)
            }
        }
    }
}

@Composable
fun ProfileItem(
    text: String,
    count: Int,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = Typography.bodyMedium, color = Black)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = count.toString(), style = Typography.bodyMedium, color = Black)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "더보기",
            tint = Black,
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileItemPreview() {
    ProfileItem(
        text = "내가 등록한 물건",
        count = 5,
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileSwapCardPreview() {
    ProfileSwapCard()
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ProfileCard()
}

@Preview(showBackground = true)
@Composable
fun UserInfoScreenPreview() {
    UserInfoScreen(navController = NavHostController(LocalContext.current))
}
