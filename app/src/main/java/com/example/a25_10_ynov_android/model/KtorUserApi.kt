package com.example.a25_10_ynov_android.model

import android.annotation.SuppressLint
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//Suspend sera expliqué dans le chapitre des coroutines
 suspend fun main()  {
    val user = KtorUserApi.loadRandomUser()
    println("""
        Il s'appelle ${user.name} pour le contacter :
        Phone : ${user.coord?.phone ?: "-"}
        Mail : ${user.coord?.mail ?: "-"}
    """.trimIndent())
    KtorMuseumApi.close()
}

object KtorUserApi {
    private const val API_URL =
        "https://www.amonteiro.fr/api/randomuser"

    //Création et réglage du client
    private val client  = HttpClient {
        install(Logging) {
            //(import io.ktor.client.plugins.logging.Logger)
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        //expectSuccess = true //Exception si code >= 300
        //engine { proxy = ProxyBuilder.http("monproxy:1234") }
    }

    //GET Le JSON reçu sera parser en List<MuseumObject>,
    //Crash si le JSON ne correspond pas
    suspend fun loadRandomUser(): UserBean {
        return client.get(API_URL){
//            headers {
//                append("Authorization", "Bearer YOUR_TOKEN")
//                append("Custom-Header", "CustomValue")
//            }
        }.body()
        //possibilité de typer le body
        //.body<List<MuseumObject>>()
    }

    //POST
    suspend fun postData(newObject: MuseumObject): MuseumObject {
        return client.post(API_URL){
            setBody(newObject)
        }.body()
    }

    //Ferme le Client mais celui ci ne sera plus utilisable. Uniquement pour le main
    fun close() = client.close()

    //Avancés : Exemple avec Flow
    //fun getDataFlow() = flow<List<MuseumObject>> {
    //    emit(client.get(API_URL).body())
    //}
}

//DATA CLASS

//Possible qu'il y ait besoin de cette annotation en fonction du compilateur
@SuppressLint("UnsafeOptInUsageError")
@Serializable //KotlinX impose cette annotation
data class UserBean(
    val name: String,
    val age: Int,
    val coord : CoordBean?
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable //KotlinX impose cette annotation
data class CoordBean(
    val phone: String?,
    val mail: String?,
)