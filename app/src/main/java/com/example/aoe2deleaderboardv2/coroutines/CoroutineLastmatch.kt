package com.example.aoe2deleaderboardv2.coroutines

import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineRequestRest
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineHandler
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineResult
import com.example.aoe2deleaderboardv2.dto.MatchInfo
import com.example.aoe2deleaderboardv2.rest.config.RestEndpoints
import org.json.JSONException
import org.json.JSONObject

class CoroutineLastmatch(
    uiHandler: CoroutineHandler<MatchInfo>,
    profileId: Int
) : CoroutineRequestRest<MatchInfo>(RestEndpoints.LAST_MATCH.url, uiHandler) {

    init {
        restClient.addParam("profile_id", profileId)
        restClient.addParam("game", "aoe2de")
    }

    override suspend fun run(): CoroutineResult<MatchInfo> {
        return try {
            val response = JSONObject(this.restClient.get() ?: throw JSONException("Failed to get last match JSON"))
            CoroutineResult(true, "Last match successfully parsed", MatchInfo(response))
        } catch (jsonException: JSONException) {
            CoroutineResult(false, jsonException.toString())
        }
    }

}