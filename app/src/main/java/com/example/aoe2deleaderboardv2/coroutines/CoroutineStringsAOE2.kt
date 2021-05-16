package com.example.aoe2deleaderboardv2.coroutines

import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineRequestRest
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineHandler
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineResult
import com.example.aoe2deleaderboardv2.dto.StringsAOE2
import com.example.aoe2deleaderboardv2.rest.config.RestEndpoints
import org.json.JSONException
import org.json.JSONObject


class CoroutineStringsAOE2(
    uiHandler: CoroutineHandler<StringsAOE2>
) : CoroutineRequestRest<StringsAOE2>(RestEndpoints.STRINGS.url, uiHandler) {

    override suspend fun run(): CoroutineResult<StringsAOE2> {
        return try {
            val response = JSONObject(this.restClient.get() ?: throw JSONException("Failed to get strings JSON"))
            CoroutineResult(true, "Strings successfully parsed", StringsAOE2(response))
        } catch (jsonException: JSONException) {
            CoroutineResult(false, jsonException.toString())
        }
    }

}