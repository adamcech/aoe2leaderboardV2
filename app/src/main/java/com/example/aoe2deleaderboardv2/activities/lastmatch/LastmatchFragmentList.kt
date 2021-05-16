package com.example.aoe2deleaderboardv2.activities.lastmatch

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aoe2deleaderboardv2.R
import com.example.aoe2deleaderboardv2.dto.MatchPlayer
import com.example.aoe2deleaderboardv2.dto.StringsAOE2
import kotlinx.android.synthetic.main.lastmatch_list.*

class LastmatchFragmentList : Fragment(R.layout.lastmatch_list) {

    private var strings: StringsAOE2? = null
    private var matchPlayers: List<MatchPlayer>? = null
    private var onClick: ((MatchPlayer) -> Unit)? = null

    override fun onResume() {
        super.onResume()
        setRvAdapter()
    }

    fun setParams(strings: StringsAOE2?, matchPlayers: List<MatchPlayer>?, onClick: (MatchPlayer) -> Unit) {
        this.strings = strings
        this.matchPlayers = matchPlayers
        this.onClick = onClick

        setRvAdapter()
    }

    private fun setRvAdapter() {
        matchPlayers?.let { mPlayer ->
            onClick?.let { onClick ->
                rvMatchPlayers.adapter = LastmatchAdapter(mPlayer, strings) { matchPlayer -> onClick(matchPlayer) }
                rvMatchPlayers.addItemDecoration(
                    DividerItemDecoration(context, (rvMatchPlayers.layoutManager as LinearLayoutManager).orientation)
                )
            }
        }
    }

}