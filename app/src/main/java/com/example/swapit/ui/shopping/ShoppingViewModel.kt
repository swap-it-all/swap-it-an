import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.data.datasource.local.model.bottomsheet.SortOption
import com.example.swapit.data.datasource.local.model.post.CategoryOption
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = mutableStateOf<List<ShoppingProduct>>(emptyList())
    val products: List<ShoppingProduct> get() = _products.value
    val selectedOption = mutableStateOf(SortOption.POPULAR)
    val selectedCategory = mutableStateOf(emptyList<CategoryOption>())
    val searchKeyword = mutableStateOf("")
    val recentKeyword = mutableStateOf(emptyList<String>())

    fun writeSearch(keyword: String) {
        searchKeyword.value = keyword
    }

    fun search() {
        if (!recentKeyword.value.contains(searchKeyword.value) || searchKeyword.value.isNotEmpty()) {
            recentKeyword.value += searchKeyword.value
        }
        fetchProducts()
    }

    fun selectOption(option: SortOption) {
        selectedOption.value = option
        fetchProducts()
    }

    fun selectCategory(category: CategoryOption) {
        if (selectedCategory.value.contains(category)) {
            selectedCategory.value = selectedCategory.value.filter { it != category }
        } else {
            selectedCategory.value += category
        }
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _products.value =
                repository.shoppingCardProducts(
                    cursorId = null,
                    createdAt = null,
                    cursorValue = null,
                    sortBy = selectedOption.value.key,
                    keyword = searchKeyword.value,
                    categoryIds = selectedCategory.value.map { it.id },
                )
        }
    }

    init {
        viewModelScope.launch {
            _products.value =
                repository.shoppingCardProducts(
                    cursorId = null,
                    createdAt = null,
                    cursorValue = null,
                    sortBy = selectedOption.value.key,
                    keyword = null,
                    categoryIds = selectedCategory.value.map { it.id },
                )
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

        fun factory(repository: ProductRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ShoppingViewModel(
                    repository = repository,
                )
            }
    }
}
