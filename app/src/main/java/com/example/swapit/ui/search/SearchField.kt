import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swapit.R
import com.example.swapit.ui.theme.Black
import com.example.swapit.ui.theme.Gray3
import com.example.swapit.ui.theme.Gray6
import com.example.swapit.ui.theme.Typography

@Composable
fun SearchField(
    searchTerm: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
) {
    BasicTextField(
        value = searchTerm,
        onValueChange = onValueChange,
        textStyle = Typography.bodyMedium.copy(color = Black),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(Gray6, shape = RoundedCornerShape(32.dp))
            .padding(horizontal = 16.dp),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (searchTerm.isEmpty()) {
                        Text(
                            stringResource(R.string.shopping_search_button_content),
                            style = Typography.bodyMedium,
                            color = Gray3
                        )
                    }
                    innerTextField()
                }
                Image(
                    painter = painterResource(R.drawable.ic_search_magnifying),
                    contentDescription = stringResource(R.string.search_search_button),
                    colorFilter = ColorFilter.tint(Gray3)
                )
            }
        }
    )
}
