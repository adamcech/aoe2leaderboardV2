package com.example.aoe2deleaderboardv2.activities.leaderboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aoe2deleaderboardv2.dto.StringsAOE2

class LeaderboardViewModel : ViewModel() {

    init {
        Log.d("VMTHREAD", Thread.currentThread().name);
    }

    val strings = MutableLiveData<StringsAOE2>()

    fun select(strings: StringsAOE2) {
        this.strings.value = strings
    }

}