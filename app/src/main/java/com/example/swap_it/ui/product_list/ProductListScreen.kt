package com.example.swap_it.ui.product_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swap_it.R
import com.example.swap_it.ui.theme.SwapitTheme

class ProductListScreen {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ProductScreen(modifier: Modifier = Modifier) {
        Scaffold (
            topBar = {
                TopAppBarCustom()
            }
        ){
            Surface(modifier = modifier) {
                Text(text = "Product Screen")
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarCustom(modifier: Modifier = Modifier) {
        TopAppBar(
            title = { Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "로고",
                modifier = modifier.size(32.dp),
            ) },
            actions = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = "알림"
                    )
                }
            }
        )

    }

    @Preview(showBackground = true)
    @Composable
    fun ProductScreenPreview() {
        SwapitTheme {
            ProductScreen()
        }
    }
}