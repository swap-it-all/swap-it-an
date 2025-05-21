import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider
import com.swapit.oopswap.data.datasource.local.model.bottomsheet.SortOption
import com.swapit.oopswap.data.datasource.local.model.post.CategoryOption
import com.swapit.oopswap.domain.model.product.Product
import com.swapit.oopswap.domain.repository.ProductRepository
import com.swapit.oopswap.ui.base.BaseViewModel
import com.swapit.oopswap.ui.base.BaseViewModelFactory

class ShoppingViewModel(private val repository: ProductRepository) : BaseViewModel() {
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: List<Product> get() = _products.value
    val selectedOption = mutableStateOf<SortOption?>(null)
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

    fun fetchProducts() {
        safeLaunch {
            val list =
                repository.productCardProducts(
                    cursorId = null,
                    createdAt = null,
                    cursorValue = null,
                    sortBy = (selectedOption.value?.key ?: SortOption.RECENT.key).toString(),
                    keyword = searchKeyword.value,
                    categoryIds = selectedCategory.value.map { it.id },
                )
            _products.value = list
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
