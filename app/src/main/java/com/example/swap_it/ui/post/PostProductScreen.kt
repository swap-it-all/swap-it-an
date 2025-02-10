package com.example.swap_it.ui.post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.toCoilUri
import com.example.swap_it.R
import com.example.swap_it.data.datasource.local.model.post.CategoryOption
import com.example.swap_it.data.datasource.local.model.post.QualityOption
import com.example.swap_it.ui.component.DefaultButton
import com.example.swap_it.ui.post.component.PostProductDescriptionTextField
import com.example.swap_it.ui.post.component.PostProductImagePicker
import com.example.swap_it.ui.post.component.PostProductNameTextField
import com.example.swap_it.ui.post.component.PostProductPriceTextField
import com.example.swap_it.ui.post.component.PostProductSwapLocationTextField
import com.example.swap_it.ui.theme.BackgroundColor
import com.example.swap_it.ui.theme.Gray3
import com.example.swap_it.ui.theme.Paddings
import com.example.swap_it.ui.theme.Shapes
import com.example.swap_it.ui.theme.Typography


@Composable
fun PostProductScreen(
    // todo navigation
    // navController: NavHostController,
    viewModel: PostProductViewModel,
) {
    val selectedImageUris by rememberUpdatedState(viewModel.selectedImageUris)

    val multiplePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            viewModel.multipleImages(uris.map { it.toCoilUri() })
        }
    )

    Scaffold(
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = BackgroundColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.xlarge)
                    .verticalScroll(rememberScrollState())
            ) {
                LazyRow {
                    item {
                        PostProductImagePicker(
                            count = selectedImageUris.value.size,
                            maxCount = 10,
                            onClick = {
                                multiplePhotoLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            color = Gray3,
                        )
                    }
                    items(selectedImageUris.value) { uri ->
                        AsyncImage(
                            model = uri,
                            contentScale = ContentScale.Crop,
                            contentDescription = "이미지",
                            modifier = Modifier
                                .size(86.dp)
                                .clip(Shapes.small)
                        )
                        Spacer(modifier = Modifier.padding(Paddings.medium))
                    }
                }

                NameTextField(
                    text = stringResource(R.string.post_product_name),
                    onNameChange = {})
                PriceTextField(
                    text = stringResource(R.string.post_product_price),
                    onPriceChange = {})
                QualityButtons(
                    text = stringResource(R.string.post_product_quality),
                    items = viewModel.allQualitys
                )
                CategoryButtons(
                    text = stringResource(R.string.post_product_category),
                    items = viewModel.allCategories
                )
                LocationTextField(
                    text = stringResource(R.string.post_product_location),
                    onLocationChange = {}
                )
                DescriptionTextField(
                    text = stringResource(R.string.post_product_description),
                    onDescriptionChange = {})
                DefaultButton(
                    text = stringResource(R.string.post_product_post_button),
                    modifier = Modifier.fillMaxWidth()
                ) { }
            }
        }

    }
}


@Composable
fun NameTextField(
    text: String,
    onNameChange: (String) -> Unit,
) {
    Text(
        text = text,
        style = Typography.titleSmall
    )
    Spacer(modifier = Modifier.padding(Paddings.medium))
    PostProductNameTextField(
        text = "",
        onNameChange = onNameChange,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PriceTextField(
    text: String,
    onPriceChange: (String) -> Unit,
) {
    Text(
        text = text,
        style = Typography.titleSmall
    )
    Spacer(modifier = Modifier.padding(Paddings.medium))
    PostProductPriceTextField(
        text = "",
        onPriceChange = onPriceChange,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun QualityButtons(
    text: String,
    items: List<QualityOption>,
) {
    Text(
        text = text,
        style = Typography.titleSmall
    )
    Spacer(modifier = Modifier.padding(Paddings.medium))

    PostProductQuality(items)

    Spacer(modifier = Modifier.padding(Paddings.medium))
}

@Composable
fun CategoryButtons(
    text: String,
    items: List<CategoryOption>,
) {
    Text(
        text = text,
        style = Typography.titleSmall
    )
    Spacer(modifier = Modifier.padding(Paddings.medium))

    PostProductCategory(items)

    Spacer(modifier = Modifier.padding(Paddings.medium))
}

@Composable
fun LocationTextField(
    text: String,
    onLocationChange: (String) -> Unit,
) {
    Text(
        text = text,
        style = Typography.titleSmall
    )
    Spacer(modifier = Modifier.padding(Paddings.medium))

    PostProductSwapLocationTextField(
        value = "",
        onValueChange = onLocationChange,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DescriptionTextField(
    text: String,
    onDescriptionChange: (String) -> Unit,
) {
    Text(
        text = text,
        style = Typography.titleSmall
    )
    Spacer(modifier = Modifier.padding(Paddings.medium))

    PostProductDescriptionTextField(
        text = "",
        onDescriptionChange = onDescriptionChange,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PostProductScreenPreview() {
    PostProductScreen(viewModel = PostProductViewModel())
}
