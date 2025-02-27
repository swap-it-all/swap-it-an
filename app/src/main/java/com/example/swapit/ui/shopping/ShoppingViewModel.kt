import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.toDomainModel
import com.example.swapit.domain.repository.ShoppingRepository
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository) : ViewModel() {
    private val _products = mutableStateOf<List<ShoppingProduct>>(emptyList())
    val products: List<ShoppingProduct> get() = _products.value

    private val _productCount = mutableIntStateOf(0)
    val productCount get() = _productCount.intValue

    var bottomSheet = mutableStateOf(false)

    fun dismissBottomSheet() {
        bottomSheet.value = false
    }

    fun showBottomSheet() {
        bottomSheet.value = true
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getShoppingResponse()
                val responseResult = repository.getShoppingResults(response)
                Log.d("ShoppingData", "Response: $response")
                if (response.success) {
                    _products.value = responseResult.goodsList.map { it.toDomainModel() }
                    _productCount.intValue = responseResult.count
                    Log.d("ShoppingCountData", "Loaded ${_productCount.intValue} products")
                } else {
                    Log.e("ShoppingData", "API call failed")
                }
            } catch (e: Exception) {
                Log.e("ShoppingViewModel", "Error loading products", e)
            }
        }
    }
}
