package com.example.a25_10_ynov_android.viewmodel

import com.example.a25_10_ynov_android.model.DescriptionBean
import com.example.a25_10_ynov_android.model.TempBean
import com.example.a25_10_ynov_android.model.WeatherBean
import com.example.a25_10_ynov_android.model.WindBean

class MainViewModelTest : MainViewModel() {

    companion object {
        const val ERROR_MESSAGE_TEST = "Une erreur est survenue"
    }

    //Méthode pour définir un état particulier à tester
    fun errorState() {
        runInProgress.value = false
        errorMessage.value = ERROR_MESSAGE_TEST
        dataList.value = emptyList()
    }

    fun loadingState() {
        runInProgress.value = true
        errorMessage.value = ""
        dataList.value = emptyList()
    }

    fun successState() {
        runInProgress.value = false
        errorMessage.value = ""
        dataList.value = fakeList()
    }

    fun fakeList(cityName :String = "Nice") = listOf(
        WeatherBean(
            id = 1,
            name = "$cityName 1",
            main = TempBean(temp = 18.5),
            weather = listOf(
                DescriptionBean(description = "ciel dégagé", icon = "01d")
            ),
            wind = WindBean(speed = 5.0)
        ),
        WeatherBean(
            id = 2,
            name = "$cityName 2",
            main = TempBean(temp = 22.3),
            weather = listOf(
                DescriptionBean(description = "partiellement nuageux", icon = "02d")
            ),
            wind = WindBean(speed = 3.2)
        )
    )

}