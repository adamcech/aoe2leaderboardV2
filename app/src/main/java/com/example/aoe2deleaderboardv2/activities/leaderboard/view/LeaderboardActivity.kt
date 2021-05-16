package com.example.aoe2deleaderboardv2.activities.leaderboard.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aoe2deleaderboardv2.R
import com.example.aoe2deleaderboardv2.activities.OnlineActivity
import com.example.aoe2deleaderboardv2.activities.lastmatch.LastmatchActivity
import com.example.aoe2deleaderboardv2.activities.leaderboard.LeaderboardViewModel
import com.example.aoe2deleaderboardv2.coroutines.CoroutineLeaderboard
import com.example.aoe2deleaderboardv2.coroutines.CoroutineStringsAOE2
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineHandler
import com.example.aoe2deleaderboardv2.coroutines.implementation.CoroutineResult
import com.example.aoe2deleaderboardv2.dto.Player
import com.example.aoe2deleaderboardv2.dto.StringsAOE2
import com.example.aoe2deleaderboardv2.rest.config.RestGameMode
import kotlinx.android.synthetic.main.leaderboard.*

class LeaderboardActivity : FragmentActivity(), OnlineActivity {

    private val viewModel: LeaderboardViewModel by viewModels()

    private var strings: StringsAOE2? = null

    private var mode: RestGameMode = RestGameMode.ONE_VS_ONE_RANDOM_MAP
        set(value) {
            field = value
            refreshBtnChangeModeText()
            setRvPlayersAdapter(listOf(), false)
            getData()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaderboard)

        CoroutineStringsAOE2(StringsHandler()).start()
        btnChangeMode.setOnClickListener(onClickBtnChangeMode)

        viewModel.strings.observe(this, Observer<StringsAOE2> { selectedStrings ->
            // Update the UI
        })

        mode = RestGameMode.ONE_VS_ONE_RANDOM_MAP
    }

    private fun refreshBtnChangeModeText() {
        btnChangeMode.text = mode.type
    }

    private fun getData() {
        if (isOnline(this)) {
            CoroutineLeaderboard(LeaderboardHandler(), mode, 20).start()
        }
    }

    private fun setMsg(msg: String?) {
        msg?.let {
            tvMsg.text = it
        }
    }

    private fun setRvPlayersAdapter(players: List<Player>?, removeMsg: Boolean = true) {
        players?.let { p ->
            rvPlayers.adapter =
                LeaderboardAdapter(
                    p
                ) { player -> onClickPlayer(player) }
            rvPlayers.addItemDecoration(
                DividerItemDecoration(this, (rvPlayers.layoutManager as LinearLayoutManager).orientation))

            if (removeMsg) {
                setMsg("")
            }
        }
    }

    private fun onClickPlayer(player: Player) {
        strings?.let {
            val intent = Intent(this, LastmatchActivity::class.java)
            intent.putExtra("player", player)
            intent.putExtra("strings", strings)
            startActivity(intent)
        } ?: run {
            setMsg("Error show last match")
        }
    }

    private val onClickBtnChangeMode = View.OnClickListener { _ ->
        val modes = RestGameMode.getAll()

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Select Mode")
        builder.setItems(modes.map { it.type }.toTypedArray()) { _, which ->
            mode = modes[which]
        }
        builder.show()
    }

    inner class StringsHandler : CoroutineHandler<StringsAOE2> {
        override fun preRun() { }
        override fun postRun(result: CoroutineResult<StringsAOE2>) =
            if (result.isSuccessful) strings = result.result else setMsg(result.msg)
    }

    inner class LeaderboardHandler : CoroutineHandler<List<Player>> {
        override fun preRun() =
            setMsg("Loading Leaderboard Data...")
        override fun postRun(result: CoroutineResult<List<Player>>) =
            if (result.isSuccessful) setRvPlayersAdapter(result.result) else setMsg(result.msg)
    }
}