package com.example.aoe2deleaderboardv2.dto

import org.json.JSONObject
import java.io.Serializable
import java.time.Instant
import kotlin.math.roundToInt

class MatchInfo(json: JSONObject) : Serializable {

    var matchName: String = ""
    private var started: Long = -1
    private var finished: Long = -1
    private var _mapType: Int = -1
    var matchPlayers: List<MatchPlayer> = listOf()

    var leaderboardId: Int = -1

    init {
        json.optJSONObject("last_match")?.let { lastMatch ->
            matchName = lastMatch.optString("name")
            started = lastMatch.optLong("started")
            finished = lastMatch.optLong("finished")
            _mapType = lastMatch.getInt("map_type")
            leaderboardId = lastMatch.getInt("leaderboard_id")

            val playersList = mutableListOf<MatchPlayer>()
            lastMatch.optJSONArray("players")?.let {players ->
                for (i in 0 until players.length()) {
                    players.optJSONObject(i)?.let { player ->
                        playersList.add(MatchPlayer(player))
                    }
                }
            }
            matchPlayers = playersList.toList()
        }
    }

    fun getLength(): Int {
        val finished = if (inProgress()) Instant.now().epochSecond else this.finished
        return ((finished - started).toFloat() / 60).roundToInt()
    }

    fun mapName(strings: StringsAOE2?): String {
        return strings?.mapNames?.get(_mapType) ?: "Unknown"
    }

    fun inProgress(): Boolean =
        matchPlayers[0].inProgress()

    override fun toString(): String {
        return "MatchInfo(matchName='$matchName', started=$started, finished=$finished, mapType=$_mapType, matchPlayers=$matchPlayers)"
    }

}