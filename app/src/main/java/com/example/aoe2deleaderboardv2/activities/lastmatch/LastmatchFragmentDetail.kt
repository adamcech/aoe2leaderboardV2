package com.example.aoe2deleaderboardv2.activities.lastmatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aoe2deleaderboardv2.R
import com.example.aoe2deleaderboardv2.activities.OnlineActivity
import com.example.aoe2deleaderboardv2.coroutines.CoroutineLeaderboard
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineHandler
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineResult
import com.example.aoe2deleaderboardv2.dto.MatchInfo
import com.example.aoe2deleaderboardv2.dto.MatchPlayer
import com.example.aoe2deleaderboardv2.dto.Player
import com.example.aoe2deleaderboardv2.rest.config.RestGameMode

@SuppressLint("SetTextI18n")
class LastmatchFragmentDetail(
    private val matchInfo: MatchInfo,
    private val matchPlayer: MatchPlayer
) : Fragment(R.layout.lastmatch_detail), OnlineActivity {

    private var tvDetail: TextView? = null
    private var tvMsg: TextView? = null

    private var tvRank: TextView? = null
    private var tvRating: TextView? = null
    private var tvWins: TextView? = null
    private var tvGames: TextView? = null
    private var tvWinRatio: TextView? = null

    private var btnBack: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        tvDetail = view?.findViewById(R.id.tvPlayerDetail)
        tvMsg = view?.findViewById(R.id.tvMsg)

        tvRank = view?.findViewById(R.id.tvPlayerRank)
        tvRating = view?.findViewById(R.id.tvPlayerRating)
        tvWins = view?.findViewById(R.id.tvPlayerWins)
        tvGames = view?.findViewById(R.id.tvPlayerGames)
        tvWinRatio = view?.findViewById(R.id.tvPlayerWinRatio)

        btnBack = view?.findViewById(R.id.btnDetailBack)

        tvDetail?.text = "Player Detail: ${matchPlayer.name}"
        btnBack?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        if (isOnline(context)) {
            val gameMode = RestGameMode.getByLeaderboardId(matchInfo.leaderboardId)
            gameMode?.let { gMode ->
                CoroutineLeaderboard(LeaderboardHandler(), gMode, 1, matchPlayer.profileId).start()
            }
        } else {
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

    private fun setDetail(player: Player) {
        tvRank?.text = "Rank: " + player.rank.toString()
        tvRating?.text = "Rating: " + player.rating.toString()
        tvWins?.text = "Wins: " + player.wins.toString()
        tvGames?.text = "Games: " + player.games.toString()
        tvWinRatio?.text = "Win: " + player.winPercentage.toString() + "%"

        tvMsg?.text = "Mode: " + RestGameMode.getByLeaderboardId(matchInfo.leaderboardId)?.type
    }

    inner class LeaderboardHandler : CoroutineHandler<List<Player>> {
        override fun preRun() {
            tvMsg?.text = "Loading Player Detail..."
        }

        override fun postRun(result: CoroutineResult<List<Player>>) {
            if (result.result?.size == 1) {
                setDetail(result.result[0])
            }
        }
    }

}
