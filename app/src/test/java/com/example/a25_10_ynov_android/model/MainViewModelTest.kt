package com.example.a25_10_ynov_android.model

import com.example.a25_10_ynov_android.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MainViewModelTest {

    //Pour piloter les coroutines
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun loadWeathersNetwork() = runBlocking {

        val viewModel = MainViewModel()

        assertFalse(viewModel.runInProgress.value)
        // Appeler la méthode à tester
        val job = viewModel.loadWeathers("Paris")
        assertTrue(viewModel.runInProgress.value)
        job.join()
        assertFalse(viewModel.runInProgress.value)
        assertTrue(viewModel.dataList.value.isNotEmpty())
        assertTrue(viewModel.dataList.value.count { it.name.contains("Paris", true) } > 0 )

    }

    @Test
    fun loadWeathersMock() = runTest(testDispatcher) {

        mockkObject(KtorWeatherApi)

        coEvery { KtorWeatherApi.loadWeathers("Paris") }.returns(getParisFakeResult())


        val viewModel = MainViewModel(testDispatcher)

        assertFalse(viewModel.runInProgress.value)

        // Appeler la méthode à tester
        viewModel.loadWeathers("Paris")

        assertTrue(viewModel.runInProgress.value)

        advanceUntilIdle()

        assertFalse(viewModel.runInProgress.value)
        //Mock
        coVerify { KtorWeatherApi.loadWeathers("Paris") }

        confirmVerified(KtorWeatherApi)
        //Que le 1 er élément c'est bien Paris et le même id
        assertEquals("La ville n'est pas Paris", getParisFakeResult().first().name, viewModel.dataList.value.first().name)
        assertEquals("L'id n'est pas identique", getParisFakeResult().first().id, viewModel.dataList.value.first().id)

    }

    fun getParisFakeResult() = arrayListOf(
        WeatherBean(
            id = 1,
            name = "Paris",
            main = TempBean(temp = 20.0),
            wind = WindBean(speed = 5.0),
            weather = listOf(DescriptionBean(description = "Ensoleillé", icon = "01d"))
        )
    )
}