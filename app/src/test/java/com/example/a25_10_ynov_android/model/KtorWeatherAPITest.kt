package com.example.a25_10_ynov_android.model

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Test

class KtorWeatherAPITest {

    @Test(expected = Exception::class)
    fun loadWeathersEmptyStringv2() = runBlocking<Unit> {
            KtorWeatherApi.loadWeathers("")
    }

    @Test
    fun loadWeathersEmptyString() = runBlocking<Unit> {
        try {
            KtorWeatherApi.loadWeathers("")
            fail("Une exception aurait du avoir lieu")
        }
        catch(e:Exception){

        }

    }


    @Test
    fun loadWeatherNiceTest() = runBlocking<Unit> {
        val res = KtorWeatherApi.loadWeathers("Nice")

        assertTrue("La liste doit contenir au moins 1 élément",  res.isNotEmpty())

        res.forEach {
            assertTrue("Le nom doit contenir Nice",  it.name.contains("Nice", true))
            assertTrue("La température n'est pas entre -40 et 60°", it.main.temp in -40.0..60.0)
            assertTrue("La description est vide", it.weather.isNotEmpty())
            assertTrue("Il n'y a pas d'icône", it.weather[0].icon.isNotBlank())
        }
    }
}