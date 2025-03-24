package com.swapit.company.ui.shopping.detail.report

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swapit.company.ui.alert.CancelButton
import com.swapit.company.ui.component.DefaultButton
import com.swapit.company.ui.theme.BackgroundColor
import com.swapit.company.ui.theme.Black
import com.swapit.company.ui.theme.Gray4
import com.swapit.company.ui.theme.Gray6
import com.swapit.company.ui.theme.Paddings
import com.swapit.company.ui.theme.Typography

@Composable
fun ReportScreen(
    navController: NavHostController,
    goodsId: String,
    reportViewModel: ReportViewModel,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { ReportAppBar(navController) },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(BackgroundColor),
        ) {
            Text(
                "신고 사유",
                style = Typography.titleLarge,
                modifier = Modifier.padding(Paddings.xlarge),
            )
            ReportField(
                message = reportViewModel.reportMessage.value,
                onValueChange = { reportViewModel.reportMessage.value = it },
            )
            Spacer(modifier = Modifier.padding(Paddings.largeExtra))
            DefaultButton(
                text = "신고하기",
                enabled = reportViewModel.reportMessage.value.isNotEmpty(),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Paddings.xlarge),
                onClick = {
                    reportViewModel.reportProduct(goodsId) { success ->
                        if (success) {
                            Toast.makeText(context, "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "신고에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    navController.navigateUp()
                },
            )
        }
    }
}

@Composable
fun ReportField(
    message: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
) {
    BasicTextField(
        value = message,
        onValueChange = onValueChange,
        textStyle = Typography.bodyMedium.copy(color = Black),
        minLines = 5,
        maxLines = 10,
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send,
            ),
        modifier =
            modifier
                .heightIn(min = 32.dp)
                .background(Gray6, shape = RoundedCornerShape(32.dp))
                .padding(horizontal = 16.dp),
        decorationBox = { innerTextField ->

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (message.isEmpty()) {
                        Text(
                            "신고 사유를 입력해 주세요.",
                            style = Typography.bodySmall,
                            color = Gray4,
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportAppBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = {
            CancelButton(
                modifier = Modifier.padding(start = Paddings.xlarge),
                navController = navController,
            )
        },
        title = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) { Text("신고하기") }
        },
        actions = {
            Spacer(
                modifier =
                    Modifier
                        .size(24.dp)
                        .padding(end = Paddings.xlarge),
            )
        },
        colors =
            TopAppBarColors(
                containerColor = BackgroundColor,
                navigationIconContentColor = BackgroundColor,
                actionIconContentColor = BackgroundColor,
                scrolledContainerColor = BackgroundColor,
                titleContentColor = BackgroundColor,
            ),
    )
}
