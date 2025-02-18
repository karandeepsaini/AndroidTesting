package com.example.shoppinglisttesting.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttesting.ui.repo.ShoppingRepository
import com.example.shoppinglisttesting.db.Dao
import com.example.shoppinglisttesting.data.ImageItem
import com.example.shoppinglisttesting.data.ShoppingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    private val dao: Dao,
    private val repo: ShoppingRepository,
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

    private val _shoppingItemList = dao.getAll().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )
    val shoppingItemList: StateFlow<List<ShoppingItem>> = _shoppingItemList

    private val _imageList = MutableStateFlow<List<ImageItem>>(emptyList())
    val imageList: StateFlow<List<ImageItem>> = _imageList

    private val _searchItem = MutableStateFlow("")
    val searchItem: StateFlow<String> = _searchItem.asStateFlow();

    private val _totalSum = dao.getTotalPrice().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        0
    )
    val totalSum: StateFlow<Int> = _totalSum


    fun searchItem(search: String) {
        _searchItem.value = search

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(2000L)
                repo.getImage(search).collectLatest {
                    _imageList.emit(it.hits)
                }
            }
        }
    }


    fun updateShoppingItem(item: ShoppingItem) {
        _shoppingItem.value = item
    }

    fun resetShoppingItem() {
        _shoppingItem.value = ShoppingItem(
            uid = 0,
            image = "",
            name = "",
            amount = 0,
            pricePerItem = 0.0
        )
    }


    fun AddItem() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.insert(_shoppingItem.value)
            }
        }
    }

    fun DeleteItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.delete(shoppingItem)
            }
        }
    }


    fun setImage(previewUrl: String) {
        _shoppingItem.value = _shoppingItem.value.copy(image = previewUrl)
    }


}