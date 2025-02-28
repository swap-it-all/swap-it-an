import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.repository.ShoppingRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository) : ViewModel() {
    private val _products = mutableStateOf<List<ShoppingProduct>>(emptyList())
    val products: List<ShoppingProduct> get() = _products.value

    init {
        viewModelScope.launch {
            _products.value = repository.shoppingCardProducts()
        }
    }

    var bottomSheet = mutableStateOf(false)

    fun dismissBottomSheet() {
        bottomSheet.value = false
    }

    fun showBottomSheet() {
        bottomSheet.value = true
    }

    companion object {
        private const val TAG = "ShoppingViewModel"

        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ShoppingViewModel(
                    repository = repository
                )
            }
    }
}
