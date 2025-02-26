package com.example.shoppinglisttesting.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shoppinglisttesting.data.ShoppingItem
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@HiltAndroidTest
class DaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingDatabase
    lateinit var dao: Dao


    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertTest() = runTest {
        val shoppingItem = ShoppingItem(
            1,
            "url",
            "banana",
            1,
            1.0
        )
        dao.insert(shoppingItem)

        val allShoppingItem = dao.getAll().first()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteTest() = runTest {
        val shoppingItem = ShoppingItem(
            1,
            "url",
            "banana",
            1,
            1.0
        )

        dao.insert(shoppingItem)
        dao.delete(shoppingItem)

        val list = dao.getAll().first()

        assertThat(list).doesNotContain(shoppingItem)
    }

    @Test
    fun getTotalPrice() = runTest {
        val shoppingItem = listOf(
            ShoppingItem(
                1,
                "url",
                "banana",
                1,
                1.0
            ),
            ShoppingItem(
                2,
                "url",
                "banana",
                3,
                5.0
            )
        )

        val sum = shoppingItem.sumOf {
            (it.amount * it.pricePerItem).toInt()
        }

        shoppingItem.forEach {
            dao.insert(it)
        }

        val totalSum = dao.getTotalPrice().first()

        assertThat(totalSum).isEqualTo(sum)
    }

    @Test
    fun getAllTest() = runTest {
        val shoppingItem = listOf(
            ShoppingItem(
                1,
                "url",
                "banana",
                1,
                1.0
            ),
            ShoppingItem(
                2,
                "url",
                "banana",
                3,
                5.0
            )
        ).sortedByDescending { it.uid }


        shoppingItem.forEach {
            dao.insert(it)
        }

        val list = dao.getAll().first()

        assertThat(list).isEqualTo(shoppingItem)
    }

}