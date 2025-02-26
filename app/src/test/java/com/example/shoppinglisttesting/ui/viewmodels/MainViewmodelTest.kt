package com.example.shoppinglisttesting.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppinglisttesting.MainCoroutineRule
import com.example.shoppinglisttesting.data.ShoppingItem
import com.example.shoppinglisttesting.repo.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewmodelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewmodel
    private lateinit var fakeRepository: FakeShoppingRepository



    val tempSchedule = TestCoroutineScheduler()
    val dispatcher = UnconfinedTestDispatcher(tempSchedule)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        fakeRepository = FakeShoppingRepository()
        viewModel = MainViewmodel(fakeRepository,dispatcher)
    }

    @Test
    fun `addItem should insert shopping item into repository`() = runTest {
        // Arrange
        val shoppingItem = ShoppingItem(
            uid = 1,
            image = "image_url",
            name = "Test Item",
            amount = 2,
            pricePerItem = 10.0
        )

        val results = mutableListOf<List<ShoppingItem>>()
        backgroundScope.launch(dispatcher) {
            viewModel.shoppingItemList.toList(results)
        }

        viewModel.AddItem(shoppingItem)
        assertThat(results.last()).contains(shoppingItem)
    }

    @Test
    fun `shopping name two large not add to list`() = runTest {
        val shoppingItem = ShoppingItem(
            uid = 1,
            image = "image_url",
            name = "TestsssssssssssssssTestsssssssssssssssTestsssssssssssssss",
            amount = 2,
            pricePerItem = 10.0
        )

        val results = mutableListOf<List<ShoppingItem>>()
        backgroundScope.launch(dispatcher) {
            viewModel.shoppingItemList.toList(results)
        }

        viewModel.AddItem(shoppingItem)
        results.forEach {
            assertThat(it).doesNotContain(shoppingItem)
        }
    }

    @Test
    fun `shopping item price two large not add to list`() = runTest {
        val shoppingItem = ShoppingItem(
            uid = 1,
            image = "image_url",
            name = "Tests",
            amount = 2,
            pricePerItem = 100000.0
        )

        val results = mutableListOf<List<ShoppingItem>>()
        backgroundScope.launch(dispatcher) {
            viewModel.shoppingItemList.toList(results)
        }

        viewModel.AddItem(shoppingItem)
        results.forEach {
            assertThat(it).doesNotContain(shoppingItem)
        }
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

}