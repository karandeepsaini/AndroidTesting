package com.example.shoppinglisttesting.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttesting.repo.ShoppingRepositoryImpl
import com.example.shoppinglisttesting.data.ImageItem
import com.example.shoppinglisttesting.data.ShoppingItem
import com.example.shoppinglisttesting.other.Status
import com.example.shoppinglisttesting.repo.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    private val repo: ShoppingRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _shoppingItem = MutableStateFlow(
        ShoppingItem(
            uid = 0,
            image = "",
            name = "",
            amount = 0,
            pricePerItem = 0.0
        )
    )
    val shoppingItem: StateFlow<ShoppingItem> = _shoppingItem.asStateFlow()

    val shoppingItemList : StateFlow<List<ShoppingItem>> = repo.observeShoppingItem().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )




    private val _totalSum = repo.getTotalSum().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        0
    )
    val totalSum: StateFlow<Int> = _totalSum

    private val _imageList = MutableStateFlow<List<ImageItem>>(emptyList())
    val imageList: StateFlow<List<ImageItem>> = _imageList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var searchJob: Job? = null

    fun searchItem(search: String) {
        _searchQuery.value = search

        if (searchJob == null || searchJob?.isActive == false) {
            observerSearchQuery()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun observerSearchQuery() {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .flatMapLatest { query ->
                    repo.searchForImage(query)
                        .catch { e ->
                            Timber.e("SEARCH %s ", "ERROR : ${e.message}")
                        }
                }
                .collectLatest { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            Timber.d("Loading images...")
                            setImageList(emptyList())
                        }

                        Status.SUCCESS -> {
                            val items = resource.data?.hits ?: emptyList()
                            setImageList(items)
                            Timber.d("Images loaded successfully!")
                        }

                        Status.ERROR -> {
                            Timber.e("Error loading images: ${resource.message}")
                        }
                    }
                }
        }
    }

    fun setImageList(items: List<ImageItem>) {
        _imageList.value = items
    }


    fun updateShoppingItem(item: ShoppingItem) {
        _shoppingItem.value = item
    }

    fun AddItem(shoppingItem: ShoppingItem) {
        if (shoppingItem.image.isBlank() ||
            shoppingItem.name.isBlank() ||
            shoppingItem.amount <= 0 ||
            shoppingItem.pricePerItem <= 0 ||
            shoppingItem.name.length > 20 ||
            shoppingItem.pricePerItem > 10000
            ) {
            return
        }

        viewModelScope.launch {
            withContext(dispatcher) {
                repo.insertShoppingItem(shoppingItem)
            }
        }
    }

    fun DeleteItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            withContext(dispatcher) {
                repo.deleteShoppingItem(shoppingItem)
            }
        }
    }


    fun setImage(previewUrl: String) {
        _shoppingItem.value = _shoppingItem.value.copy(image = previewUrl)
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }


}