package com.example.aoe2deleaderboardv2.dto

import org.json.JSONObject
import java.io.Serializable

class MatchPlayer(json: JSONObject) : Serializable {

    val profileId: Int = json.optInt("profile_id")

    val name: String = json.optString("name")
    private val _civ: Int = json.optInt("civ")
    val isWinner: Boolean? = if (json.isNull("won")) null else json.optBoolean("won")

    fun civ(strings: StringsAOE2?): String {
        return strings?.civNames?.get(_civ) ?: "Unknown"
    }

    fun inProgress(): Boolean {
        return isWinner == null
    }

    override fun toString(): String {
        return "MatchPlayer(name='$name', _civ=$_civ, isWinner=$isWinner)"
    }

}