package com.example.aoe2deleaderboardv2.dto

import org.json.JSONObject
import java.io.Serializable
import kotlin.math.roundToInt

class Player(json: JSONObject) : Serializable {

    val profileId: Int = json.optInt("profile_id")
    val rank: Int = json.optInt("rank")
    val name: String = json.optString("name")
    val rating: Int = json.optInt("rating")
    val wins: Int = json.optInt("wins")
    val games: Int = json.optInt("games")


    val winPercentage: Int
        get() = ((wins.toFloat() / games.toFloat()) * 100).roundToInt()

    override fun toString(): String {
        return "Player(profileId=$profileId, rank=$rank, name=$name, rating=$rating, winPercentage=$winPercentage)"
    }
}