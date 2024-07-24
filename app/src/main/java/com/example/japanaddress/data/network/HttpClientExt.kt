package com.example.japanaddress.data.network

import com.example.japanaddress.core.Config
import com.example.japanaddress.util.DataError
import com.example.japanaddress.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String,Any?> = mapOf()
): Result<Response , DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T,DataError.Network> {
    val response = try {
        execute()
    }catch (e: UnresolvedAddressException){
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    }catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    }catch (e: Exception){
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network> {
    return when(response.status.value){
        in 200..299 -> {
            val responseBody: String = response.bodyAsText()
            Result.Success(Json.decodeFromString(responseBody))
        }
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}


fun constructRoute(route: String): String {
    return when {
        route.contains(Config.BASE_URL) -> route
        route.startsWith("/") -> Config.BASE_URL + route
        else -> Config.BASE_URL + "/$route"
    }
}