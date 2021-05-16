package com.example.aoe2deleaderboardv2.rest

import com.loopj.android.http.SyncHttpClient

class RestClient(
    private val url: String
) {

    private val client = SyncHttpClient()
    private val params = mutableListOf<ClientParams>()

    fun addParam(key: String, value: Int) =
        this.addParam(key, value.toString())

    fun addParam(key: String, value: String) =
        params.add(ClientParams(key, value))

    fun get(): String? {
        val handler = JsonHandler()
        client.get(generateUrl(), handler)
        return handler.response
    }

    private fun generateUrl(): String {
        var str = "${url}?"

        params.forEach { p ->
            str += "${p.key}=${p.value}&"
        }

        return str
    }

    private data class ClientParams (
        val key: String,
        val value: String
    )
}