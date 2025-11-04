package com.example.a25_10_ynov_android

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.example.a25_10_ynov_android.ui.screens.SearchScreen
import com.example.a25_10_ynov_android.viewmodel.MainViewModelTest
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    //Création de l'environnement  de Test pour Compose
    @get:Rule
    val composeTestRule = createComposeRule()

    //Si besoin du context pour les resources de l'application par exemple
    //val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testErrorStateDisplayed() {
        //On charge un ViewModel configuré dans un état
        val viewModel = MainViewModelTest().apply { errorState() }
        //On charge le composable à tester
        composeTestRule.setContent {
            SearchScreen(mainViewModel = viewModel)
        }

        // Message d'erreur visible
        composeTestRule.onNodeWithText(MainViewModelTest.ERROR_MESSAGE_TEST).assertIsDisplayed()

        // Vérifie que l'indicateur de chargement n'est pas visible
        val semantic = SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo)
        assertTrue(composeTestRule.onAllNodes(semantic, true).fetchSemanticsNodes().isEmpty())

        //La liste n'affiche aucun élément
        composeTestRule.onAllNodesWithText("Nice", substring = true, ignoreCase = true).assertCountEquals(viewModel.dataList.value.size)
    }

    @Test
    fun testLoadingStateDisplayed() {
        //TODO
    }

    @Test
    fun testSuccessStateDisplayed() {
        //TODO
    }

    @Test
    fun testSearchWeathersSuccess() {
        //TODO
    }
}