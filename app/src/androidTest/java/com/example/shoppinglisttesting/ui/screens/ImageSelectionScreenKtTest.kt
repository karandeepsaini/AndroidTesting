package com.example.shoppinglisttesting.ui.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shoppinglisttesting.data.ImageItem
import com.example.shoppinglisttesting.repo.FakeShoppingRepository
import com.example.shoppinglisttesting.ui.AppNavHost
import com.example.shoppinglisttesting.ui.viewmodels.MainViewmodel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@HiltAndroidTest
@ExperimentalTestApi
@RunWith(AndroidJUnit4::class)
class ImageSelectionScreenKtTest {

    @get:Rule(order = 0)
    val hiltrule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var fakeShoppingRepository: FakeShoppingRepository


    lateinit var navController: TestNavHostController

    lateinit var fakeViewModel: MainViewmodel

    val fakephotoList = listOf<ImageItem>(
        ImageItem(id = 1, previewUrl = "https://via.placeholder.com/150"),
        ImageItem(id = 2, previewUrl = "https://via.placeholder.com/150"),
        ImageItem(id = 3, previewUrl = "https://via.placeholder.com/150")
    )


    @Before
    fun setUp() {
        hiltrule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)

            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            fakeShoppingRepository = FakeShoppingRepository()
            fakeViewModel = MainViewmodel(fakeShoppingRepository)


            fakeViewModel.setImageList(fakephotoList)
            AppNavHost(navController = navController, mainViewmodel = fakeViewModel)
        }
    }

    @Test
    fun onSelectingImageScreenPopStack() {

        composeTestRule
            .onNodeWithContentDescription("AddItem")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Shopping Item Image")
            .assertIsDisplayed()
            .performClick()



        composeTestRule.onNodeWithContentDescription(fakephotoList.first().id.toString()).assertIsDisplayed().performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription("Shopping Item Image")
            .assertIsDisplayed()

    }

    @After
    fun tearDown() {
    }


}