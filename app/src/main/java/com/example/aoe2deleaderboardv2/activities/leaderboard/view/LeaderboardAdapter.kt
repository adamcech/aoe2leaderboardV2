package com.example.aoe2deleaderboardv2.activities.leaderboard.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aoe2deleaderboardv2.R
import com.example.aoe2deleaderboardv2.dto.Player

@SuppressLint("SetTextI18n")
class LeaderboardAdapter(
        private val players: List<Player>,
        private val onClick: (Player) -> Unit)
    : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    class LeaderboardViewHolder(view: View, onClick: (Player) -> Unit) : RecyclerView.ViewHolder(view) {
        private val tvRankName: TextView = view.findViewById(R.id.tvRankName)
        private val tvRating: TextView = view.findViewById(R.id.tvRating)
        private val tvWinPercentage: TextView = view.findViewById(R.id.tvWinPercentage)

        private var player: Player? = null

        init {
            view.setOnClickListener {
                player?.let { onClick(it) }
            }
        }

        fun bind(currentPlayer: Player) {
            player = currentPlayer
            player?.let {
                tvRankName.text = "${it.rank}. ${it.name}"
                tvRating.text = "Rating: ${it.rating}"
                tvWinPercentage.text = "Win: ${it.winPercentage}%"
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.leaderboard_entry, viewGroup, false)
        return LeaderboardViewHolder(
            view,
            onClick
        )
    }

    override fun onBindViewHolder(leaderboardViewHolder: LeaderboardViewHolder, position: Int) {
        leaderboardViewHolder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

}
