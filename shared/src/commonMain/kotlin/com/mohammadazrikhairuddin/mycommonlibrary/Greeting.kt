package com.mohammadazrikhairuddin.mycommonlibrary

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    private val client = HttpClient()

    suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun mockRequestAPI(
        onSuccess: (List<MockResponse>) -> Unit,
        onError: (String) -> Unit
    ) {
        val response = httpClient.get("https://api.opendota.com/api/heroes")

        if (response.status.value in 200..299) {
            onSuccess(response.body())
        } else {
            onError("Alamak....")
        }
    }
}

@Serializable
data class MockResponse(val id: Int, val name: String)