import com.swapit.oopswap.R
import com.swapit.oopswap.data.datasource.local.model.alert.AlertType

fun AlertType.toIconResId(): Int =
    when (this) {
        AlertType.CHAT -> R.drawable.ic_chat
        AlertType.REQUESTED,
        AlertType.ACCEPTED,
        AlertType.REJECTED,
        AlertType.COMPLETED,
        -> R.drawable.ic_shopping_bag
        AlertType.REVIEW -> R.drawable.ic_pencil
    }
