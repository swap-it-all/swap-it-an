import com.swapit.oopswap.R
import com.swapit.oopswap.data.datasource.local.model.alert.AlertType

fun AlertType.toIconResId(): Int =
    when (this) {
        AlertType.CHAT -> R.drawable.ic_chat_bg_black
        AlertType.REQUESTED,
        AlertType.ACCEPTED,
        AlertType.REJECTED,
        AlertType.COMPLETED,
        -> R.drawable.ic_shopping_bag_bg_black
        AlertType.REVIEW -> R.drawable.ic_pencil_bg_black
    }
