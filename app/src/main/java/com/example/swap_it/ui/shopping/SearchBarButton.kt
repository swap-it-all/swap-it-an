package com.example.swap_it.ui.shopping

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swap_it.R
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Gray6
import com.example.swap_it.ui.theme.SwapitTheme

@Composable
fun SearchBarButton(modifier: Modifier = Modifier,navController: NavHostController) {
    Button(
        modifier = modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Gray6),
        onClick = {
            navController.navigate("Search") {
                navController.graph.startDestinationRoute?.let {
                    popUpTo(it) { saveState = true }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.shopping_search_button_content),
                    modifier = modifier.weight(1f),
                    color = Gray3
                )
                Image(
                    painter = painterResource(R.drawable.ic_search_magnifying),
                    contentDescription = "검색 버튼",
                    colorFilter = ColorFilter.tint(Gray3)
                )
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun SearchBarButtonPreview(){
    SwapitTheme {
        SearchBarButton(Modifier, rememberNavController())
    }
}
