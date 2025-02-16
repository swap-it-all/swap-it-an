package com.example.swapit.ui.shopping.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swapit.data.model.ShoppingDetailData
import com.example.swapit.ui.theme.Paddings

val defaultShoppingDetailData =
    ShoppingDetailData(
        imageUri =
            listOf(
                "https://static.nike.com/a/images/c_limit",
                "https://static.nike.com/a/images/c_limit",
                "https://static.nike.com/a/images/c_limit",
                "https://static.nike.com/a/images/c_limit",
            ),
        category = "신발",
        quality = "상",
        time = "1일전",
        title = "나이키 운동화",
        viewCount = "100",
        price = 10000,
        userImageUri = "https://static.nike.com/a/images/c_limit",
        userName = "곰 발바닥",
        rate = 4.5f,
        region = "동대문구",
        content =
            "답글 소중한 리뷰 남겨주셔서 정말 감사드립니다\uD83D\uDE0C\uD83D\uDC9F\n" +
                "편안한 와이드 실루엣에 핀턱 디테일까지 들어가 있어서\n" +
                "스웻팬츠지만 힙~!하고 멋스럽게~ 연출 가능한 제품이죠~\uD83D\uDC4D\n" +
                "탄탄한 원단이라서 변형이 적기 때문에 오래오래 착용하시기 너무 좋으실거라 생각합니다\uD83D\uDE0A\uD83C\uDF40\n" +
                "구매하신 제품 예쁘게 입어주시고, 또 놀러 와주시면 정말 감사하겠습니다\uD83D\uDC9C",
    )

@Composable
fun ShoppingDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    shoppingDetailData: ShoppingDetailData,
) {
    Box(modifier.fillMaxSize()) {
        DetailContent(navController, shoppingDetailData)
        Row(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(Paddings.xlarge, 40.dp),
        ) {
            BottomButtonSection(navController)
        }
    }
}

@Composable
fun DetailContent(
    navController: NavHostController,
    shoppingDetailData: ShoppingDetailData,
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        ProductImageSection(shoppingDetailData, navController)
        ProductContentSection(shoppingDetailData)
    }
}

@Composable
@Preview(showBackground = true)
fun ShoppingDetailScreenPreview() {
    ShoppingDetailScreen(
        navController = rememberNavController(),
        shoppingDetailData = defaultShoppingDetailData,
    )
}
