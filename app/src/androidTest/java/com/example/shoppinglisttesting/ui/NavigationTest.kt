package com.example.shoppinglisttesting.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso
import com.example.shoppinglisttesting.repo.FakeShoppingRepository
import com.example.shoppinglisttesting.ui.screens.MainActivity
import com.example.shoppinglisttesting.ui.viewmodels.MainViewmodel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {


    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    lateinit var navController : TestNavHostController



    private lateinit var viewModel: MainViewmodel
    private lateinit var fakeRepository: FakeShoppingRepository


    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        composeTestRule.activity.setContent{
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            fakeRepository = FakeShoppingRepository()
            viewModel = MainViewmodel(fakeRepository)
            AppNavHost(navController = navController, mainViewmodel = viewModel)
        }
    }

    @Test
    fun NavHost_verifyHomeScreenStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Home Screen")
            .assertIsDisplayed()
    }

    @Test
    fun navhost_clickAddNavigateToAddItemScreen(){
        composeTestRule
            .onNodeWithContentDescription("AddItem")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("AddItem Screen")
            .assertIsDisplayed()
    }


    @Test
    fun navhost_check_navigation_for_add_image_screen(){
        composeTestRule
            .onNodeWithContentDescription("AddItem")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Shopping Item Image")
            .performClick()


        composeTestRule
            .onNodeWithContentDescription("AddImage Screen")
            .assertIsDisplayed()
    }


    @Test
    fun Navhost_check_navigation_for_add_image_screen_and_back_to_addItem(){
        composeTestRule
            .onNodeWithContentDescription("AddItem")
            .performClick()


        composeTestRule
            .onNodeWithContentDescription("Shopping Item Image")
            .performClick()


        composeTestRule
            .onNodeWithContentDescription("AddImage Screen")
            .assertIsDisplayed()

        composeTestRule.waitForIdle()

        Espresso.pressBack()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription("Shopping Item Image")
            .performClick()
    }


}