package com.kiwi.ui_collection

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.kiwi.common_ui_compose.KiwisBarTheme
import org.junit.Rule
import org.junit.Test

class CollectionTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        composeTestRule.setContent {
            KiwisBarTheme {
            // TODO:
            }
        }
        composeTestRule.onRoot(useUnmergedTree = false).printToLog("currentLabelExists")

        composeTestRule.onNodeWithText("Onboarding", substring = true).assertDoesNotExist()
    }
}