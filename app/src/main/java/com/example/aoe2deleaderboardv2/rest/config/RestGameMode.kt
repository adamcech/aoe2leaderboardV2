package com.example.aoe2deleaderboardv2.rest.config

enum class RestGameMode (val id: Int, val type: String) {

    UNRANKED(0, "Unranked"),
    ONE_VS_ONE_DEATHMATCH(1, "1v1 Deathmatch"),
    TEAM_DEATHMATCH(2, "Team Deathmatch"),
    ONE_VS_ONE_RANDOM_MAP(3, "1v1 Random"),
    TEAM_RANDOM_MAP(4, "Team random");

    companion object {
        fun getAll() = listOf(UNRANKED, ONE_VS_ONE_DEATHMATCH, TEAM_DEATHMATCH, ONE_VS_ONE_RANDOM_MAP, TEAM_RANDOM_MAP)

        fun getByLeaderboardId(id: Int) : RestGameMode? {
            val enums = values()

            for (e in enums) {
                if (e.id == id) {
                    return e
                }
            }

            return null
        }
    }
}