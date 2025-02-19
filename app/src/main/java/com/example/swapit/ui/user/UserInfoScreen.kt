package com.example.swapit.ui.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.swapit.ui.component.AppBar
import com.example.swapit.ui.component.BottomNavigationBar
import com.example.swapit.ui.navigation.NavigationModule

@SuppressLint("NotConstructor")
class UserInfoScreen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun UserInfoScreen(navController: NavHostController) {
        val navigationModule = NavigationModule()
        Scaffold(
            topBar = {
                AppBar(navController = navController)
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Blue),
            ) {
                Text(
                    text = "유저 정보",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                )
    @Composable
    fun ProfileCard(
        userName: String = "하울의움직이는성",
        userEmail: String = "swapit202501@gmail.com"
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_plus_circle),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
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
                            tint = Gray4
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
        review: Float = 4.8f
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
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
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
                    modifier = Modifier.height(60.dp)
                )
                Column(
                    modifier = Modifier
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
                    modifier = Modifier.height(60.dp)
                )
                Column(
                    modifier = Modifier
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
}
