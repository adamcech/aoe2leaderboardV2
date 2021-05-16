package com.example.aoe2deleaderboardv2.coroutines

import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineRequestRest
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineHandler
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineResult
import com.example.aoe2deleaderboardv2.dto.Player
import com.example.aoe2deleaderboardv2.rest.config.RestEndpoints
import com.example.aoe2deleaderboardv2.rest.config.RestGameMode
import org.json.JSONException
import org.json.JSONObject

class CoroutineLeaderboard(
    uiHandler: CoroutineHandler<List<Player>>,
    restGameMode: RestGameMode,
    count: Int,
    profileId: Int? = null
) : CoroutineRequestRest<List<Player>>(RestEndpoints.LEADERBOARD.url, uiHandler) {

    init {
        restClient.addParam("leaderboard_id", restGameMode.id)
        restClient.addParam("count", count)

        profileId?.let {
            restClient.addParam("profile_id", profileId)
        }
    }

    override suspend fun run(): CoroutineResult<List<Player>> {
        return try {
            val players = mutableListOf<Player>()
            val response = JSONObject(this.restClient.get() ?: throw JSONException("Failed to get leaderboard JSON"))

            response.optJSONArray("leaderboard")?.let { leaderboard ->
                for (i in 0 until leaderboard.length()) {
                    leaderboard.optJSONObject(i)?.let { player ->
                        players.add(Player(player))
                    }
                }
            }

            CoroutineResult(true, "Leaderboard successfully parsed", players)
        } catch (jsonException: JSONException) {
            CoroutineResult(false, jsonException.toString())
        }
    }

}