import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapit.data.datasource.remote.dto.response.shopping.Goods
import com.example.swapit.data.datasource.remote.service.ShoppingService
import com.example.swapit.domain.repository.ShoppingRepository
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository) : ViewModel() {
    private val _products = mutableStateOf<List<Goods>>(emptyList())
    val products: List<Goods> get() = _products.value

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
                val response = repository.getShoppingData()
                Log.d("API Response: ",response.toString()) // 디버깅용 로그")
                if (response.success) {
                    _products.value = response.results.goods.map { it }
                }
            } catch (e: Exception) {
                // 에러 처리
                println("Error loading products: ${e.message}")
            }
        }
    }
}
