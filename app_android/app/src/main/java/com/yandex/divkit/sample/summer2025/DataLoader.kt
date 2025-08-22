package com.yandex.divkit.sample.summer2025

import com.yandex.div2.DivData
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import java.net.ConnectException

internal object DataLoader {
    private val client by lazy(LazyThreadSafetyMode.PUBLICATION) {
        OkHttpClient()
    }

    suspend fun get(): DivData? {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment("divruntime")
            .build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Accept-Language", "ru")
            .build()

        val response = try {
            client.newCall(request).execute()
        } catch (e: ConnectException) {
            println("Failed to get response: ${e.message}")
            return null
        } catch (e: IOException) {
            println("Network error: ${e.message}")
            return null
        }

        val body = response.body?.string() ?: run {
            println("Failed to get response body")
            return null
        }

        val responseJson = try {
            JSONObject(body)
        } catch (e: JSONException) {
            println("Failed to parse JSON: ${e.message}")
            return null
        }

        return createDivData(responseJson)
    }
}
