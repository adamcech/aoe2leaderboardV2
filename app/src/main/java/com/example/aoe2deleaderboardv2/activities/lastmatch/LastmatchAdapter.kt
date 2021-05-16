package com.example.aoe2deleaderboardv2.activities.lastmatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aoe2deleaderboardv2.R
import com.example.aoe2deleaderboardv2.dto.MatchPlayer
import com.example.aoe2deleaderboardv2.dto.StringsAOE2

class LastmatchAdapter(
        private val matchPlayers: List<MatchPlayer>,
        private val strings: StringsAOE2?,
        private val onClick: (MatchPlayer) -> Unit)
    : RecyclerView.Adapter<LastmatchAdapter.LastmatchViewHolder>() {

    inner class LastmatchViewHolder(view: View, onClick: (MatchPlayer) -> Unit) : RecyclerView.ViewHolder(view) {
        private val ivWinIcon: ImageView = view.findViewById(R.id.ivWinIcon)
        private val tvPlayerName: TextView = view.findViewById(R.id.tvPlayerName)
        private val tvPlayerCiv: TextView = view.findViewById(R.id.tvPLayerCivilization)

        private var matchPlayer: MatchPlayer? = null

        init {
            view.setOnClickListener {
                matchPlayer?.let { onClick(it) }
            }
        }

        fun bind(currentMatchPlayer: MatchPlayer) {
            matchPlayer = currentMatchPlayer
            matchPlayer?.let { m ->
                tvPlayerName.text = m.name
                tvPlayerCiv.text = m.civ(strings)

                m.isWinner?.let {win ->
                    ivWinIcon.setImageResource(if (win) R.drawable.win else R.drawable.lose)
                } ?: run {
                    ivWinIcon.setImageResource(R.drawable.loading)
                    val anim = RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f)
                    anim.interpolator = LinearInterpolator()
                    anim.repeatCount = Animation.INFINITE
                    anim.duration = 3000
                    ivWinIcon.startAnimation(anim)
                }

            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LastmatchViewHolder =
        LastmatchViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.lastmatch_entry, viewGroup, false),
            onClick
        )

    override fun onBindViewHolder(lastmatchViewHolder: LastmatchViewHolder, position: Int) {
        lastmatchViewHolder.bind(matchPlayers[position])
    }

    override fun getItemCount(): Int = matchPlayers.size

}
