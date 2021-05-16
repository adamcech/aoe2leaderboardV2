package com.example.aoe2deleaderboardv2.activities.lastmatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.*
import com.example.aoe2deleaderboardv2.R
import com.example.aoe2deleaderboardv2.activities.OnlineActivity
import com.example.aoe2deleaderboardv2.coroutines.CoroutineLastmatch
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineHandler
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineResult
import com.example.aoe2deleaderboardv2.dto.MatchInfo
import com.example.aoe2deleaderboardv2.dto.MatchPlayer
import com.example.aoe2deleaderboardv2.dto.Player
import com.example.aoe2deleaderboardv2.dto.StringsAOE2
import kotlinx.android.synthetic.main.lastmatch.*
import kotlinx.android.synthetic.main.lastmatch.tvMsg

@SuppressLint("SetTextI18n")
class LastmatchActivity : FragmentActivity(), OnlineActivity {

    private var player: Player? = null
    private var strings: StringsAOE2? = null
    private var matchInfo: MatchInfo? = null

    private val fragmentList = LastmatchFragmentList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentListAndDetail, fragmentList)
                setReorderingAllowed(true)
            }
        }

        setContentView(R.layout.lastmatch)

        // Load intent data
        player = intent.getSerializableExtra("player") as? Player
        strings = intent.getSerializableExtra("strings") as? StringsAOE2

        // Prepare UI
        tvPlayerName.text = "Last Match of: ${player?.name}"
        tvMsg.text = ""
        tvMatchName.text = ""
        tvMatchLength.text = ""
        tvMatchMap.text = ""

        // Request data from network
        getData()
    }

    private fun getData() {
        if (isOnline(this)) {
            player?.let { p ->
                CoroutineLastmatch(LastmatchHandler(), p.profileId).start()
            }
        } else {
            finish()
        }
    }

    private fun setMsg(msg: String?) {
        msg?.let {
            tvMsg.text = it
        }
    }

    private fun setMatchInfo(mInfo: MatchInfo?) {
        matchInfo = mInfo

        matchInfo?.let { m ->
            tvMatchName.text = "Name: ${m.matchName}"

            if (m.inProgress())
                tvMatchLength.text = "Length: In progress (${m.getLength()}+ mins)"
            else
                tvMatchLength.text = "Length: ${m.getLength()} mins"

            tvMatchMap.text = "Map: ${m.mapName(strings)}"

            fragmentList.setParams(strings, m.matchPlayers) {
                    matchPlayer -> onClickMatchPlayer(matchPlayer)
            }
            setMsg("")
        }
    }

    private fun onClickMatchPlayer(matchPlayer: MatchPlayer) {
        matchInfo?.let { mInfo ->
            supportFragmentManager.commit {
                replace(R.id.fragmentListAndDetail, LastmatchFragmentDetail(mInfo, matchPlayer))
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
    }

    inner class LastmatchHandler : CoroutineHandler<MatchInfo> {
        override fun preRun() =
            setMsg("Loading Last Match Data...")
        override fun postRun(result: CoroutineResult<MatchInfo>) =
            if (result.isSuccessful) setMatchInfo(result.result) else setMsg(result.msg)
    }
}