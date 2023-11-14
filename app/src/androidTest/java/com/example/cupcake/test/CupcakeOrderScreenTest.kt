package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.R
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    // Test StartOrderScreen content
    @Test
    fun startOrderScreen_verifyContent() {
        // Launch StartOrderScreen
        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onQuantitySelected = {})
        }
        // Test all the quantity options are displayed on the screen
        DataSource.quantityOptions.forEach {
            composeTestRule.onNodeWithStringId(it.first).assertIsDisplayed()
        }
    }

    // Test SelectOptionScreen content
    @Test
    fun selectOptionScreen_verifyContent() {
        // Given list of options
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        // Given subtotal
        val subtotal = "$100"
        // Load SelectOptionScreen with given variables
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }
        // Test all the options are displayed on the screen
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }
        // Test the subtotal displayed as given
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal_price, subtotal
            )
        ).assertIsDisplayed()
        // Test Next Button is enabled
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
        // Test Next button is enabled when an option is selected
        composeTestRule.onNodeWithStringId(R.string.vanilla).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    // Test Summary content
    @Test
    fun summaryScreen_verifyContent() {
        // Given order states for SummaryScreen
        val orderState = OrderUiState(
            quantity = 6,
            flavor = "vanilla",
            date = "Tue Nov 23",
            price = "$50",
            pickupOptions = listOf()
        )
        // Launch SummaryScreen with given state
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = orderState,
                onSendButtonClicked = { _, _ ->  },
                onCancelButtonClicked = {})
        }
        // Test all the order states shown
        composeTestRule.onNodeWithText(orderState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderState.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal_price,
                orderState.price
            )
        ).assertIsDisplayed()
    }
}