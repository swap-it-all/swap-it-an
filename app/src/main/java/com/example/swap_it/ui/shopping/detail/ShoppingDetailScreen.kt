package com.example.swap_it.ui.shopping.detail

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
import com.example.swap_it.data.model.ShoppingDetailData
import com.example.swap_it.ui.theme.Paddings


val defaultShoppingDetailData = ShoppingDetailData(
    imageUri = listOf(
        "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/59ff827c-5672-4657-9eca-0179dfe206c3/AIR+MAX+DN8.png",
        "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/9c78e4e6-f31b-4479-be66-f2fa71e157e3/W+NIKE+TC+7900.png",
        "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/db17b92b-e81f-4667-9f3d-586b4653df82/W+NIKE+ZOOM+VOMERO+5.png",
        "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/04af6d78-2200-43f8-ba98-07127d500872/NIKE+V2K+RUN.png",
        "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/009de621-5a75-4117-8d11-71bd7997c336/NIKE+CORTEZ.png",
    ),
    category = "신발",
    quality = "상",
    time = "1일전",
    title = "나이키 운동화",
    viewCount = "100",
    price = 10000,
    userImageUri = "https://i.namu.wiki/i/uuF0vlgKZ2uy2_ddA3S_0NIekZTGhf1UvGfEdWgNnN5JOIt1-zPcMCC08f3bqbeGhacloEdSi_rHV9V0caQBgMcdd88R6meypgpT7EJMzfwzf36VSxYQvylW6WW3xpNq5DaHScenfJ2wCd83cTzSbQ.webp",
    userName = "곰 발바닥",
    rate = 4.5f,
    region = "동대문구",
    content = "답글 소중한 리뷰 남겨주셔서 정말 감사드립니다\uD83D\uDE0C\uD83D\uDC9F\n" +
            "편안한 와이드 실루엣에 핀턱 디테일까지 들어가 있어서\n" +
            "스웻팬츠지만 힙~!하고 멋스럽게~ 연출 가능한 제품이죠~\uD83D\uDC4D\n" +
            "탄탄한 원단이라서 변형이 적기 때문에 오래오래 착용하시기 너무 좋으실거라 생각합니다\uD83D\uDE0A\uD83C\uDF40\n" +
            "구매하신 제품 예쁘게 입어주시고, 또 놀러 와주시면 정말 감사하겠습니다\uD83D\uDC9C"
)

@Composable
fun ShoppingDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    shoppingDetailData: ShoppingDetailData
) {
    Box(modifier.fillMaxSize()) {
        DetailContent(modifier, navController, shoppingDetailData)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(Paddings.xlarge, 40.dp)
        ) {
            BottomButtonSection(modifier)
        }

    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    shoppingDetailData: ShoppingDetailData
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        ProductImageSection(modifier, shoppingDetailData, navController)
        ProductContentSection(modifier, shoppingDetailData)
    }
}

@Composable
@Preview(showBackground = true)
fun ShoppingDetailScreenPreview() {
    ShoppingDetailScreen(
        navController = rememberNavController(),
        shoppingDetailData = defaultShoppingDetailData
    )
}